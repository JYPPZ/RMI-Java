package controlador.grsaa.servidor;

import controlador.grsaa.sop_rmi.GestionConsumoPlc;
import controlador.grsaa.utilidades.UtilidadesRegistroS;

import java.rmi.RemoteException;

public class ServidorGrsaa {
    public static void main(String[] args) throws RemoteException {
        int numPuertoNS;
        String direccionNS;

        direccionNS = "localhost";
        numPuertoNS = 2025;

        GestionConsumoPlc objRemoto = new GestionConsumoPlc();

        try {
            UtilidadesRegistroS.arrancarNS(direccionNS, numPuertoNS);
            UtilidadesRegistroS.RegistrarObjetoRemoto(objRemoto, direccionNS, numPuertoNS, "GestionConsumoPlc");
        } catch (Exception e) {
            System.err.println("No fue posible Arrancar el NS o Registrar el objeto remoto" + e.getMessage());
        }
    }
}
