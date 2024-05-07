package vista;

import javax.swing.*;

public class ConsultarActual extends JFrame{
    public JPanel PanelConsultarActual;
    public JLabel lblId;
    public JLabel lblDireccion;
    public JLabel lblFechaConsulta;
    public JLabel lblLectura;
    public JButton btnVolver;
    public JButton btnConsultar;

    public ConsultarActual(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelConsultarActual);
    }
}
