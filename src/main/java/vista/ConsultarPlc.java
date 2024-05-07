package vista;

import controlador.ControladorConsultar;

import javax.swing.*;

public class ConsultarPlc extends JFrame {
    public JLabel lblNombre;
    public JLabel lblTipoIdentificacion;
    public JLabel lblNumeroIdentificacion;
    public JLabel lblDireccion;
    public JLabel lblEstrato;
    public JLabel lblFecha;
    public JLabel lblLectura;
    public JComboBox<String> cmbId;
    public JButton btnConsultar;
    public JButton btnVolver;
    public JPanel PanelConsultarPlc;

    public ConsultarPlc(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelConsultarPlc);
    }
}
