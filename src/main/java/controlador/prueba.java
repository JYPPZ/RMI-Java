package controlador;

import vista.PanelPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prueba implements ActionListener {

    private PanelPrincipal miPanel;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miPanel.btnConsultar) {
            System.out.println("Consultar");
        }
        if (e.getSource() == miPanel.btnSalir) {
            System.out.println("Salir");
            System.exit(0);
        }
    }


    public prueba(PanelPrincipal miPanel) {
        this.miPanel = miPanel;
        this.miPanel.btnConsultar.addActionListener(this);
        this.miPanel.btnSalir.addActionListener(this);
    }

    public static void main(String[] args) {
        PanelPrincipal miPanel = new PanelPrincipal();
        prueba miPrueba = new prueba(miPanel);
        miPanel.setVisible(true);
    }

}
