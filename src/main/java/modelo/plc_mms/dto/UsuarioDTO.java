package modelo.plc_mms.dto;

import modelo.plc_tu.sop_rmi.ICallback;

import java.io.Serializable;


public class UsuarioDTO implements Serializable {
    
    private int id;
    private String nombreCompleto;
    private String usuario;
    private String clave;
    private ICallback callback;

    public UsuarioDTO(int id, String usuario, String clave) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
    }

    public UsuarioDTO(int id, String nombreCompleto, String usuario, String clave) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.clave = clave;
    }

    public UsuarioDTO() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public ICallback getCallback() {
        return callback;
    }

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

}
