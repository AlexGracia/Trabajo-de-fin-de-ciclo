package org.alex.accesodatos.beans;

import javax.swing.JTextField;

import org.alex.accesodatos.util.Constantes;

/**
 * JTextField personalizado.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class TextPropio extends JTextField {

	private static final long serialVersionUID = 1L;

	public TextPropio() {
		super();

		// Fuente
		setFont(Constantes.FUENTE);
	}

}
