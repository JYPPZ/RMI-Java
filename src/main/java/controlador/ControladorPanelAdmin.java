package controlador;

import controlador.grsaa.utilidades.UtilidadesRegistroC;
import modelo.grsaa.sop_rmi.IGestionConsumoPlc;
import modelo.plc_mms.dto.UsuarioDTO;
import vista.ConsultarGrsaa;
import vista.EditarPlcGrsaa;
import vista.EliminarPlcGrsaa;
import vista.PanelAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorPanelAdmin implements ActionListener {
    private IGestionConsumoPlc gestionConsumoPlc;//objeto remoto 3
    private final UsuarioDTO user;
    private ConsultarGrsaa consultarGrsaaForm;
    private EliminarPlcGrsaa eliminarPlcGrsaaForm;
    private EditarPlcGrsaa editarPlcGrsaaForm;
    private PanelAdmin panelAdminForm;

    public ControladorPanelAdmin(UsuarioDTO user) {
        this.user = user;
    }

    public ControladorPanelAdmin(UsuarioDTO user, ConsultarGrsaa consultarGrsaaForm, EliminarPlcGrsaa eliminarPlcGrsaaForm,
                                 EditarPlcGrsaa editarPlcGrsaaForm, PanelAdmin panelAdminForm) throws RemoteException {
        this.user = user;
        this.consultarGrsaaForm = consultarGrsaaForm;
        this.eliminarPlcGrsaaForm = eliminarPlcGrsaaForm;
        this.editarPlcGrsaaForm = editarPlcGrsaaForm;
        this.panelAdminForm = panelAdminForm;

        panelAdminForm.btnConsultar.addActionListener(this);
        panelAdminForm.btnEditar.addActionListener(this);
        panelAdminForm.btnEliminar.addActionListener(this);
        //iniciar el objeto remoto
        iniciar();
    }

    /**
     * Método para obtener el objeto remoto
     * @return Objeto remoto
     */
    public IGestionConsumoPlc getGestionConsumoPlc() {
        return gestionConsumoPlc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelAdminForm.btnConsultar){
            consultarGrsaaForm.setVisible(true);
            cargarIdPlc();
        }
        if (e.getSource() ==  panelAdminForm.btnEditar){
            editarPlcGrsaaForm.setVisible(true);
            cargarIdPlc();
        }
        if (e.getSource() == panelAdminForm.btnEliminar){
            eliminarPlcGrsaaForm.setVisible(true);
            cargarIdPlc();
        }
    }

    /**
     * Método para cargar los IDs de los PLCs en el ComboBox.
     */
    public void cargarIdPlc() {
        try {
            // Obtener los IDs del PLC desde el gestor de PLC
            List<String> listaIds = gestionConsumoPlc.devolverIds();
            //
            System.out.println("Lista de IDs: " + listaIds);
            // limpiar caja
            consultarGrsaaForm.cmbId.removeAllItems();
            editarPlcGrsaaForm.cmbId.removeAllItems();
            eliminarPlcGrsaaForm.cmbId.removeAllItems();

            // Devolver la lista de IDs
            for (String id : listaIds){
                consultarGrsaaForm.cmbId.addItem(id);
                editarPlcGrsaaForm.cmbId.addItem(id);
                eliminarPlcGrsaaForm.cmbId.addItem(id);
            }

        } catch (Exception ex) {
            Logger.getLogger(ControladorConsultar.class.getName()).log(Level.SEVERE, "Error al buscar IDs de PLC");
            JOptionPane.showMessageDialog(null, "No se pudo encontrar los PLCs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para iniciar la conexión con el servidor.
     * @throws RemoteException Excepción de conexión.
     */
    public void iniciar() throws RemoteException {
        String direccionIpRMIRegistry = "localhost";
        int numPuertoRMIRegistry = 2025;

        gestionConsumoPlc = (IGestionConsumoPlc) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry, numPuertoRMIRegistry,
                "GestionConsumoPlc");
        JOptionPane.showMessageDialog(null, "Hola esta es una prueBa " + user.getCallback().getMensaje());
    }
}
