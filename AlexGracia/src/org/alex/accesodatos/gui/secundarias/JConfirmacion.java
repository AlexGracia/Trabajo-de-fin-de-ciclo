package org.alex.accesodatos.gui.secundarias;

import java.awt.Dimension;

import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.beans.LabelPropio;

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
		okButtonDispose();
		setTitle(titulo);
		setSize(new Dimension(170, 120));
		setLocationRelativeTo(this);

		LabelPropio lblestsSeguro = new LabelPropio("\u00BFEst\u00E1s seguro?");
		contentPanel.add(lblestsSeguro);

		setVisible(true);
	}

}
