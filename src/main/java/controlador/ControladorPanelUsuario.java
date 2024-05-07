package controlador;

import controlador.plc_tu.ControladorConsultar;
import controlador.plc_tu.utilidades.UtilidadesRegistroC;
import modelo.plc_mms.dto.UsuarioDTO;
import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import modelo.plc_mms.sop_rmi.IGestionUsuarios;
import vista.ConsultarActual;
import vista.ConsultarMes;
import vista.ConsultarPlc;
import vista.PanelUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorPanelUsuario implements ActionListener {
    private IGestionPlcTu gestionPlcTu;
    private IGestionUsuarios gestionUsuarios;
    private final UsuarioDTO user;
    private final PanelUsuario panelUsuarioForm;
    private final ConsultarMes consultarMesForm;
    private final ConsultarActual consultarActualForm;
    private final ConsultarPlc consultarPlcForm;

    public ControladorPanelUsuario(UsuarioDTO user, PanelUsuario panelUsuarioForm, ConsultarMes consultarMesForm, ConsultarActual consultarActualForm, ConsultarPlc consultarPlcForm) throws RemoteException {
        this.user = user;
        this.panelUsuarioForm = panelUsuarioForm;
        this.consultarMesForm = consultarMesForm;
        this.consultarActualForm = consultarActualForm;
        this.consultarPlcForm = consultarPlcForm;
        panelUsuarioForm.btnConsultarPlc.addActionListener(this);
        panelUsuarioForm.btnConsultarFactura.addActionListener(this);
        panelUsuarioForm.btnConsultarLecturaActual.addActionListener(this);
        panelUsuarioForm.btnSalir.addActionListener(this);
        //inciar objeto remoto
        iniciar();
    }

    public IGestionPlcTu getGestionPlcTu() {
        return gestionPlcTu;
    }

    public IGestionUsuarios getGestionUsuarios() {
        return gestionUsuarios;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelUsuarioForm.btnConsultarPlc) {
            consultarPlcForm.setVisible(true);
            cargarIdPlc();
        }
        if (e.getSource() == panelUsuarioForm.btnConsultarFactura) {
            consultarMesForm.setVisible(true);
        }
        if (e.getSource() == panelUsuarioForm.btnConsultarLecturaActual) {
            consultarActualForm.setVisible(true);
        }
        if (e.getSource() == panelUsuarioForm.btnSalir) {
            panelUsuarioForm.dispose();
        }
    }
    /**
     * Método para cargar los IDs de los PLCs en el ComboBox.
     */
    public void cargarIdPlc() {
        try {
            // Obtener los IDs del PLC desde el gestor de PLC
            List<String> listaIds = gestionPlcTu.devolverIds(0);
            //
            System.out.println("Lista de IDs: " + listaIds);
            // limpiar caja
            consultarPlcForm.cmbId.removeAllItems();

            // Devolver la lista de IDs
            for (String id : listaIds){
                consultarPlcForm.cmbId.addItem(id);
            }

        } catch (Exception ex) {
            Logger.getLogger(ControladorConsultar.class.getName()).log(Level.SEVERE, "Error al buscar IDs de PLC");
            JOptionPane.showMessageDialog(null, "No se pudo encontrar los PLCs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void iniciar() throws RemoteException {
        String direccionIpRMIRegistry = "localhost";
        int numPuertoRMIRegistry = 2025;

        gestionPlcTu = (IGestionPlcTu) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry, numPuertoRMIRegistry,
                "GestionPLC_TU");

        gestionUsuarios = (IGestionUsuarios) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry, 2024,
                "GestionUsuarios");
        JOptionPane.showMessageDialog(null, user.getCallback().getMensaje(), "Usuario", JOptionPane.INFORMATION_MESSAGE);
    }
}
