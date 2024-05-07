package vista;

import javax.swing.*;

public class ConsultarGrsaa extends JFrame{
    public JPanel PanelConsultarGrsaa;
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

    public ConsultarGrsaa(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelConsultarGrsaa);
    }
}
