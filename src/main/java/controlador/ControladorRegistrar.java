package controlador;

import modelo.plc_mms.dto.PlcTuDTO;
import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import modelo.plc_mms.sop_rmi.IGestionUsuarios;
import vista.AgregarPlc;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ControladorRegistrar implements ActionListener {
    private final IGestionPlcTu gestionPlcTu;
    private final IGestionUsuarios gestionUsuarios;
    private final AgregarPlc agregarPlcForm;
    //int contador;

    public ControladorRegistrar(AgregarPlc agregarPlcForm, IGestionPlcTu gestionPlcTu, IGestionUsuarios gestionUsuarios) {
        this.agregarPlcForm = agregarPlcForm;
        this.gestionPlcTu = gestionPlcTu;
        this.gestionUsuarios = gestionUsuarios;
        agregarPlcForm.btnGuardar.addActionListener(this);
        agregarPlcForm.btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agregarPlcForm.btnGuardar) {
            crearPLC();
        }
        if (e.getSource() == agregarPlcForm.btnVolver) {
            agregarPlcForm.setVisible(false);
        }
    }
    public void crearPLC() {
        try {
            // Validación de campos no vacíos
            String nombre = validarStringVacio(agregarPlcForm.tfNombre.getText(), "Nombre");
            String tipoId = validarStringVacio(Objects.requireNonNull(agregarPlcForm.cmbTipoIdentificacion.getSelectedItem()).toString(), "Tipo de Identificación");
            String numId = validarStringVacio(agregarPlcForm.tfNumeroIdentificacion.getText(), "Número de Identificación");
            String direccionResidencia = validarStringVacio(agregarPlcForm.tfDireccion.getText(), "Dirección de Residencia");
            String fechaRegistro = validarStringVacio(agregarPlcForm.tfFecha.getText(), "Fecha de Registro");
            String lecturaStr = validarStringVacio(agregarPlcForm.tfLectura.getText(), "Lectura");

            // Conversión de datos
            int estrato = Integer.parseInt(validarStringVacio(Objects.requireNonNull(agregarPlcForm.cmbEstrato.getSelectedItem()).toString(), "Estrato"));
            int lectura = Integer.parseInt(lecturaStr);

            // Creación del objeto PlcTuDTO
            PlcTuDTO plcTuDTO = new PlcTuDTO(nombre, tipoId, numId, direccionResidencia, estrato, fechaRegistro, lectura);

            // Creación del PLC_TU
            String id = gestionPlcTu.crear(plcTuDTO);

            // Mensaje de resultado
            String mensaje = id.isEmpty() ? "PLC_TU NO Creado" : "PLC_TU Creado con ID: " + id;
            JOptionPane.showMessageDialog(null, mensaje);

            // Envío de mensaje si corresponde
            if (gestionPlcTu.obtenerNum() == 4) {
                gestionUsuarios.enviarMensaje("La factura del PLC_TU id: " + id + " está lista, y ya puede ser consultada", 2);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Estrato tiene que ser numerico: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage());
        }
    }

    private String validarStringVacio(String valor, String campo) {
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacío");
        }
        return valor;
    }

}
