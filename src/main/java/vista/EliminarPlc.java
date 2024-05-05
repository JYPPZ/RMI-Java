package vista;

import javax.swing.*;

public class EliminarPlc extends JFrame{
    public JComboBox<String> cmbId;
    public JButton btnEliminar;
    public JButton btnVolver;
    public JPanel PanelEliminarPlc;

    public EliminarPlc(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelEliminarPlc);
    }
}
