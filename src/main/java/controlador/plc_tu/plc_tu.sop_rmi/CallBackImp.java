package controlador.plc_tu.plc_tu.sop_rmi;

import modelo.plc_tu.sop_rmi.ICallback;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallBackImp extends UnicastRemoteObject implements ICallback {

    private String mensaje;

    public CallBackImp() throws RemoteException {
    }

    @Override
    public String notificar(String mensaje) throws RemoteException {
        this.mensaje = mensaje;
        System.out.println(mensaje);
        return mensaje;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }

}
