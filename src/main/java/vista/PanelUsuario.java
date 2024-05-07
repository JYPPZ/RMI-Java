package vista;

import javax.swing.*;

public class PanelUsuario extends JFrame{
    public JPanel PanelUsuario;
    public JButton btnConsultarPlc;
    public JButton btnConsultarLecturaActual;
    public JButton btnConsultarFactura;
    public JButton btnSalir;

    public PanelUsuario(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelUsuario);
    }
}
