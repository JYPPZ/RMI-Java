package controlador;

import controlador.plc_tu.utilidades.UtilidadesRegistroC;
import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorPanelCeo implements ActionListener {
    private final PanelCeo panelCeoForm;
    private final AgregarPlc agregarPlcForm;
    private final ConsultarPlc consultarPlcForm;
    private final EditarPlc editarPlcForm;
    private final EliminarPlc eliminarPlcForm;
    private static IGestionPlcTu gestionPlcTu;
    public ControladorPanelCeo(PanelCeo panelCeoForm, AgregarPlc agregarPlcForm,
                               ConsultarPlc consultarPlcForm, EditarPlc editarPlcForm,
                               EliminarPlc eliminarPlcForm) throws RemoteException {
        this.panelCeoForm = panelCeoForm;
        this.agregarPlcForm = agregarPlcForm;
        this.consultarPlcForm = consultarPlcForm;
        this.editarPlcForm = editarPlcForm;
        this.eliminarPlcForm = eliminarPlcForm;
        //ControladorPanel.gestionPlcTu = gestionPlcTu;
        //gestionPlcTu = new GestionPlcTuImp();
        panelCeoForm.btnRegistrar.addActionListener(this);
        panelCeoForm.btnConsultar.addActionListener(this);
        panelCeoForm.btnSalir.addActionListener(this);
        panelCeoForm.btnEditar.addActionListener(this);
        panelCeoForm.btnEliminar.addActionListener(this);

        iniciar();
    }

    public static IGestionPlcTu getGestionPlcTu() {
        return gestionPlcTu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelCeoForm.btnRegistrar) {
            agregarPlcForm.setVisible(true);
        }
        if (e.getSource() == panelCeoForm.btnConsultar) {
            consultarPlcForm.setVisible(true);
            cargarIdPlc();
        }
        if (e.getSource() == panelCeoForm.btnSalir) {
            panelCeoForm.dispose();
        }
        if (e.getSource() == panelCeoForm.btnEditar) {
            editarPlcForm.setVisible(true);
            cargarIdPlc();
        }
        if (e.getSource() == panelCeoForm.btnEliminar) {
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
            //
            System.out.println("Lista de IDs: " + listaIds);
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
        try {
            String direccionIpRMIRegistry = "localhost";
            int numPuertoRMIRegistry = 2025;

            gestionPlcTu = (IGestionPlcTu) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry, numPuertoRMIRegistry,
                    "GestionPLC_TU");
        } catch (Exception ex) {
            Logger.getLogger(ControladorPanelCeo.class.getName()).log(Level.SEVERE, "Error al iniciar el objeto remoto");
        }
    }
}
