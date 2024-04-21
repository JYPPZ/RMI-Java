package modelo.plc_mms.sop_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.grsaa.dto.FacturacionDTO;
import modelo.plc_mms.dto.PlcTuDTO;
/**
 *
 * @author ideapad330S
 */
public interface IGestionPlcTu extends Remote{
    
    String crear(PlcTuDTO objPlc) throws RemoteException;
    PlcTuDTO  consultar(String id) throws RemoteException;
    PlcTuDTO  editar(String id, PlcTuDTO objPlc) throws RemoteException;
    boolean eliminar(String id) throws RemoteException;
    List<String> devolverIds(int num)throws RemoteException;
    FacturacionDTO facturarMesUsuario(String id) throws RemoteException;
    List<PlcTuDTO> listaDePlcs() throws RemoteException;
    List<String> consultaActual(String numId)  throws RemoteException;
    int obtenerNum() throws RemoteException;

}
