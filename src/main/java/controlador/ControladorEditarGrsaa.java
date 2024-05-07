package controlador;

import modelo.grsaa.sop_rmi.IGestionConsumoPlc;
import modelo.plc_mms.dto.PlcTuDTO;
import vista.EditarPlcGrsaa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorEditarGrsaa implements ActionListener {
    private final IGestionConsumoPlc gestionConsumoPlc;
    private final EditarPlcGrsaa editarPlcGrsaaForm;

    public ControladorEditarGrsaa(IGestionConsumoPlc gestionConsumoPlc, EditarPlcGrsaa editarPlcGrsaaForm) {
        this.gestionConsumoPlc = gestionConsumoPlc;
        this.editarPlcGrsaaForm = editarPlcGrsaaForm;
        editarPlcGrsaaForm.btnGuardar.addActionListener(this);
        editarPlcGrsaaForm.btnVolver.addActionListener(this);
        editarPlcGrsaaForm.btnConsultar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editarPlcGrsaaForm.btnGuardar){
            actualizarDatos();
        }
        if (e.getSource() == editarPlcGrsaaForm.btnConsultar){
            consultarPlc();
        }
        if (e.getSource() == editarPlcGrsaaForm.btnVolver){
            editarPlcGrsaaForm.dispose();
        }
    }
    /**
     * Método para consultar un PLC.
     */
    public void consultarPlc(){
        try {
            // Obtener el ID del PLC seleccionado
            String idPlc = (String) editarPlcGrsaaForm.cmbId.getSelectedItem();

            // Consultar el PLC con el ID seleccionado
            PlcTuDTO miPlc = gestionConsumoPlc.consultar(idPlc);

            // Mostrar los datos del PLC en los campos de texto
            editarPlcGrsaaForm.tfNombre.setText(miPlc.getNombrePropetario());
            editarPlcGrsaaForm.tfNumeroIdentificacion.setText(miPlc.getNumId());
            editarPlcGrsaaForm.tfDireccion.setText(miPlc.getDireccionResidencia());
            editarPlcGrsaaForm.tfFecha.setText(miPlc.getFechaRegistro());
            editarPlcGrsaaForm.tfLectura.setText(String.valueOf(miPlc.getLectura()));
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
            String idPlc = (String) editarPlcGrsaaForm.cmbId.getSelectedItem();

            // Validación de campos no vacíos
            String nombre = validarStringVacio(editarPlcGrsaaForm.tfNombre.getText(), "Nombre");
            String tipoId = validarStringVacio(Objects.requireNonNull(editarPlcGrsaaForm.cmbTipoIdentificacion.getSelectedItem()).toString(), "Tipo de Identificación");
            String numId = validarStringVacio(editarPlcGrsaaForm.tfNumeroIdentificacion.getText(), "Número de Identificación");
            String direccionResidencia = validarStringVacio(editarPlcGrsaaForm.tfDireccion.getText(), "Dirección de Residencia");
            String fechaRegistro = validarStringVacio(editarPlcGrsaaForm.tfFecha.getText(), "Fecha de Registro");
            String lecturaStr = validarStringVacio(editarPlcGrsaaForm.tfLectura.getText(), "Lectura");

            // Conversión de datos
            int estrato = Integer.parseInt(validarStringVacio(Objects.requireNonNull(editarPlcGrsaaForm.cmbEstrato.getSelectedItem()).toString(), "Estrato"));
            int lectura = Integer.parseInt(lecturaStr);

            // Creación del objeto PlcTuDTO
            PlcTuDTO nuevoPlc = new PlcTuDTO(nombre, tipoId, numId, direccionResidencia, estrato, fechaRegistro, lectura);
            // Actualizar el PLC con los datos ingresados
            if (gestionConsumoPlc.editar(idPlc, nuevoPlc) == null){
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
