package org.alex.accesodatos.gui.secundarias;

import java.awt.Dimension;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.beans.LabelPropio;
import org.alex.libs.Util;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * JDialog encargado de la elección del tipo de informe.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class JOpcionInforme extends DialogPropio {
	private static final long serialVersionUID = 1L;
	public ComboPropio comboPropio;

	public JOpcionInforme() {
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (getOpcionSeleccionada() == 0)
					Util.setMensajeInformacion("Seleccione un tipo.");
				else
					dispose();
			}
		});
		setTitle("Exportar informe");
		setSize(new Dimension(300, 200));
		setLocationRelativeTo(this);

		LabelPropio lblSeleccioneUnTipo = new LabelPropio("Seleccione un tipo:");
		contentPanel.add(lblSeleccioneUnTipo);

		comboPropio = new ComboPropio();
		comboPropio.addItem("");
		comboPropio.addItem("General");
		comboPropio.addItem("Detallado");
		comboPropio.addItem("De empresa");
		comboPropio.addItem("Gráfico");
		contentPanel.add(comboPropio);

		setVisible(true);

	}
	
	public int getOpcionSeleccionada(){
		return comboPropio.getSelectedIndex();
	}

}
