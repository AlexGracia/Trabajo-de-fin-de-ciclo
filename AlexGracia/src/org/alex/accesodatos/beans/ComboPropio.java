package org.alex.accesodatos.beans;

import javax.swing.JComboBox;

import org.alex.accesodatos.util.Constantes;

/**
 * JComboBox personalizado.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class ComboPropio extends JComboBox<String> {

	private static final long serialVersionUID = 1L;

	public ComboPropio() {
		super();

		// Fuente
		setFont(Constantes.FUENTE);
	}

	/**
	 * 
	 * @return Devuelve el item seleccionado convertido a String.
	 */
	public String getSelectedString() {
		return getSelectedItem().toString();
	}

}
