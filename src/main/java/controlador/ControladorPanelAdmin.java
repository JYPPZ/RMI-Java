package controlador;

import controlador.grsaa.utilidades.UtilidadesRegistroC;
import modelo.grsaa.sop_rmi.IGestionConsumoPlc;
import modelo.plc_mms.dto.UsuarioDTO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class ControladorPanelAdmin implements ActionListener {
    private IGestionConsumoPlc gestionConsumoPlc;
    private final UsuarioDTO user;

    public ControladorPanelAdmin(UsuarioDTO user) throws RemoteException {
        this.user = user;
        iniciar();
    }

    /**
     * Método el objeto remoto
     * @return Objeto remoto
     */
    public IGestionConsumoPlc getGestionConsumoPlc() {
        return gestionConsumoPlc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Método para iniciar la conexión con el servidor.
     * @throws RemoteException Excepción de conexión.
     */
    public void iniciar() throws RemoteException {
        String direccionIpRMIRegistry = "localhost";
        int numPuertoRMIRegistry = 2025;

        gestionConsumoPlc = (IGestionConsumoPlc) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry, numPuertoRMIRegistry,
                "GestionConsumoPlc");
        JOptionPane.showMessageDialog(null, user.getCallback().getMensaje());
    }
}
