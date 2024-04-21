/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controlador.plc_mms;


import java.rmi.RemoteException;
import controlador.plc_mms.sop_rmi.GestionPlcTuImp;
import controlador.plc_mms.utilidades.UtilidadesRegistroS;

/**
 *
 * @author ideapad330S
 */
public class ServidorNodos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
         int numPuertoRMIRegistry;
        String direccionIpRMIRegistry;
        direccionIpRMIRegistry = "localhost";
        numPuertoRMIRegistry = 2025;

       GestionPlcTuImp objRemoto = new GestionPlcTuImp();
       objRemoto.consultarReferenciaRemota(direccionIpRMIRegistry, 2025);
        try {
            UtilidadesRegistroS.arrancarNS(numPuertoRMIRegistry);
            UtilidadesRegistroS.RegistrarObjetoRemoto(objRemoto, direccionIpRMIRegistry, numPuertoRMIRegistry,
                    "GestionPLC_TU");

        } catch (Exception e) {
            System.err.println("No fue posible Arrancar el NS o Registrar el objeto remoto" + e.getMessage());
        }
    }
    
}
