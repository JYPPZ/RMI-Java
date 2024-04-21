
package controlador.plc_mms;

import controlador.plc_mms.utilidades.UtilidadesRegistroS;
import java.rmi.RemoteException;
import controlador.plc_mms.sop_rmi.*;

public class ServidorDeObjetos {
    
     public static void main(String[] args) throws RemoteException {

        int numPuertoRMIRegistry;
        String direccionIpRMIRegistry;
        direccionIpRMIRegistry = "localhost";
        numPuertoRMIRegistry = 2024;

       GestionUsuariosImp objRemoto = new GestionUsuariosImp();

        try {
            UtilidadesRegistroS.arrancarNS(numPuertoRMIRegistry);
            UtilidadesRegistroS.RegistrarObjetoRemoto(objRemoto, direccionIpRMIRegistry, numPuertoRMIRegistry,
                    "GestionUsuarios");

        } catch (Exception e) {
            System.err.println("No fue posible Arrancar el NS o Registrar el objeto remoto" + e.getMessage());
        }

    }
}
