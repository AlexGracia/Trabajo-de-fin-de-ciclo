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
public class JConfiguracion extends DialogPropio {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private ComboPropio comboUsuario;
	private JPasswordField passwordOld, passwordNew, passwordNew2;

	/**
	 * Create the dialog.
	 */
	public JConfiguracion() {

		setTitle("Configuración");
		setSize(new Dimension(500, 255));
		setLocationRelativeTo(this);
		contentPanel.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(Constantes.FUENTE);
		contentPanel.add(tabbedPane, BorderLayout.CENTER);

		okButton.addActionListener(_getActionListener());

		// User
		JPanel panelUser = new JPanel();
		tabbedPane.addTab("Usuario", panelUser);
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
				"Confirmar contrase\u00F1a:");
		panelUser.add(lblprpConfirmacinNuevaContrasea,
				"cell 0 3,alignx trailing");

		passwordNew2.setFont(Constantes.FUENTE);
		panelUser.add(passwordNew2, "cell 1 3,growx");

		setVisible(true);
	}

	private ActionListener _getActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				_setUser();
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
				Util.setMensajeInformacion("La contraseña nueva \nno supera la confirmación.");
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
			Util.setMensajeInformacion("La contraseña vieja no es correcta.");

		dispose();
	}
}
