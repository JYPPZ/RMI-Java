package controlador;

import modelo.grsaa.dto.FacturacionDTO;
import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import modelo.plc_mms.sop_rmi.IGestionUsuarios;
import vista.ConsultarMes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorConsultarMes implements ActionListener {
    private final IGestionPlcTu gestionPlcTu;
    private final IGestionUsuarios gestionUsuarios;
    private final ConsultarMes consultarMesForm;
    public ControladorConsultarMes(IGestionPlcTu gestionPlcTu, IGestionUsuarios gestionUsuarios, ConsultarMes consultarMesForm) {
        this.gestionPlcTu = gestionPlcTu;
        this.gestionUsuarios = gestionUsuarios;
        this.consultarMesForm = consultarMesForm;
        consultarMesForm.btnConsultar.addActionListener(this);
        consultarMesForm.btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultarMesForm.btnVolver){
            consultarMesForm.dispose();
        }if (e.getSource() == consultarMesForm.btnConsultar){
            consultarFactura();
        }
    }
    public void consultarFactura(){
        try {
            FacturacionDTO fac = new FacturacionDTO();
            List<String> listaIds;
            listaIds = gestionPlcTu.devolverIds(0);
            fac = gestionPlcTu.facturarMesUsuario(listaIds.get(0));
            gestionUsuarios.enviarMensaje(" consulta PLC_TU con id:" + listaIds.get(0), 0);

            consultarMesForm.lblDireccionResidencia.setText(fac.getDireccionResidencia());
            consultarMesForm.lblFechaConsulta.setText(fac.getFechaRegistro());
            consultarMesForm.lblLecturaFinal.setText(String.valueOf(fac.getLecturaFinal()));
            consultarMesForm.lblLectura.setText(String.valueOf(fac.getLecturaInical()));
            consultarMesForm.lblValorConsumo.setText(String.valueOf(fac.getValorConsumo()));
        } catch (Exception e) {
            System.out.println("Error al consultar factura" + e.getLocalizedMessage());
        }
    }
}
