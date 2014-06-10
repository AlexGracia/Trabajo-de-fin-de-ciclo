package org.alex.accesodatos.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.util.Constantes;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Diálogo para recoger los datos de conexión con un SGBD
 * 
 * @author Alex Gracia
 */
public class JConecta extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPasswordField txtPass;

	private String user;
	private String pass;

	public enum Accion {
		ACEPTAR, CANCELAR
	}

	private Accion accion;
	private ComboPropio comboPropio;

	/**
	 * El usuario ha pulsado aceptar. Se recogen los datos del formulario como
	 * atributos de la clase y se esconde el formulario.
	 */
	private void aceptar() {

		user = comboPropio.getSelectedString();
		pass = String.valueOf(txtPass.getPassword());

		accion = Accion.ACEPTAR;
		setVisible(false);
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

		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						JConecta.class
								.getResource("/org/alex/accesodatos/iconos/IconoAplicacion.png")));
		setModal(true);
		setTitle("Login");
		setSize(new Dimension(250, 175));
		getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
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
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton();
				okButton.setIcon(new ImageIcon(JConecta.class
						.getResource("/org/alex/accesodatos/iconos/ok.png")));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						aceptar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("");
				cancelButton
						.setIcon(new ImageIcon(
								JConecta.class
										.getResource("/org/alex/accesodatos/iconos/cancelar.png")));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						accion = Accion.CANCELAR;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		setLocationRelativeTo(this);
	}
}
