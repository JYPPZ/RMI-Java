package vista;

import javax.swing.*;
import java.awt.*;

public class EditarPlc extends JFrame {

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
    public JPanel PanelEditarPlc;
    public JButton btnConsultar;

    public EditarPlc(){
        setLocationRelativeTo(null);
        setSize(350, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelEditarPlc);
    }
}
