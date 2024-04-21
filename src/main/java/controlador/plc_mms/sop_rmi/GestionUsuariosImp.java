package controlador.plc_mms.sop_rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.plc_mms.dto.UsuarioDTO;
import modelo.plc_mms.sop_rmi.IGestionUsuarios;
import modelo.plc_tu.sop_rmi.ICallback;

import javax.swing.*;

public class GestionUsuariosImp extends UnicastRemoteObject implements IGestionUsuarios {

    private final Map<Integer, UsuarioDTO> usuariosRegistrados;

    public GestionUsuariosImp() throws RemoteException {
        super();
        usuariosRegistrados = new HashMap<>();
        usuariosRegistrados.put(0, new UsuarioDTO(0, "Operador", "admin-oper", "admin-oper"));
        usuariosRegistrados.put(1, new UsuarioDTO(1, "Administrador", "admin", "admin"));
        usuariosRegistrados.put(2,new UsuarioDTO(2, "invitado", "invitado", "invitado"));
    }


    @Override
    public int abrirSesion(UsuarioDTO objUsuario) throws RemoteException {
        System.out.println("Intento de inicio de sesión: " + objUsuario.getUsuario());
        UsuarioDTO usuarioRegistrado = usuariosRegistrados.get(objUsuario.getId());
        if (usuarioRegistrado != null &&
                usuarioRegistrado.getUsuario().equalsIgnoreCase(objUsuario.getUsuario()) &&
                objUsuario.getClave().equals(usuarioRegistrado.getClave())) {
            return usuarioRegistrado.getId();
        }
        return -1;
    }

    @Override
    public UsuarioDTO consultarUsuario(int id) throws RemoteException {
        if (usuariosRegistrados.get(id) == null){
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }
        return usuariosRegistrados.get(id);
    }
    
        @Override
    public void enviarMensaje(String mensaje, int id) throws RemoteException {
        System.out.println("EnviarMensaje");
        notificarUsuarios(mensaje, id);
    }
    
    private void notificarUsuarios(String mensaje, int id) throws RemoteException {
        System.out.println("notificarUsuarios(): Invocando al mEtodo notificar usuarios desde el servidor");
        for (UsuarioDTO objUsuario : usuariosRegistrados.values()) {
            if(objUsuario.getId() != id){
                objUsuario.getCallback().notificar(mensaje);
            }
        }
    }

    @Override
    public synchronized boolean registrarUsuario(UsuarioDTO usuario) throws RemoteException {
        System.out.println("registrarUsuario()");
        if (usuariosRegistrados.containsKey(usuario.getId()) &&
                usuariosRegistrados.put(usuario.getId(), usuario) == null) {
            JOptionPane.showMessageDialog(null, "Usuario ya registrado");
            return false;
        }
        //usuariosRegistrados.put(usuario.getId(), usuario);
        return true;
    }

}
