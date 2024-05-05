package controlador;

import vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorPanel implements ActionListener {
    private final PanelPrincipal panelPrincipalForm;
    private final AgregarPlc agregarPlcForm;
    private final ConsultarPlc consultarPlcForm;
    private final EditarPlc editarPlcForm;
    private final EliminarPlc eliminarPlcForm;

    public ControladorPanel(PanelPrincipal panelPrincipalForm, AgregarPlc agregarPlcForm, ConsultarPlc consultarPlcForm, EditarPlc editarPlcForm, EliminarPlc eliminarPlcForm) {
        this.panelPrincipalForm = panelPrincipalForm;
        this.agregarPlcForm = agregarPlcForm;
        this.consultarPlcForm = consultarPlcForm;
        this.editarPlcForm = editarPlcForm;
        this.eliminarPlcForm = eliminarPlcForm;
        panelPrincipalForm.btnRegistrar.addActionListener(this);
        panelPrincipalForm.btnConsultar.addActionListener(this);
        panelPrincipalForm.btnSalir.addActionListener(this);
        panelPrincipalForm.btnEditar.addActionListener(this);
        panelPrincipalForm.btnEliminar.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelPrincipalForm.btnRegistrar) {
            agregarPlcForm.setVisible(true);
        }
        if (e.getSource() == panelPrincipalForm.btnConsultar) {
            consultarPlcForm.setVisible(true);
        }
        if (e.getSource() == panelPrincipalForm.btnSalir) {
            System.exit(0);
        }
        if (e.getSource() == panelPrincipalForm.btnEditar) {
            editarPlcForm.setVisible(true);
        }
        if (e.getSource() == panelPrincipalForm.btnEliminar) {
            eliminarPlcForm.setVisible(true);
        }
    }
}
