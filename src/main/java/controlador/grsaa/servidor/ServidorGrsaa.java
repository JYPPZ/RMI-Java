package controlador.grsaa.servidor;

import controlador.grsaa.sop_rmi.GestionConsumoPlc;
import controlador.grsaa.utilidades.UtilidadesRegistroS;

import java.rmi.RemoteException;

public class ServidorGrsaa {
    public static void main(String[] args) throws RemoteException {
        int numPuertoNS;
        String direccionNS;

        System.out.println("Cual es el la dirección ip donde se encuentra  el n_s");
        direccionNS = "localhost";
        System.out.println("Cual es el número de puerto por el cual escucha el n_s");
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
