import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class ProductoXML {
    private List<Producto> productos;

    public ProductoXML() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarProductosEnXML();
    }

    public void guardarProductosEnXML() {
        try {
            File xmlFile = new File("productos.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("productos");
            doc.appendChild(rootElement);

            for (Producto producto : productos) {
                Element productoElement = doc.createElement("producto");
                rootElement.appendChild(productoElement);

                Element codigo = doc.createElement("codigo");
                codigo.appendChild(doc.createTextNode(producto.getCodigo()));
                productoElement.appendChild(codigo);

                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(producto.getNombre()));
                productoElement.appendChild(nombre);

                Element precio = doc.createElement("precio");
                precio.appendChild(doc.createTextNode(String.valueOf(producto.getPrecio())));
                productoElement.appendChild(precio);

                Element categoria = doc.createElement("categoria");
                categoria.appendChild(doc.createTextNode(producto.getCategoria()));
                productoElement.appendChild(categoria);

                Element imagen = doc.createElement("imagen");
                imagen.appendChild(doc.createTextNode(producto.getImagen()));
                productoElement.appendChild(imagen);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Producto> cargarProductosDesdeXML() {
        List<Producto> productosCargados = new ArrayList<>();
        try {
            File xmlFile = new File("productos.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFile);

            NodeList nodeList = doc.getElementsByTagName("producto");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
                    String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                    double precio = Double.parseDouble(element.getElementsByTagName("precio").item(0).getTextContent());
                    String categoria = element.getElementsByTagName("categoria").item(0).getTextContent();
                    String imagen = element.getElementsByTagName("imagen").item(0).getTextContent();

                    productosCargados.add(new Producto(codigo, nombre, precio, categoria, imagen));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productosCargados;
    }
}
