package org.alex.accesodatos.gui;

import java.awt.Dimension;

import javax.swing.JLabel;

import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.util.Constantes;

/**
 * Clase que implementa un dialogo para confirmar acciones del usuario.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class JConfirmacion extends DialogPropio {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 * 
	 * @param titulo
	 */
	public JConfirmacion(String titulo) {

		setTitle(titulo);
		setLocationRelativeTo(null);
		setSize(new Dimension(180, 120));

		{
			JLabel lblestsSeguro = new JLabel("\u00BFEst\u00E1s seguro?");
			lblestsSeguro.setFont(Constantes.FUENTE);
			contentPanel.add(lblestsSeguro);
		}

		setVisible(true);

	}

}
