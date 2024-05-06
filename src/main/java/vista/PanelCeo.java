package vista;

import javax.swing.*;

public class PanelCeo extends JFrame {

    // Inicializar los componentes
    public JButton btnRegistrar;
    public JButton btnEditar;
    public JButton btnEliminar;
    public JButton btnConsultar;
    public JButton btnSalir;
    public JPanel jpPanelPrincipal;

    public PanelCeo(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(jpPanelPrincipal);
    }
}
