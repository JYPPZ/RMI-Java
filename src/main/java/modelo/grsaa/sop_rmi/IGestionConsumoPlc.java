package modelo.grsaa.sop_rmi;

import modelo.grsaa.dto.FacturacionDTO;
import modelo.plc_mms.dto.PlcTuDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGestionConsumoPlc extends Remote {

    List<PlcTuDTO> enviarNotificacion(List<PlcTuDTO> listaPlcs) throws RemoteException;
    void guardarTxt(String id) throws RemoteException;
    List<PlcTuDTO> devolverListaActual()throws RemoteException;
    PlcTuDTO consultar(String id) throws RemoteException;
    PlcTuDTO editar(String id, PlcTuDTO objPlc) throws RemoteException;
    boolean eliminar(String id) throws RemoteException;
    List<String> devolverIds() throws RemoteException;
    FacturacionDTO facturarMes() throws RemoteException;
}
