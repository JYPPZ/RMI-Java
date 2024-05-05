package controlador.plc_mms.sop_rmi;

import controlador.plc_tu.utilidades.UtilidadesRegistroC;
import modelo.grsaa.dto.FacturacionDTO;
import modelo.grsaa.sop_rmi.IGestionConsumoPlc;
import modelo.plc_mms.dto.PlcTuDTO;
import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestionPlcTuImp extends UnicastRemoteObject implements IGestionPlcTu {

    //private static int contador = 1;
    private List<PlcTuDTO> listaPlcs = new ArrayList<>();
    private IGestionConsumoPlc gestionConsumo;

    public GestionPlcTuImp() throws RemoteException {
    }

    @Override
    public String crear(PlcTuDTO objPlc) throws RemoteException {
        objPlc.setId(generarID());
        listaPlcs.add(objPlc);
        if (listaPlcs.size() >= 4) {
            enviarInfo();
        }
        return objPlc.getId();
    }

    @Override
    public PlcTuDTO consultar(String id) throws RemoteException {
        for (PlcTuDTO plc : listaPlcs) {
            if (plc.getId().equals(id)) {
                return plc;
            }
        }
        return null;
    }

    @Override
    public PlcTuDTO editar(String id, PlcTuDTO objPlc) throws RemoteException {
        for (PlcTuDTO plc : listaPlcs) {
            if (plc.getId().equals(id)) {
                plc.setEstrato(objPlc.getEstrato());
                plc.setFechaRegistro(objPlc.getFechaRegistro());
                plc.setLectura(objPlc.getLectura());
                plc.setNombrePropetario(objPlc.getNombrePropetario());
                plc.setTipoId(objPlc.getTipoId());
                plc.setNumId(objPlc.getNumId());
                plc.setDireccionResidencia(objPlc.getDireccionResidencia());
                return plc;
            }
        }
        return null;
    }

    /**
     * Método para eliminar un PLC de la lista.
     * @param id ID del PLC a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     * @throws RemoteException
     */
    @Override
    public boolean eliminar(String id) throws RemoteException {
        for (int i = 0; i < listaPlcs.size(); i++) {
            if (listaPlcs.get(i).getId().equals(id)) {
                listaPlcs.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Método para generar un ID aleatorio para los PLCs.
     * @return ID generado.
     */
    public static String generarID() {
        String prefijo = "plc_tu";
        int numero = new Random().nextInt(99) + 1; // Genera un número aleatorio entre 1 y 99
        String numeroFormateado = String.format("%02d", numero); // Formatea el número para que tenga dos dígitos
        return prefijo + numeroFormateado;
    }

    @Override
    public List<String> devolverIds(int num) throws RemoteException {
        List<String> ids = new ArrayList<>();
        if (num == 1) {
            ids.add(listaPlcs.get(listaPlcs.size() - 1).getId());
        } else {
            for (PlcTuDTO plc : listaPlcs) {
                ids.add(plc.getId());
            }
        }
        return ids;
    }

    @Override
    public FacturacionDTO facturarMesUsuario(String id) throws RemoteException {
        if (id.equals(listaPlcs.get(0).getNumId())) {
            return gestionConsumo.facturarMes();
        }
        return null;
    }

    @Override
    public List<PlcTuDTO> listaDePlcs() throws RemoteException {
        return listaPlcs;
    }

    @Override
    public List<String> consultaActual(String numId) throws RemoteException {
        List<String> actual = new ArrayList<>();
        int total = 0;
        for (PlcTuDTO listaPlc : listaPlcs) {
            total += listaPlc.getLectura();
        }
        actual.add(listaPlcs.get(0).getDireccionResidencia());
        actual.add(listaPlcs.get(0).getFechaRegistro());
        actual.add(String.valueOf(total));
        actual.add(listaPlcs.get(0).getNumId());
        return actual;
    }

    public void consultarReferenciaRemota(String direccionIpRMIRegistry, int numPuertoRMIRegistry) {
        System.out.println(" ");
        System.out.println("Desde consultarReferenciaRemotaDeNotificacion()...");
        this.gestionConsumo = (IGestionConsumoPlc) UtilidadesRegistroC.obtenerObjRemoto(direccionIpRMIRegistry,
                numPuertoRMIRegistry, "GestionConsumoPlc");
    }

    public void enviarInfo() throws RemoteException {
        System.out.println("Enviar info nodos");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                this.listaPlcs = gestionConsumo.enviarNotificacion(listaPlcs);
            } catch (RemoteException ex) {
                Logger.getLogger(GestionPlcTuImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, 0, 240, TimeUnit.SECONDS);
        this.listaPlcs = gestionConsumo.devolverListaActual();
    }
    
    @Override
    public int obtenerNum(){
        return listaPlcs.size();
    }

}
