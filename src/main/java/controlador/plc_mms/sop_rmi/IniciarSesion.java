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
    private static IGestionUsuarios gestionUsuarios; //obj remoto 1
    private final PanelCeo panelCeoForm;
    private final PanelAdmin panelAdminForm;

    public IniciarSesion(ClienteSesion clienteSesionForm, PanelCeo panelCeoForm,
                         PanelAdmin panelAdminForm) throws RemoteException {
        //gestionUsuarios = new GestionUsuariosImp();
        this.clienteSesionForm = clienteSesionForm;
        this.panelCeoForm = panelCeoForm;
        this.panelAdminForm = panelAdminForm;
        this.clienteSesionForm.btnIniciarSesion.addActionListener(this);
        this.clienteSesionForm.btnSalir.addActionListener(this);
        // iniciar objeto remoto
        iniciar();
    }

    /**
     * Ventanas y controladores necesarios para iniciar aplicacion
     */
    ConsultarGrsaa consultarGrsaaForm = new ConsultarGrsaa();
    EliminarPlcGrsaa eliminarPlcGrsaaForm = new EliminarPlcGrsaa();
    EditarPlcGrsaa editarPlcGrsaaForm = new EditarPlcGrsaa();
    // ventanas necesarias por parte del Ceo
    AgregarPlc agregarPlcForm = new AgregarPlc(); // Vista Agregar PLC
    EditarPlc editarPlcForm = new EditarPlc(); // Vista Editar PLC
    EliminarPlc eliminarPlcForm = new EliminarPlc(); // Vista Eliminar PLC
    ConsultarPlc consultarPlcForm = new ConsultarPlc(); // Vista Consultar PLC
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
                UsuarioDTO usuario = new UsuarioDTO(id, user, pass);
                usuario.setCallback(new CallBackImp());
                //capturarOR = new CapturarOR(usuario);
                //capturarOR.setUsuario(usuario);
                int sesion = gestionUsuarios.abrirSesion(usuario);

                switch (sesion) {
                    case 1 -> {
                        panelAdminForm.setVisible(true);
                        controladorPanelAdmin = new ControladorPanelAdmin(usuario, consultarGrsaaForm, eliminarPlcGrsaaForm, editarPlcGrsaaForm, panelAdminForm);
                        controladorConsultarGrsaa = new ControladorConsultarGrsaa(controladorPanelAdmin.getGestionConsumoPlc(), consultarGrsaaForm);
                        controladorEditarGrsaa = new ControladorEditarGrsaa(controladorPanelAdmin.getGestionConsumoPlc(), editarPlcGrsaaForm);
                        controladorEliminarGrsaa = new ControladorEliminarGrsaa(controladorPanelAdmin.getGestionConsumoPlc(), eliminarPlcGrsaaForm);
                    }
                    case 2 -> {
                        JOptionPane.showMessageDialog(null, "Acceso no implementado aún.");
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
