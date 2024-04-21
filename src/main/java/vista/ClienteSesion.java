package vista;

import javax.swing.*;

public class ClienteSesion extends JFrame{
    public JPanel panel1;
    public JLabel lblId;
    public JLabel lblUsuario;
    public JLabel lblContrasena;
    public JButton btnIniciarSesion;
    public JTextField tfIde;
    public JTextField tfUsuario;
    public JPasswordField pfContrasena;
    public JButton btnSalir;

    public ClienteSesion(){
        setLocationRelativeTo(null);
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
    }
}
