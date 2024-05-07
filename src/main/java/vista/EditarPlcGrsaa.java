package vista;

import javax.swing.*;

public class EditarPlcGrsaa extends JFrame{
    public JPanel PanelEditarPlcGrsaa;
    public JComboBox<String> cmbId;
    public JButton btnGuardar;
    public JButton btnVolver;
    public JTextField tfNombre;
    public JTextField tfNumeroIdentificacion;
    public JTextField tfDireccion;
    public JComboBox<String> cmbTipoIdentificacion;
    public JComboBox<String> cmbEstrato;
    public JTextField tfFecha;
    public JTextField tfLectura;
    public JButton btnConsultar;

    public EditarPlcGrsaa(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelEditarPlcGrsaa);
    }
}
