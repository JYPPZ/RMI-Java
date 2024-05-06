package vista;

import javax.swing.*;

public class PanelAdmin extends JFrame{
    public JButton btnEditar;
    public JButton btnEliminar;
    public JButton btnConsultar;
    public JPanel PanelGrsaa;
    public JButton btnSalir;

    public PanelAdmin(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelGrsaa);
    }
}
