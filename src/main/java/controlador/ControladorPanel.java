package controlador;

import controlador.plc_tu.utilidades.UtilidadesRegistroC;
import modelo.plc_mms.dto.UsuarioDTO;
import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorPanel implements ActionListener {
    private final PanelPrincipal panelPrincipalForm;
    private final AgregarPlc agregarPlcForm;
    private final ConsultarPlc consultarPlcForm;
    private final EditarPlc editarPlcForm;
    private final EliminarPlc eliminarPlcForm;
    private IGestionPlcTu gestionPlcTu;

    public ControladorPanel(PanelPrincipal panelPrincipalForm, AgregarPlc agregarPlcForm, ConsultarPlc consultarPlcForm, EditarPlc editarPlcForm, EliminarPlc eliminarPlcForm,
                            IGestionPlcTu gestionPlcTu) throws RemoteException {
        this.panelPrincipalForm = panelPrincipalForm;
        this.agregarPlcForm = agregarPlcForm;
        this.consultarPlcForm = consultarPlcForm;
        this.editarPlcForm = editarPlcForm;
        this.eliminarPlcForm = eliminarPlcForm;
        this.gestionPlcTu = gestionPlcTu;
        panelPrincipalForm.btnRegistrar.addActionListener(this);
        panelPrincipalForm.btnConsultar.addActionListener(this);
        panelPrincipalForm.btnSalir.addActionListener(this);
        panelPrincipalForm.btnEditar.addActionListener(this);
        panelPrincipalForm.btnEliminar.addActionListener(this);
        //iniciar obj remoto
        iniciar();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelPrincipalForm.btnRegistrar) {
            agregarPlcForm.setVisible(true);
        }
        if (e.getSource() == panelPrincipalForm.btnConsultar) {
            consultarPlcForm.setVisible(true);
            cargarIdPlc();
        }
        if (e.getSource() == panelPrincipalForm.btnSalir) {
            panelPrincipalForm.dispose();
        }
        if (e.getSource() == panelPrincipalForm.btnEditar) {
            editarPlcForm.setVisible(true);
            cargarIdPlc();
        }
        if (e.getSource() == panelPrincipalForm.btnEliminar) {
            eliminarPlcForm.setVisible(true);
            cargarIdPlc();
        }
    }

    /**
     * Método para cargar los IDs de los PLCs en el ComboBox.
     */
    public void cargarIdPlc() {
        try {
            // Obtener los IDs del PLC desde el gestor de PLC
            List<String> listaIds = gestionPlcTu.devolverIds(0);

            // limpiar caja
            consultarPlcForm.cmbId.removeAllItems();
            editarPlcForm.cmbId.removeAllItems();
            eliminarPlcForm.cmbId.removeAllItems();

            // Devolver la lista de IDs
            for (String id : listaIds){
                consultarPlcForm.cmbId.addItem(id);
                editarPlcForm.cmbId.addItem(id);
                eliminarPlcForm.cmbId.addItem(id);
            }

        } catch (Exception ex) {
            Logger.getLogger(ControladorConsultar.class.getName()).log(Level.SEVERE, "Error al buscar IDs de PLC");
            JOptionPane.showMessageDialog(null, "No se pudo encontrar los PLCs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para iniciar el objeto remoto.
     * @throws RemoteException Excepción de conexión.
     */
    public void iniciar() throws RemoteException{
        String direccionIpRMIRegistry = "localhost";
        int numPuertoRMIRegistry = 2025;

        gestionPlcTu = (IGestionPlcTu) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry, numPuertoRMIRegistry,
                "GestionPLC_TU");
    }
}
