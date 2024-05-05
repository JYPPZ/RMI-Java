package vista;

import javax.swing.*;

public class AgregarPlc extends JFrame{
    public JTextField tfNumeroIdentificacion;
    public JTextField tfLectura;
    public JTextField tfDireccion;
    public JTextField tfFecha;
    public JTextField tfNombre;
    public JComboBox cmbTipoIdentificacion;
    public JComboBox cmbEstrato;
    public JButton btnGuardar;
    public JButton btnVolver;
    private JPanel PanelRegistrarPlc;

    public AgregarPlc(){
        setLocationRelativeTo(null);
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelRegistrarPlc);
    }
}
