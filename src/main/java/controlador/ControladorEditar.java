package controlador;

import modelo.plc_mms.dto.PlcTuDTO;
import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import vista.EditarPlc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorEditar implements ActionListener {
    private final IGestionPlcTu gestionPlcTu;
    private final EditarPlc editarPlcForm;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editarPlcForm.btnGuardar){
            actualizarDatos();
        }
        if (e.getSource() == editarPlcForm.btnConsultar){
            consultarPlc();
        }
        if (e.getSource() == editarPlcForm.btnVolver){
            editarPlcForm.dispose();
        }
    }

    public ControladorEditar(IGestionPlcTu gestionPlcTu, EditarPlc editarPlcForm) {
        this.gestionPlcTu = gestionPlcTu;
        this.editarPlcForm = editarPlcForm;
        editarPlcForm.btnGuardar.addActionListener(this);
        editarPlcForm.btnVolver.addActionListener(this);
        editarPlcForm.btnConsultar.addActionListener(this);
    }

    /**
     * Método para consultar un PLC.
     */
    public void consultarPlc(){
        try {
            // Obtener el ID del PLC seleccionado
            String idPlc = (String) editarPlcForm.cmbId.getSelectedItem();

            // Consultar el PLC con el ID seleccionado
            PlcTuDTO miPlc = gestionPlcTu.consultar(idPlc);

            // Mostrar los datos del PLC en los campos de texto
            editarPlcForm.tfNombre.setText(miPlc.getNombrePropetario());
            editarPlcForm.tfNumeroIdentificacion.setText(miPlc.getNumId());
            editarPlcForm.tfDireccion.setText(miPlc.getDireccionResidencia());
            editarPlcForm.tfFecha.setText(miPlc.getFechaRegistro());
            editarPlcForm.tfLectura.setText(String.valueOf(miPlc.getLectura()));
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorConsultar.class.getName()).log(Level.SEVERE, "Error al consultar PLC");
            JOptionPane.showMessageDialog(null, "No se pudo consultar el PLC", "Error", JOptionPane.ERROR_MESSAGE);
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,  "Error -> "  + exception.getLocalizedMessage());
        }
    }

    /**
     * Método para actualizar los datos de un PLC.
     */
    public void actualizarDatos(){
        try {
            // Obtener el ID del PLC seleccionado
            String idPlc = (String) editarPlcForm.cmbId.getSelectedItem();

            // Validación de campos no vacíos
            String nombre = validarStringVacio(editarPlcForm.tfNombre.getText(), "Nombre");
            String tipoId = validarStringVacio(Objects.requireNonNull(editarPlcForm.cmbTipoIdentificacion.getSelectedItem()).toString(), "Tipo de Identificación");
            String numId = validarStringVacio(editarPlcForm.tfNumeroIdentificacion.getText(), "Número de Identificación");
            String direccionResidencia = validarStringVacio(editarPlcForm.tfDireccion.getText(), "Dirección de Residencia");
            String fechaRegistro = validarStringVacio(editarPlcForm.tfFecha.getText(), "Fecha de Registro");
            String lecturaStr = validarStringVacio(editarPlcForm.tfLectura.getText(), "Lectura");

            // Conversión de datos
            int estrato = Integer.parseInt(validarStringVacio(Objects.requireNonNull(editarPlcForm.cmbEstrato.getSelectedItem()).toString(), "Estrato"));
            int lectura = Integer.parseInt(lecturaStr);

            // Creación del objeto PlcTuDTO
            PlcTuDTO nuevoPlc = new PlcTuDTO(nombre, tipoId, numId, direccionResidencia, estrato, fechaRegistro, lectura);
            // Actualizar el PLC con los datos ingresados
            if (gestionPlcTu.editar(idPlc, nuevoPlc) == null){
                JOptionPane.showMessageDialog(null, "No se actualizó el Plc", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null, "Plc actualizado correctamente");
            }

        } catch (RemoteException ex) {
            Logger.getLogger(ControladorConsultar.class.getName()).log(Level.SEVERE, "Error al consultar PLC");
            JOptionPane.showMessageDialog(null, "No se pudo consultar el PLC", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception exception){
            JOptionPane.showMessageDialog(null,  "Error -> "  + exception.getLocalizedMessage());
        }
    }

    /**
     * Método para validar que un campo no esté vacío.
     * @param valor Valor del campo.
     * @param campo Nombre del campo.
     * @return Valor del campo.
     */
    private String validarStringVacio(String valor, String campo) {
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
        return valor;
    }
}
