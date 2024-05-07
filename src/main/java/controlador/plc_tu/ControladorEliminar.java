package controlador.plc_tu;

import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import vista.EliminarPlc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEliminar implements ActionListener {
    private final IGestionPlcTu gestionPlcTu;
    private final EliminarPlc eliminarPlcForm;


    public ControladorEliminar(EliminarPlc eliminarPlcForm, IGestionPlcTu gestionPlcTu) {
        this.eliminarPlcForm = eliminarPlcForm;
        this.gestionPlcTu = gestionPlcTu;
        eliminarPlcForm.btnEliminar.addActionListener(this);
        eliminarPlcForm.btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == eliminarPlcForm.btnEliminar) {
            eliminarPlc();
        }
        if (e.getSource() == eliminarPlcForm.btnVolver) {
            eliminarPlcForm.dispose();
        }
    }

    /**
     * Método para eliminar un PLC
     */
    private void eliminarPlc() {
        try {
            String idPlc = (String) eliminarPlcForm.cmbId.getSelectedItem();
            if (gestionPlcTu.eliminar(idPlc)) {
                JOptionPane.showMessageDialog(null, "PLC eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "PLC eliminado correctamente", "error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar PLC" + e.getLocalizedMessage());
        }
    }
}
