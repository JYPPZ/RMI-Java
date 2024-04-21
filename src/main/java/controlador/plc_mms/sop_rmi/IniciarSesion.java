package controlador.plc_mms.sop_rmi;

import modelo.plc_mms.dto.UsuarioDTO;
import modelo.plc_mms.sop_rmi.IGestionUsuarios;
import vista.ClienteSesion;
import vista.PanelPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class IniciarSesion implements ActionListener {
    private final ClienteSesion cliente;
    private final PanelPrincipal miPanel;
    private final IGestionUsuarios gestion = new GestionUsuariosImp();

    public IniciarSesion(ClienteSesion cliente, PanelPrincipal miPanel) throws RemoteException {
        this.cliente = cliente;
        this.miPanel = miPanel;
        this.cliente.btnIniciarSesion.addActionListener(this);
        this.cliente.btnSalir.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cliente.btnIniciarSesion) {
            abrirSesion();
        }
        if (e.getSource() == cliente.btnSalir) {
            System.exit(0);
        }
    }
    public void abrirSesion() {
        try {
            int id = Integer.parseInt(cliente.tfIde.getText());
            String user = cliente.tfUsuario.getText();
            String pass = new String(cliente.pfContrasena.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Usuario y contraseña no pueden estar vacíos.");
                return;
            }
            //verificar si el usuario existe
            UsuarioDTO usuario = gestion.consultarUsuario(id);
            if (usuario != null && usuario.getUsuario().equals(user)) {
                int sesion = gestion.abrirSesion(new UsuarioDTO(id, user, pass));
                if (sesion == 1) {
                    miPanel.btnRegistrar.setVisible(false);
                    miPanel.setVisible(true);
                } else if (sesion == 2) {
                    JOptionPane.showMessageDialog(null, "EN PROCESO DE IMPLEMENTACIÓN");
                    //TODO: Implementar lógica para usuario administrador
                } else {
                    miPanel.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de usuario debe ser numérico.");
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws RemoteException {
        ClienteSesion cliente = new ClienteSesion();
        PanelPrincipal miPanel = new PanelPrincipal();
        IniciarSesion iniciarSesion = new IniciarSesion(cliente, miPanel);
        cliente.setVisible(true);
    }
}
