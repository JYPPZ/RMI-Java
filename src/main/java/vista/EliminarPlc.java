package vista;

import javax.swing.*;

public class EliminarPlc extends JFrame{
    private JComboBox cmbId;
    private JButton btnEliminar;
    private JButton btnVolver;
    private JPanel PanelEliminarPlc;

    public EliminarPlc(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelEliminarPlc);
    }
}
