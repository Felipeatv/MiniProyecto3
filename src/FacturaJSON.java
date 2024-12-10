import com.google.gson.*;
import java.io.*;
import java.util.*;

public class FacturaJSON {
    private List<Factura> facturas;

    public FacturaJSON() {
        facturas = new ArrayList<>();
    }

    public void agregarFactura(Factura factura) {
        facturas.add(factura);
        guardarFacturasEnJSON();
    }

    public void guardarFacturasEnJSON() {
        try (Writer writer = new FileWriter("facturas.json")) {
            Gson gson = new Gson();
            gson.toJson(facturas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Factura> cargarFacturasDesdeJSON() {
        List<Factura> facturasCargadas = new ArrayList<>();
        try (Reader reader = new FileReader("facturas.json")) {
            Gson gson = new Gson();
            facturasCargadas = Arrays.asList(gson.fromJson(reader, Factura[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facturasCargadas;
    }
}

