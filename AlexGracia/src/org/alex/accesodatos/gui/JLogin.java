package org.alex.accesodatos.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;

import net.miginfocom.swing.MigLayout;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.Util;

public class JLogin extends DialogPropio {
	private static final long serialVersionUID = 1L;

	// Variables
	private ComboPropio comboUsuario;
	private JPasswordField passwordField;
	private byte intentos = 1;
	private boolean isUser, isTecnic;

	/**
	 * Crea la ventana de login.
	 */
	public JLogin() {
		// Modificaciones a la ventana madre

		okButton.addActionListener(_comprobarLogin());
		setTitle("Login");
		setSize(new Dimension(200, 160));
		setLocationRelativeTo(this);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][]"));

		// Components
		LabelPropio lblprpUsuario = new LabelPropio("Usuario:");
		contentPanel.add(lblprpUsuario, "cell 0 0");

		comboUsuario = new ComboPropio();
		comboUsuario.addItem("user");
		comboUsuario.addItem("tecnic");
		comboUsuario.addItem("admin");
		contentPanel.add(comboUsuario, "cell 1 0, growx");

		LabelPropio lblprpContrasea = new LabelPropio("Contrase\u00F1a:");
		contentPanel.add(lblprpContrasea, "cell 0 1,alignx trailing");

		passwordField = new JPasswordField();
		passwordField.setFont(Constantes.FUENTE);
		contentPanel.add(passwordField, "cell 1 1,growx");

		setVisible(true);
	}

	private ActionListener _comprobarLogin() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// Variables
				String user = comboUsuario.getSelectedString(), pass = String
						.valueOf(passwordField.getPassword());

				String query = (String) HibernateUtil.getQuery(
						"select u.rango from Users u where login = '" + user
								+ "' and password = '" + pass + "'")
						.uniqueResult();

				// No ha acertado
				if (query == null) {
					Util.setMensajeInformacion("Contraseña incorrecta,"
							+ "\nintentos restantes " + (3 - intentos) + ".");
					passwordField.setText("");
					if (intentos++ == 3)
						System.exit(EXIT_ON_CLOSE);
					return;
				}

				byte i = 0;
				// Saber que usuario accede al programa.
				if (query.equals(Constantes.rangos[i++]))
					isUser = true;
				else if (query.equals(Constantes.rangos[i]))
					isTecnic = true;

				dispose();
			}
		};
	}

	public boolean isUser() {
		return isUser;
	}

	public boolean isTecnic() {
		return isTecnic;
	}
}
