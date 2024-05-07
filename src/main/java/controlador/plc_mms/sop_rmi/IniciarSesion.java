package controlador.plc_mms.sop_rmi;

import controlador.ControladorConsultarActual;
import controlador.ControladorConsultarMes;
import controlador.ControladorConsultarUsuario;
import controlador.ControladorPanelUsuario;
import controlador.grsaa.ControladorConsultarGrsaa;
import controlador.grsaa.ControladorEditarGrsaa;
import controlador.grsaa.ControladorEliminarGrsaa;
import controlador.grsaa.ControladorPanelAdmin;
import controlador.plc_tu.*;
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
    private static IGestionUsuarios gestionUsuarios; //obj remoto 1
    private final PanelCeo panelCeoForm;
    private final PanelAdmin panelAdminForm;
    private final PanelUsuario panelUsuarioForm;
    public IniciarSesion(ClienteSesion clienteSesionForm, PanelCeo panelCeoForm,
                         PanelAdmin panelAdminForm, PanelUsuario panelUsuarioForm) throws RemoteException {
        //gestionUsuarios = new GestionUsuariosImp();
        this.clienteSesionForm = clienteSesionForm;
        this.panelCeoForm = panelCeoForm;
        this.panelAdminForm = panelAdminForm;
        this.panelUsuarioForm = panelUsuarioForm;
        this.clienteSesionForm.btnIniciarSesion.addActionListener(this);
        this.clienteSesionForm.btnSalir.addActionListener(this);
        // iniciar objeto remoto
        iniciar();
    }

    /**
     * Ventanas y controladores necesarios para iniciar aplicacion
     */
    // ventanas necesarias por parte de admin
    ConsultarGrsaa consultarGrsaaForm = new ConsultarGrsaa();
    EliminarPlcGrsaa eliminarPlcGrsaaForm = new EliminarPlcGrsaa();
    EditarPlcGrsaa editarPlcGrsaaForm = new EditarPlcGrsaa();
    // ventanas necesarias por parte del Ceo
    AgregarPlc agregarPlcForm = new AgregarPlc(); // Vista Agregar PLC
    EditarPlc editarPlcForm = new EditarPlc(); // Vista Editar PLC
    EliminarPlc eliminarPlcForm = new EliminarPlc(); // Vista Eliminar PLC
    ConsultarPlc consultarPlcForm = new ConsultarPlc(); // Vista Consultar PLC
    //ventanas necesarias usuario
    ConsultarMes consultarMesForm = new ConsultarMes();
    ConsultarActual consultarActualForm = new ConsultarActual();
    // Controlador Ceo
    ControladorPanelCeo controladorPanelCeo; //obj remoto 2
    ControladorRegistrar controladorRegistrar;
    ControladorConsultar controladorConsultar;
    ControladorEditar controladorEditar;
    ControladorEliminar controladorEliminar;
    // controladores Admin
    ControladorPanelAdmin controladorPanelAdmin;
    ControladorConsultarGrsaa controladorConsultarGrsaa;
    ControladorEditarGrsaa controladorEditarGrsaa;
    ControladorEliminarGrsaa controladorEliminarGrsaa;
    // controladores Usuario
    ControladorPanelUsuario controladorPanelUsuario;
    ControladorConsultarMes controladorConsultarMes;
    ControladorConsultarActual controladorConsultarActual;
    ControladorConsultarUsuario controladorConsultarUsuario;
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
    private void abrirSesion() {
        try {
            int id = Integer.parseInt(clienteSesionForm.tfIde.getText());
            String user = clienteSesionForm.tfUsuario.getText();
            String pass = new String(clienteSesionForm.pfContrasena.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Usuario y contraseña no pueden estar vacíos.");
                return;
            }

            UsuarioDTO userIn = gestionUsuarios.consultarUsuario(id);
            if (userIn != null && userIn.getUsuario().equals(user)) {
                int sesion = gestionUsuarios.abrirSesion(userIn);

                switch (sesion) {
                    case 1 -> {
                        panelAdminForm.setVisible(true);
                        controladorPanelAdmin = new ControladorPanelAdmin(userIn, consultarGrsaaForm, eliminarPlcGrsaaForm, editarPlcGrsaaForm, panelAdminForm);
                        controladorConsultarGrsaa = new ControladorConsultarGrsaa(controladorPanelAdmin.getGestionConsumoPlc(), consultarGrsaaForm);
                        controladorEditarGrsaa = new ControladorEditarGrsaa(controladorPanelAdmin.getGestionConsumoPlc(), editarPlcGrsaaForm);
                        controladorEliminarGrsaa = new ControladorEliminarGrsaa(controladorPanelAdmin.getGestionConsumoPlc(), eliminarPlcGrsaaForm);
                    }
                    case 2 -> {
                        panelUsuarioForm.setVisible(true);
                        controladorPanelUsuario = new ControladorPanelUsuario(userIn, panelUsuarioForm, consultarMesForm, consultarActualForm, consultarPlcForm);
                        controladorConsultarActual = new ControladorConsultarActual(controladorPanelUsuario.getGestionPlcTu(), controladorPanelUsuario.getGestionUsuarios(), consultarActualForm);
                        controladorConsultarMes = new ControladorConsultarMes(controladorPanelUsuario.getGestionPlcTu(), controladorPanelUsuario.getGestionUsuarios(), consultarMesForm);
                        controladorConsultarUsuario = new ControladorConsultarUsuario(controladorPanelUsuario.getGestionPlcTu(), consultarPlcForm);
                        clienteSesionForm.setVisible(false);
                    }
                    default -> {
                        panelCeoForm.setVisible(true);
                        controladorPanelCeo = new ControladorPanelCeo(panelCeoForm, agregarPlcForm, consultarPlcForm, editarPlcForm, eliminarPlcForm); //obj remoto 2
                        controladorRegistrar = new ControladorRegistrar(agregarPlcForm, ControladorPanelCeo.getGestionPlcTu(), gestionUsuarios);
                        controladorConsultar = new ControladorConsultar(ControladorPanelCeo.getGestionPlcTu(), gestionUsuarios, consultarPlcForm);
                        controladorEditar = new ControladorEditar(ControladorPanelCeo.getGestionPlcTu(), editarPlcForm);
                        controladorEliminar = new ControladorEliminar(eliminarPlcForm, ControladorPanelCeo.getGestionPlcTu());
                    }
                };
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado o contraseña incorrecta.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de usuario debe ser numérico.");
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        }
    }

    private void iniciar(){
        // Se obtiene el objeto remoto
        String direccionIpRMIRegistry = "localhost";
        int numPuertoRMIRegistry = 2024;
        gestionUsuarios = (IGestionUsuarios) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry,
                numPuertoRMIRegistry, "GestionUsuarios");
    }

}
