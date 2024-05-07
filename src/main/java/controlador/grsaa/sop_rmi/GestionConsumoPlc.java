package controlador.grsaa.sop_rmi;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import controlador.plc_mms.sop_rmi.GestionPlcTuImp;
import modelo.grsaa.dto.FacturacionDTO;
import modelo.plc_mms.dto.PlcTuDTO;
import modelo.grsaa.sop_rmi.IGestionConsumoPlc;


public class GestionConsumoPlc extends UnicastRemoteObject implements IGestionConsumoPlc {

    private List<PlcTuDTO> listaPlcs = new ArrayList<>();

    public GestionConsumoPlc() throws RemoteException {

    }

    @Override
    public List<PlcTuDTO> enviarNotificacion(List<PlcTuDTO> listaPlcs) throws RemoteException {
        if (listaPlcs == null) {
            throw new IllegalArgumentException("La lista de PLCs no puede ser null");
        }

        this.listaPlcs = new ArrayList<>(listaPlcs); // Hacer una copia de la lista
        for (PlcTuDTO plc : this.listaPlcs) {
            System.out.println("Nodo enviado: " + plc);
            guardarTxt(plc.getId());
        }
        return this.listaPlcs;
    }

    /**}
     * Guarda un objeto PlcTuDTO en un archivo de texto.
     * @param id Identificador del PLC a guardar
     * @throws RemoteException Excepción de conexión
     */
    @Override
    public void guardarTxt(String id) throws RemoteException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El identificador no puede ser nulo o vacío");
        }

        PlcTuDTO objeto = consultar(id);
        String digitos = objeto.getId().substring(objeto.getId().length() - 2);
        String nombreBaseArchivo = "id" + String.format("%02d", 1) + "_" + "id" + digitos;

        // Generar nombre de archivo único con marca de tiempo
        String nombreArchivo = nombreBaseArchivo + "_" + SimpleDateFormat.DATE_FIELD + ".txt";
        String rutaDirectorio = "src/main/java/txts/";
        try {
            FileWriter escritor = getFileWriter(rutaDirectorio, nombreArchivo, objeto);
            escritor.close();
            System.out.println("Objeto guardado en: " + nombreArchivo);
        } catch (IOException e) {
            Logger.getLogger(GestionConsumoPlc.class.getName()).log(Level.SEVERE, "Error al guardar el archivo", e);
        }
    }

    private static FileWriter getFileWriter(String rutaDirectorio, String nombreArchivo, PlcTuDTO objeto) throws IOException {
        FileWriter escritor = null;
        try {
            File archivo = new File(rutaDirectorio + nombreArchivo);
            escritor = new FileWriter(archivo);

            escritor.write("ID: " + objeto.getId() + "\n");
            escritor.write("Nombre propietario: " + objeto.getNombrePropetario() + "\n");
            escritor.write("Tipo Id: " + objeto.getTipoId() + "\n");
            escritor.write("Numero de identificación: " + objeto.getNumId() + "\n");
            escritor.write("Dirección: " + objeto.getDireccionResidencia() + "\n");
            escritor.write("Estrato: " + objeto.getEstrato() + "\n");
            escritor.write("Fecha: " + objeto.getFechaRegistro() + "\n");
            escritor.write("Lectura: " + objeto.getLectura() + "\n");

            escritor.close();
            System.out.println("Objeto guardado en: " + nombreArchivo);
        } catch (IOException e) {
            Logger.getLogger(GestionConsumoPlc.class.getName()).log(Level.SEVERE, "Error al guardar el archivo", e);

        }
        return escritor;
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
                GestionPlcTuImp.editar(objPlc, plc);
                guardarTxt(plc.getId());
                return plc;
            }
        }
        return null;
    }

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

    @Override
    public List<String> devolverIds() throws RemoteException {
        List<String> ids = new ArrayList<>();
        for (PlcTuDTO plc : listaPlcs) {
            ids.add(plc.getId());
        }
        return ids;
    }

    public int calcularCosto(int lecturaFinal, int lecturaAnterior, int estrato) {
        if (lecturaAnterior > lecturaFinal) {
            return 0;
        }

        int valor = (lecturaFinal - lecturaAnterior);

        switch (estrato) {
            case 1 -> {
                valor = valor * 420;
            }

            case 2 -> {
                valor = valor * 525;
            }

            case 3 -> {
                valor = valor * 852;
            }

            default -> {
                valor = valor * 1002;
            }
        }
        return valor;
    }

    @Override
    public FacturacionDTO facturarMes() throws RemoteException {
        FacturacionDTO fac = new FacturacionDTO();
        int tamanio = listaPlcs.size() - 1;
        fac.setId(listaPlcs.get(0).getNumId());
        fac.setDireccionResidencia(listaPlcs.get(tamanio).getDireccionResidencia());
        fac.setFechaRegistro(listaPlcs.get(tamanio).getFechaRegistro());
        fac.setLecturaInical(listaPlcs.get(0).getLectura());
        int lecturaFinal = 0;
        for (PlcTuDTO listaPlc : listaPlcs) {
            lecturaFinal += listaPlc.getLectura();
        }
        fac.setLecturaFinal(lecturaFinal);
        int costo;
        costo = calcularCosto(lecturaFinal, listaPlcs.get(0).getLectura(), listaPlcs.get(0).getEstrato());
        System.out.println("cost: "+costo + " lectura: "+ lecturaFinal);
        fac.setValorConsumo(costo);
        return fac;
    }

    @Override
    public List<PlcTuDTO> devolverListaActual() throws RemoteException {
        return listaPlcs;
    }

}
