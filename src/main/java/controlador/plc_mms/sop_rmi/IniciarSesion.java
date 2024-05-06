package controlador.plc_mms.sop_rmi;

import controlador.*;
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
    private final PanelCeo panelCeoForm;
    private static IGestionUsuarios gestionUsuarios; //obj remoto 1
    private final PanelAdmin panelAdminForm;
    private UsuarioDTO usuario;

    public IniciarSesion(ClienteSesion clienteSesionForm, PanelCeo panelCeoForm, PanelAdmin panelAdminForm) throws RemoteException {
        //gestionUsuarios = new GestionUsuariosImp();
        this.clienteSesionForm = clienteSesionForm;
        this.panelCeoForm = panelCeoForm;
        this.panelAdminForm = panelAdminForm;
        this.clienteSesionForm.btnIniciarSesion.addActionListener(this);
        this.clienteSesionForm.btnSalir.addActionListener(this);
        // iniciar objeto remoto
        iniciar();
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clienteSesionForm.btnIniciarSesion) {
            //abrirSesion();
            System.out.println(abrirSesion());
            try {
                ControladorPanelAdmin controladorPanelAdmin = new ControladorPanelAdmin(abrirSesion());
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == clienteSesionForm.btnSalir) {
            System.exit(0);
        }
    }

    /**
     * Método para abrir sesión en el sistema.
     */
    public UsuarioDTO abrirSesion() {
        try {
            int id = Integer.parseInt(clienteSesionForm.tfIde.getText());
            String user = clienteSesionForm.tfUsuario.getText();
            String pass = new String(clienteSesionForm.pfContrasena.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Usuario y contraseña no pueden estar vacíos.");
                return null;
            }

            UsuarioDTO userIn = gestionUsuarios.consultarUsuario(id);
            if (userIn != null && userIn.getUsuario().equals(user)) {
                usuario = new UsuarioDTO(id, user, pass);  // Asegúrate de que el constructor de UsuarioDTO esté correctamente definido.
                usuario.setCallback(new CallBackImp());
                gestionUsuarios.registrarUsuario(usuario);
                int sesion = gestionUsuarios.abrirSesion(usuario);
                switch (sesion) {
                    case 1:
                        panelAdminForm.setVisible(true);
                        return usuario;
                    case 2:
                        JOptionPane.showMessageDialog(null, "Acceso no implementado aún.");
                        return null;
                    default:
                        panelCeoForm.setVisible(true);
                        return usuario;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado o contraseña incorrecta.");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de usuario debe ser numérico.");
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        } //catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
//        }
        return null;
    }

    public void iniciar(){
        // Se obtiene el objeto remoto
        String direccionIpRMIRegistry = "localhost";
        int numPuertoRMIRegistry = 2024;
        gestionUsuarios = (IGestionUsuarios) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry,
                numPuertoRMIRegistry, "GestionUsuarios");
    }

    public static void main(String[] args) throws RemoteException {
        // Se crean los objetos necesarios
        ClienteSesion clienteSesionForm = new ClienteSesion();
        // Se muestra la ventana de inicio de sesión
        clienteSesionForm.setVisible(true);
        PanelCeo panelCeoForm = new PanelCeo(); // panel Ceo
        PanelAdmin panelAdminForm = new PanelAdmin();
        AgregarPlc agregarPlcForm = new AgregarPlc();
        EditarPlc editarPlcForm = new EditarPlc();
        EliminarPlc eliminarPlcForm = new EliminarPlc();
        //IGestionPlcTu gestionPlcTuImp = new GestionPlcTuImp(); // usar esta en gestion de usuario
        //GestionUsuariosImp gestionUsuariosImp = new GestionUsuariosImp();
        ConsultarPlc consultarPlcForm = new ConsultarPlc();
        // controladores necesarios
        // Admin
        IniciarSesion iniciarSesion = new IniciarSesion(clienteSesionForm, panelCeoForm, panelAdminForm);

//        System.out.println(iniciarSesion.abrirSesion());
//        ControladorPanelAdmin controladorPanelAdmin = new ControladorPanelAdmin(iniciarSesion.abrirSesion());

        //oper
        ControladorPanelCeo controladorPanelCeo = new ControladorPanelCeo(panelCeoForm, agregarPlcForm,
                consultarPlcForm, editarPlcForm, eliminarPlcForm); //obj remoto 2
        ControladorRegistrar controladorRegistrar = new ControladorRegistrar(agregarPlcForm, ControladorPanelCeo.getGestionPlcTu(), gestionUsuarios);
        ControladorConsultar controladorConsultar = new ControladorConsultar(ControladorPanelCeo.getGestionPlcTu(), gestionUsuarios, consultarPlcForm);
        ControladorEditar controladorEditar = new ControladorEditar(ControladorPanelCeo.getGestionPlcTu(), editarPlcForm);
        ControladorEliminar controladorEliminar = new ControladorEliminar(eliminarPlcForm, ControladorPanelCeo.getGestionPlcTu());

    }

}
