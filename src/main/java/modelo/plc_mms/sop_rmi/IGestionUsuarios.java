/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.plc_mms.sop_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import modelo.plc_mms.dto.UsuarioDTO;

/**
 *
 * @author ideapad330S
 */
public interface IGestionUsuarios extends Remote{
    int abrirSesion(UsuarioDTO objUsuario) throws RemoteException;
    UsuarioDTO consultarUsuario(int id) throws RemoteException;
    
    void enviarMensaje(String mensaje, int id)throws RemoteException;
    boolean registrarUsuario(UsuarioDTO usuario) throws RemoteException;
    
}
