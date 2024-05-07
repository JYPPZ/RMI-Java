package controlador.grsaa;

import controlador.plc_tu.ControladorConsultar;
import modelo.grsaa.sop_rmi.IGestionConsumoPlc;
import modelo.plc_mms.dto.PlcTuDTO;
import vista.ConsultarGrsaa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorConsultarGrsaa implements ActionListener {
    private final IGestionConsumoPlc gestionConsumoPlc;
    private final ConsultarGrsaa consultarGrsaaForm;

    public ControladorConsultarGrsaa(IGestionConsumoPlc gestionConsumoPlc, ConsultarGrsaa consultarGrsaaForm) {
        this.gestionConsumoPlc = gestionConsumoPlc;
        this.consultarGrsaaForm = consultarGrsaaForm;
        consultarGrsaaForm.btnConsultar.addActionListener(this);
        consultarGrsaaForm.btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultarGrsaaForm.btnVolver){
            consultarGrsaaForm.dispose();
        }if (e.getSource() == consultarGrsaaForm.btnConsultar){
            consultarPlc();
        }
    }
    /**
     * Método para consultar un PLC.
     */
    public void consultarPlc(){
        try {
            // Obtener el ID del PLC seleccionado
            String idPlc = (String) consultarGrsaaForm.cmbId.getSelectedItem();

            // Consultar el PLC con el ID seleccionado
            PlcTuDTO plcTu = gestionConsumoPlc.consultar(idPlc);

            // Mostrar los datos del PLC en los campos de texto
            consultarGrsaaForm.lblNombre.setText(plcTu.getNombrePropetario());
            consultarGrsaaForm.lblTipoIdentificacion.setText(plcTu.getTipoId());
            consultarGrsaaForm.lblNumeroIdentificacion.setText(plcTu.getNumId());
            consultarGrsaaForm.lblDireccion.setText(plcTu.getDireccionResidencia());
            consultarGrsaaForm.lblEstrato.setText(String.valueOf(plcTu.getEstrato()));
            consultarGrsaaForm.lblFecha.setText(plcTu.getFechaRegistro());
            consultarGrsaaForm.lblLectura.setText(String.valueOf(plcTu.getLectura()));

        } catch (RemoteException ex) {
            Logger.getLogger(ControladorConsultar.class.getName()).log(Level.SEVERE, "Error al consultar PLC");
            JOptionPane.showMessageDialog(null, "No se pudo consultar el PLC", "Error", JOptionPane.ERROR_MESSAGE);
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,  "Error -> "  + exception.getLocalizedMessage());
        }
    }
}
