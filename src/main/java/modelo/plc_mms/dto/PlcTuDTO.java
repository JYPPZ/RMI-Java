/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.plc_mms.dto;

import java.io.Serializable;

/**
 * Clase que representa un PLC_TU
 *
 */
public class PlcTuDTO implements Serializable {
    private String id;  
    private String nombrePropetario;
    private String tipoId;
    private String numId;
    private String direccionResidencia;
    private int estrato;
    private String fechaRegistro;
    private int lectura;

    public PlcTuDTO(){
        
    }

    public PlcTuDTO(String nombrePropetario, String tipoId, String numId, String direccionResidencia, int estrato, String fechaRegistro, int lectura) {
        this.nombrePropetario = nombrePropetario;
        this.tipoId = tipoId;
        this.numId = numId;
        this.direccionResidencia = direccionResidencia;
        this.estrato = estrato;
        this.fechaRegistro = fechaRegistro;
        this.lectura = lectura;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrePropetario() {
        return nombrePropetario;
    }

    public void setNombrePropetario(String nombrePropetario) {
        this.nombrePropetario = nombrePropetario;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getLectura() {
        return lectura;
    }

    public void setLectura(int lectura) {
        this.lectura = lectura;
    }



    @Override
    public String toString() {
        return "PlcTuDTO{" + "id=" + id + ", nombrePropetario=" + nombrePropetario + ", tipoId=" + tipoId + ", numId=" + numId + ", direccionResidencia=" + direccionResidencia + ", estrato=" + estrato + ", fechaRegistro=" + fechaRegistro + ", lectura=" + lectura + '}';
    }
    
    
    
    
    
}
