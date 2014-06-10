package org.alex.accesodatos.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import org.alex.accesodatos.util.Constantes;
import java.awt.Insets;

/**
 * Dialogo con el que se puede configurar la conexion a la base de datos.
 * 
 * @author Alex Gracia
 * 
 */
public class JConfiguracion extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public JConfiguracion() {
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						JConfiguracion.class
								.getResource("/org/alex/accesodatos/iconos/IconoAplicacion.png")));
		setModal(true);
		setTitle("Configuracion");
		setSize(new Dimension(190, 84));
		setLocationRelativeTo(this);

		getContentPane().setLayout(new BorderLayout());

		JTextArea textArea = new JTextArea(
				"Por aquí, todo tranquilo.\nPuedes ir a descansar.");
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setOpaque(false);
		textArea.setFont(Constantes.FUENTE);
		textArea.setEditable(false);
		getContentPane().add(textArea, BorderLayout.CENTER);

		setVisible(true);
	}
}
