package vista;

import javax.swing.*;

public class ConsultarMes extends JFrame{
    public JPanel PanelConsultarMes;
    public JLabel lblDireccionResidencia;
    public JLabel lblFechaConsulta;
    public JLabel lblLecturaFinal;
    public JLabel lblValorConsumo;
    public JLabel lblLectura;
    public JButton btnConsultar;
    public JButton btnVolver;
    public ConsultarMes(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelConsultarMes);
    }
}
