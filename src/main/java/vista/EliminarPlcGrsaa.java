package vista;

import javax.swing.*;

public class EliminarPlcGrsaa extends JFrame{
    public JPanel PanelEliminarPlcGrsaa;
    public JComboBox<String> cmbId;
    public JButton btnEliminar;
    public JButton btnVolver;
    public EliminarPlcGrsaa(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelEliminarPlcGrsaa);
    }
}
