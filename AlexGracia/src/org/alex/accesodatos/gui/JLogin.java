package org.alex.accesodatos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;

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
	private boolean isUser, isTecnic;
	private byte[] intentos = { 1, 1, 1 };

	/**
	 * Crea la ventana de login.
	 */
	public JLogin() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		// Modificaciones a la ventana madre

		okButton.addActionListener(_comprobarLogin());
		setTitle("Login");
		setSize(new Dimension(200, 160));
		setLocationRelativeTo(this);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][]"));

		// Components
		LabelPropio lblprpUsuario = new LabelPropio("Usuario:");
		contentPanel.add(lblprpUsuario, "cell 0 0");

		passwordField = new JPasswordField();

		comboUsuario = new ComboPropio();
		comboUsuario.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				if (intentos[comboUsuario.getSelectedIndex()] == 1)
					passwordField.setBorder(Constantes.borderDefault);
				else
					passwordField.setBorder(new LineBorder(Color.RED, 2));
			}
		});
		comboUsuario.addItem("user");
		comboUsuario.addItem("tecnic");
		comboUsuario.addItem("admin");
		contentPanel.add(comboUsuario, "cell 1 0, growx");

		LabelPropio lblprpContrasea = new LabelPropio("Contrase\u00F1a:");
		contentPanel.add(lblprpContrasea, "cell 0 1,alignx trailing");

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

				byte i = (byte) comboUsuario.getSelectedIndex();

				// No ha acertado
				if (query == null) {
					Util.setMensajeInformacion("Contraseña incorrecta,"
							+ "\nintentos restantes " + (3 - intentos[i]) + ".");
					passwordField.setText("");
					passwordField.setBorder(new LineBorder(Color.RED, 2));
					if (intentos[i]++ == 3)
						System.exit(EXIT_ON_CLOSE);
					return;
				}

				// Saber que usuario accede al programa.
				if (query.equals(Constantes.rangos[0]))
					isUser = true;
				else if (query.equals(Constantes.rangos[1]))
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
