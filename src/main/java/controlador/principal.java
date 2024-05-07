package controlador;

import controlador.plc_mms.sop_rmi.IniciarSesion;
import modelo.plc_mms.dto.UsuarioDTO;
import vista.*;

import java.rmi.RemoteException;

public class principal {
    public static void main(String[] args) throws RemoteException {
        // Crear la vista de la clase ClienteSesion
        ClienteSesion clienteSesionForm = new ClienteSesion();
        clienteSesionForm.setVisible(true); //visualizar la ventana login
        PanelCeo panelCeoForm = new PanelCeo(); // panel Ceo
        PanelAdmin panelAdminForm = new PanelAdmin(); // panel Admin
        IniciarSesion iniciarSesion = new IniciarSesion(clienteSesionForm, panelCeoForm, panelAdminForm); // inicializar el controlador principal

    }
}
