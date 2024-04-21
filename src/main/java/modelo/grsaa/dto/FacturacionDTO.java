package modelo.grsaa.dto;

import java.io.Serializable;

public class FacturacionDTO implements Serializable {

    private String id;

    private String direccionResidencia;
    private String fechaRegistro;
    private int lecturaInical;
    private int lecturaFinal;
    private int valorConsumo;

    public FacturacionDTO() {
    }

    public FacturacionDTO(String direccionResidencia, String fechaRegistro, int lecturaInical, int lecturaFinal, int valorConsumo) {
        this.direccionResidencia = direccionResidencia;
        this.fechaRegistro = fechaRegistro;
        this.lecturaInical = lecturaInical;
        this.lecturaFinal = lecturaFinal;
        this.valorConsumo = valorConsumo;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = direccionResidencia;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getLecturaInical() {
        return lecturaInical;
    }

    public void setLecturaInical(int lecturaInical) {
        this.lecturaInical = lecturaInical;
    }

    public int getLecturaFinal() {
        return lecturaFinal;
    }

    public void setLecturaFinal(int lecturaFinal) {
        this.lecturaFinal = lecturaFinal;
    }

    public int getValorConsumo() {
        return valorConsumo;
    }

    public void setValorConsumo(int valorConsumo) {
        this.valorConsumo = valorConsumo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}