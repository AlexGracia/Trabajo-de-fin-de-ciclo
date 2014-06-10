package org.alex.accesodatos.beans;

import javax.swing.JLabel;

import org.alex.accesodatos.util.Constantes;

public class LabelPropio extends JLabel {

	private static final long serialVersionUID = 1L;

	public LabelPropio(String texto) {
		super(texto);
		setFont(Constantes.FUENTE);
	}

}
