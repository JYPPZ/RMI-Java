/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.plc_tu.sop_rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallback extends Remote{
    
    public String notificar(String mensaje) throws RemoteException;
 
    public String getMensaje()  throws RemoteException;
}
