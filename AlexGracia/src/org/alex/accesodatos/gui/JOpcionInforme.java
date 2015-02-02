package org.alex.accesodatos.gui;

import java.awt.Dimension;
import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.beans.LabelPropio;

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
		setTitle("Exportar informe");
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(null);

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

}
