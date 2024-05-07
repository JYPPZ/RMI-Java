package controlador;

import modelo.grsaa.sop_rmi.IGestionConsumoPlc;
import vista.EliminarPlcGrsaa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEliminarGrsaa implements ActionListener {
    private final IGestionConsumoPlc gestionConsumoPlc;
    private final EliminarPlcGrsaa eliminarPlcFormGrsaa;

    public ControladorEliminarGrsaa(IGestionConsumoPlc gestionConsumoPlc, EliminarPlcGrsaa eliminarPlcFormGrsaa) {
        this.gestionConsumoPlc = gestionConsumoPlc;
        this.eliminarPlcFormGrsaa = eliminarPlcFormGrsaa;
        eliminarPlcFormGrsaa.btnEliminar.addActionListener(this);
        eliminarPlcFormGrsaa.btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == eliminarPlcFormGrsaa.btnEliminar) {
            eliminarPlc();
        }
        if (e.getSource() == eliminarPlcFormGrsaa.btnVolver) {
            eliminarPlcFormGrsaa.dispose();
        }
    }

    /**
     * Método para eliminar un PLC
     */
    private void eliminarPlc() {
        try {
            String idPlc = (String) eliminarPlcFormGrsaa.cmbId.getSelectedItem();
            if (gestionConsumoPlc.eliminar(idPlc)) {
                JOptionPane.showMessageDialog(null, "PLC eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "PLC eliminado correctamente", "error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar PLC" + e.getLocalizedMessage());
        }
    }
}
