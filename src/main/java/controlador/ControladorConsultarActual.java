package controlador;

import modelo.plc_mms.sop_rmi.IGestionPlcTu;
import modelo.plc_mms.sop_rmi.IGestionUsuarios;
import vista.ConsultarActual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

public class ControladorConsultarActual implements ActionListener {
    private final IGestionPlcTu gestionPlcTu;
    private final IGestionUsuarios gestionUsuarios;
    private final ConsultarActual consultarActualForm;

    public ControladorConsultarActual(IGestionPlcTu gestionPlcTu, IGestionUsuarios gestionUsuarios, ConsultarActual consultarActualForm) {
        this.gestionPlcTu = gestionPlcTu;
        this.gestionUsuarios = gestionUsuarios;
        this.consultarActualForm = consultarActualForm;
        consultarActualForm.btnConsultar.addActionListener(this);
        consultarActualForm.btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consultarActualForm.btnVolver){
            consultarActualForm.dispose();
        }if (e.getSource() == consultarActualForm.btnConsultar){
            consultarInfoMesa();
        }
    }
    public void consultarInfoMesa(){
        try {
            List<String> id;
            List<String> actual;
            id = gestionPlcTu.devolverIds(0);
            consultarActualForm.lblId.setText(id.get(0));
            actual = gestionPlcTu.consultaActual(id.get(0));
            consultarActualForm.lblDireccion.setText(actual.get(0));
            consultarActualForm.lblFechaConsulta.setText(actual.get(1));
            consultarActualForm.lblLectura.setText(actual.get(2));
            this.gestionUsuarios.enviarMensaje("Usuario id= "+ actual.get(3) +" consulta PLC_TU con id:" + id,0);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
