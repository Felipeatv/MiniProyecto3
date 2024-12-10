import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class PantallaProducto extends JFrame {
    private JTextField txtCodigo, txtNombre, txtPrecio, txtCategoria;
    private JButton btnAgregarProducto, btnComprar;
    private JTable tableProductos;
    private ProductoXML productoManager;

    public PantallaProducto() {
        productoManager = new ProductoXML();
        setTitle("Gestión de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        JLabel lblCodigo = new JLabel("Código");
        lblCodigo.setBounds(10, 20, 80, 25);
        panel.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 20, 165, 25);
        panel.add(txtCodigo);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(10, 50, 80, 25);
        panel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 50, 165, 25);
        panel.add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio");
        lblPrecio.setBounds(10, 80, 80, 25);
        panel.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(100, 80, 165, 25);
        panel.add(txtPrecio);

        JLabel lblCategoria = new JLabel("Categoría");
        lblCategoria.setBounds(10, 110, 80, 25);
        panel.add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(100, 110, 165, 25);
        panel.add(txtCategoria);

        btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.setBounds(10, 140, 160, 25);
        panel.add(btnAgregarProducto);

        btnAgregarProducto.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText();
                String nombre = txtNombre.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                String categoria = txtCategoria.getText();

                if (codigo.isEmpty() || nombre.isEmpty() || categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Producto producto = new Producto(codigo, nombre, precio, categoria, "ruta_imagen");
                productoManager.agregarProducto(producto);
                actualizarTabla();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnComprar = new JButton("Comprar");
        btnComprar.setBounds(200, 140, 160, 25);
        panel.add(btnComprar);

        btnComprar.addActionListener(e -> {
            int filaSeleccionada = tableProductos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para comprar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Producto productoSeleccionado = productoManager.cargarProductosDesdeXML().get(filaSeleccionada);
            new PantallaFactura(productoSeleccionado).setVisible(true);
        });

        tableProductos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableProductos);
        scrollPane.setBounds(10, 180, 550, 150);
        panel.add(scrollPane);

        actualizarTabla();
    }

    private void actualizarTabla() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Categoría");

        for (Producto producto : productoManager.cargarProductosDesdeXML()) {
            model.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getCategoria()});
        }

        tableProductos.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaProducto().setVisible(true));
    }
}

