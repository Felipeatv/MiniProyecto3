import javax.swing.*;
import java.awt.event.*;

public class PantallaFactura extends JFrame {
    private JTextField txtNombre, txtIdentificacion, txtDireccion;
    private JButton btnGuardarFactura;
    private FacturaJSON facturaManager;
    private Producto producto;

    public PantallaFactura(Producto producto) {
        this.producto = producto;
        facturaManager = new FacturaJSON();

        setTitle("Registrar Factura");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(10, 20, 80, 25);
        panel.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 165, 25);
        panel.add(txtNombre);

        JLabel lblIdentificacion = new JLabel("Identificación");
        lblIdentificacion.setBounds(10, 50, 80, 25);
        panel.add(lblIdentificacion);

        txtIdentificacion = new JTextField();
        txtIdentificacion.setBounds(100, 50, 165, 25);
        panel.add(txtIdentificacion);

        JLabel lblDireccion = new JLabel("Dirección");
        lblDireccion.setBounds(10, 80, 80, 25);
        panel.add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(100, 80, 165, 25);
        panel.add(txtDireccion);

        JLabel lblTotal = new JLabel("Total: $" + (producto.getPrecio() * 1.15));
        lblTotal.setBounds(10, 110, 200, 25);
        panel.add(lblTotal);

        btnGuardarFactura = new JButton("Guardar Factura");
        btnGuardarFactura.setBounds(10, 140, 160, 25);
        panel.add(btnGuardarFactura);

        btnGuardarFactura.addActionListener(e -> {
            if (txtNombre.getText().isEmpty() || txtIdentificacion.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Factura factura = new Factura(
                    txtNombre.getText(),
                    txtIdentificacion.getText(),
                    txtDireccion.getText(),
                    producto,
                    producto.getPrecio() * 0.15,
                    producto.getPrecio() * 1.15
            );

            facturaManager.agregarFactura(factura);
            JOptionPane.showMessageDialog(this, "Factura guardada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
    }
}
