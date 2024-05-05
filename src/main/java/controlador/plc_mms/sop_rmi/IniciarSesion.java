package controlador.plc_mms.sop_rmi;

import controlador.ControladorConsultar;
import controlador.ControladorEditar;
import controlador.ControladorPanel;
import controlador.ControladorRegistrar;
import controlador.plc_tu.sop_rmi.CallBackImp;
import controlador.plc_tu.utilidades.UtilidadesRegistroC;
import modelo.plc_mms.dto.UsuarioDTO;
import modelo.plc_mms.sop_rmi.IGestionUsuarios;
import vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class IniciarSesion implements ActionListener {
    private final ClienteSesion clienteSesionForm;
    private final PanelPrincipal panelPrincipalForm;
    private static IGestionUsuarios gestionUsuarios;

    public IniciarSesion(ClienteSesion clienteSesionForm, PanelPrincipal panelPrincipalForm) throws RemoteException {
        gestionUsuarios = new GestionUsuariosImp();
        this.clienteSesionForm = clienteSesionForm;
        this.panelPrincipalForm = panelPrincipalForm;
        this.clienteSesionForm.btnIniciarSesion.addActionListener(this);
        this.clienteSesionForm.btnSalir.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clienteSesionForm.btnIniciarSesion) {
            abrirSesion();
        }
        if (e.getSource() == clienteSesionForm.btnSalir) {
            System.exit(0);
        }
    }

    /**
     * Método para abrir sesión en el sistema.
     */
    public void abrirSesion() {
        try {
            int id = Integer.parseInt(clienteSesionForm.tfIde.getText());
            String user = clienteSesionForm.tfUsuario.getText();
            String pass = new String(clienteSesionForm.pfContrasena.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Usuario y contraseña no pueden estar vacíos.");
                return;
            }
            //verificar si el usuario existe
            UsuarioDTO usuario = gestionUsuarios.consultarUsuario(id);
            CallBackImp callBack = new CallBackImp();
            usuario.setCallback(callBack);
            if (usuario.getUsuario().equals(user)) {
                int sesion = gestionUsuarios.abrirSesion(new UsuarioDTO(id, user, pass));
                if (sesion == 1) {
                    panelPrincipalForm.btnRegistrar.setVisible(false);
                    panelPrincipalForm.setVisible(true);
                    clienteSesionForm.setVisible(false);
                } else if (sesion == 2) {
                    JOptionPane.showMessageDialog(null, "EN PROCESO DE IMPLEMENTACIÓN");
                    //TODO: Implementar lógica para usuario
                } else {
                    panelPrincipalForm.setVisible(true);
                    clienteSesionForm.setVisible(false);
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
        // Se crean los objetos necesarios
        ClienteSesion clienteSesionForm = new ClienteSesion();
        PanelPrincipal panelPrincipalForm = new PanelPrincipal();
        AgregarPlc agregarPlcForm = new AgregarPlc();
        EditarPlc editarPlcForm = new EditarPlc();
        EliminarPlc eliminarPlcForm = new EliminarPlc();
        GestionPlcTuImp gestionPlcTuImp = new GestionPlcTuImp();
        GestionUsuariosImp gestionUsuariosImp = new GestionUsuariosImp();
        ConsultarPlc consultarPlcForm = new ConsultarPlc();
        // controladores necesarios
        IniciarSesion iniciarSesion = new IniciarSesion(clienteSesionForm, panelPrincipalForm);
        ControladorPanel controladorPanel = new ControladorPanel(panelPrincipalForm, agregarPlcForm,
                consultarPlcForm, editarPlcForm, eliminarPlcForm, gestionPlcTuImp);
        ControladorRegistrar controladorRegistrar = new ControladorRegistrar(agregarPlcForm, gestionPlcTuImp, gestionUsuariosImp);
        ControladorConsultar controladorConsultar = new ControladorConsultar(gestionPlcTuImp, gestionUsuariosImp, consultarPlcForm);
        ControladorEditar controladorEditar = new ControladorEditar(gestionPlcTuImp, editarPlcForm);
        // Se obtiene el objeto remoto
        String direccionIpRMIRegistry = "localhost";
        int numPuertoRMIRegistry = 2024;
        gestionUsuarios = (IGestionUsuarios) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry,
                numPuertoRMIRegistry, "GestionUsuarios");


        // Se muestra la ventana de inicio de sesión
        clienteSesionForm.setVisible(true);
    }

}
