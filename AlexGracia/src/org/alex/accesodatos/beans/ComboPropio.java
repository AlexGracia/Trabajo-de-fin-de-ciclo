package org.alex.accesodatos.beans;

import javax.swing.JComboBox;

import org.alex.accesodatos.util.Constantes;

public class ComboPropio extends JComboBox<String> {

	private static final long serialVersionUID = 1L;

	public ComboPropio() {
		super();

		// Fuente
		setFont(Constantes.FUENTE);
	}

	public String getSelectedString() {
		return getSelectedItem().toString();
	}

}
