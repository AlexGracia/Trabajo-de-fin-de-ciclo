package org.alex.accesodatos.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.util.Constantes;

/**
 * Dialogo de login.
 * 
 * @author Alex Gracia
 * @version 1.0
 */
public class JConecta extends DialogPropio {

	private static final long serialVersionUID = 1L;
	private JPasswordField txtPass;

	private String user;
	private String pass;

	public enum Accion {
		ACEPTAR, CANCELAR
	}

	private Accion accion;
	private ComboPropio comboPropio;

	private ActionListener aceptar(final boolean aceptar) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (aceptar) {
					user = comboPropio.getSelectedString();
					pass = String.valueOf(txtPass.getPassword());
					accion = Accion.ACEPTAR;
				} else
					accion = Accion.CANCELAR;

				setVisible(false);
			}
		};
	}

	/*
	 * Getters para la recogida de información del formulario desde la ventana
	 * principal de la aplicación
	 */

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public Accion getAccion() {
		return accion;
	}

	public Accion mostrarDialogo() {
		setVisible(true);
		txtPass.setText("");
		return accion;
	}

	/**
	 * Crea el diálogo y lo muestra en pantalla
	 */
	public JConecta() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				accion = Accion.CANCELAR;
			}
		});

		setTitle("Login");
		setSize(new Dimension(250, 175));
		setLocationRelativeTo(null);

		contentPanel.setLayout(null);

		JLabel lbUsuario = new JLabel("Usuario:");
		lbUsuario.setFont(Constantes.FUENTE_NEGRITA);
		lbUsuario.setBounds(10, 18, 80, 14);
		contentPanel.add(lbUsuario);

		JLabel lbPassword = new JLabel("Contrase\u00F1a:");
		lbPassword.setFont(Constantes.FUENTE);
		lbPassword.setBounds(10, 57, 103, 14);
		contentPanel.add(lbPassword);

		comboPropio = new ComboPropio();
		comboPropio.addItem("user");
		comboPropio.addItem("tecnic");
		comboPropio.addItem("admin");
		comboPropio.setBounds(126, 11, 106, 28);
		contentPanel.add(comboPropio);

		txtPass = new JPasswordField();
		txtPass.setFont(Constantes.FUENTE);
		txtPass.setBounds(126, 50, 106, 28);
		contentPanel.add(txtPass);
		txtPass.setColumns(10);
		{

			okButton.addActionListener(aceptar(true));

			cancelButton.addActionListener(aceptar(false));
		}

	}
}
