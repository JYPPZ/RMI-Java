package controlador.plc_tu;

import modelo.plc_mms.dto.PlcTuDTO;
import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import modelo.plc_mms.sop_rmi.IGestionUsuarios;
import vista.ConsultarPlc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorConsultar implements ActionListener {
    private final IGestionPlcTu gestionPlcTu;
    private final IGestionUsuarios gestionUsuarios;
    private final ConsultarPlc consultarPlcForm;

    public ControladorConsultar(IGestionPlcTu gestionPlcTu, IGestionUsuarios gestionUsuarios, ConsultarPlc consultarPlcForm) {
        this.gestionPlcTu = gestionPlcTu;
        this.gestionUsuarios = gestionUsuarios;
        this.consultarPlcForm = consultarPlcForm;
        // action listeners
        consultarPlcForm.btnConsultar.addActionListener(this);
        consultarPlcForm.btnVolver.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultarPlcForm.btnVolver){
            consultarPlcForm.dispose();
        }if (e.getSource() == consultarPlcForm.btnConsultar){
            consultarPlc();
        }
    }

    /**
     * Método para consultar un PLC.
     */
    public void consultarPlc(){
        try {
            // Obtener el ID del PLC seleccionado
            String idPlc = (String) consultarPlcForm.cmbId.getSelectedItem();

            // Consultar el PLC con el ID deselection
            PlcTuDTO plcTu = gestionPlcTu.consultar(idPlc);

            // Mostrar los datos del PLC en los campos de texto
            consultarPlcForm.lblNombre.setText(plcTu.getNombrePropetario());
            consultarPlcForm.lblTipoIdentificacion.setText(plcTu.getTipoId());
            consultarPlcForm.lblNumeroIdentificacion.setText(plcTu.getNumId());
            consultarPlcForm.lblDireccion.setText(plcTu.getDireccionResidencia());
            consultarPlcForm.lblEstrato.setText(String.valueOf(plcTu.getEstrato()));
            consultarPlcForm.lblFecha.setText(plcTu.getFechaRegistro());
            consultarPlcForm.lblLectura.setText(String.valueOf(plcTu.getLectura()));

            // Enviar mensaje al usuario
            gestionUsuarios.enviarMensaje("Usuario id= " + 0 +" consulta PLC_TU con id:" + idPlc,0);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorConsultar.class.getName()).log(Level.SEVERE, "Error al consultar PLC");
            JOptionPane.showMessageDialog(null, "No se pudo consultar el PLC", "Error", JOptionPane.ERROR_MESSAGE);
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,  "Error -> "  + exception.getLocalizedMessage());
        }
    }

}
