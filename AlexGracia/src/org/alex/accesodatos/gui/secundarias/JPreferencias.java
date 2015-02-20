package org.alex.accesodatos.gui.secundarias;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.hibernate.Users;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.Util;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * JDialog encargado de configurar el programa.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class JPreferencias extends DialogPropio {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane_1;
	private ComboPropio comboUsuario;
	private JPasswordField passwordOld, passwordNew, passwordNew2;

	/**
	 * Create the dialog.
	 */
	public JPreferencias(JTabbedPane tabbedPane) {

		setTitle("Configuraci�n");
		setSize(new Dimension(500, 350));
		setLocationRelativeTo(this);
		contentPanel.setLayout(new BorderLayout(0, 0));

		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setFont(Constantes.FUENTE);
		contentPanel.add(tabbedPane_1, BorderLayout.CENTER);

		okButton.addActionListener(_getActionListener(tabbedPane));

		// User
		JPanel panelUser = new JPanel();
		tabbedPane_1.addTab("Usuario", panelUser);
		panelUser.setLayout(new MigLayout("", "[][grow]", "[][][][]"));

		LabelPropio lblprpUsuario = new LabelPropio("Usuario:");
		panelUser.add(lblprpUsuario, "cell 0 0");

		// Inicializar componentes
		passwordOld = new JPasswordField();
		passwordNew = new JPasswordField();
		passwordNew2 = new JPasswordField();

		comboUsuario = new ComboPropio();
		comboUsuario.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				passwordNew2.setBorder(Constantes.borderDefault);
			}
		});
		comboUsuario.addItem("tecnic");
		comboUsuario.addItem("admin");
		panelUser.add(comboUsuario, "cell 1 0,growx");

		LabelPropio lblprpContraseaVieja = new LabelPropio(
				"Contrase\u00F1a vieja:");
		panelUser.add(lblprpContraseaVieja, "cell 0 1");

		passwordOld.setFont(Constantes.FUENTE);
		panelUser.add(passwordOld, "cell 1 1,growx");

		LabelPropio lblprpContraseaNueva = new LabelPropio(
				"Contrase\u00F1a nueva:");
		panelUser.add(lblprpContraseaNueva, "cell 0 2");

		passwordNew.setFont(Constantes.FUENTE);
		panelUser.add(passwordNew, "cell 1 2,growx");

		LabelPropio lblprpConfirmacinNuevaContrasea = new LabelPropio(
				"Confirmar nueva contrase\u00F1a:");
		panelUser.add(lblprpConfirmacinNuevaContrasea,
				"cell 0 3,alignx trailing");

		passwordNew2.setFont(Constantes.FUENTE);
		panelUser.add(passwordNew2, "cell 1 3,growx");

		// Tabs
		JPanel panelTabs = new JPanel();
		tabbedPane_1.addTab("Pesta�as", panelTabs);
		panelTabs.setLayout(new MigLayout("", "[]", "[]"));

		LabelPropio lblprpelijaLasPestaufas = new LabelPropio(
				"Elija las pesta\u00F1as que quiera conservar:");
		panelTabs.add(lblprpelijaLasPestaufas, "cell 0 0");

		setVisible(true);
	}

	private ActionListener _getActionListener(final JTabbedPane tabbedPane) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				switch (tabbedPane_1.getSelectedIndex()) {
				case 0:
					_setUser();
					break;
				case 1:
					Util.setMensajeInformacion("No funciona.");
					_setTabs(tabbedPane);
					dispose();
					break;
				default:
				}

			}
		};
	}

	/**
	 * Metodo encargado de modificar el password de los usuarios tecnic y admin.
	 */
	private void _setUser() {
		// Coger user
		int id = 0;
		if (comboUsuario.getSelectedString().equals("admin"))
			id = 2;
		else
			id = 3;

		Users user = (Users) HibernateUtil.getCurrentSession().get(Users.class,
				id);

		// Actualizar el password
		String passOld = String.valueOf(passwordOld.getPassword());
		String passNew = String.valueOf(passwordNew.getPassword());
		String passNew2 = String.valueOf(passwordNew2.getPassword());

		if (passOld.equals(user.getPassword())) {

			if (!passNew.equals(passNew2)) {
				Util.setMensajeInformacion("La contrase�a nueva \nno supera la confirmaci�n.");
				passwordNew2.setText("");
				passwordNew2.setBorder(new LineBorder(Color.RED, 2));
				return;
			}

			passwordNew2.setBorder(Constantes.borderDefault);

			JConfirmacion confi = new JConfirmacion("Cambiar pass");
			if (!confi.isAceptar())
				return;

			user.setPassword(passNew);
			HibernateUtil.setData("actualizar", user);
		} else
			Util.setMensajeInformacion("La contrase�a vieja no es correcta.");

		dispose();
	}

	private void _setTabs(JTabbedPane tabbedPane) {
		// TODO ocultar pesta�as
		tabbedPane.getComponentAt(0).setVisible(false);
		// tabClientes.setVisible(false);
		// tabbedPane.updateUI();
		// tabbedPane.validate();
		tabbedPane.revalidate();

	}
}
