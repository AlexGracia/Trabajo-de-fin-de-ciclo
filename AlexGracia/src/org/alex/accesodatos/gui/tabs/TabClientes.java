package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.beans.tablas.TablaClientes;
import org.alex.accesodatos.hibernate.Clientes;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * Maneja la interfaz de los clientes.
 * 
 * @author Alex Gracia
 * 
 */
public class TabClientes extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// Variables graficas
	private TextPropio tfNombre;
	private TextPropio tfDni;
	private TextPropio tfTelefono;
	private TextPropio tfDireccion;
	private JCalendarCombo calendarNacimiento;
	private TablaClientes tablaCliente;
	private TextPropio tfApellidos;
	private JCalendarCombo calendarCarnet;

	/**
	 * Constructor que prepara el interfaz.
	 * 
	 * @param tabbedPane
	 */
	public TabClientes(JTabbedPane tabbedPane) {

		byte i = 0;
		tabbedPane.addTab(Constantes.TEXTO_CLIENTES[i++], this);
		setLayout(null);

		JLabel lblNombre = new JLabel(Constantes.TEXTO_CLIENTES[i++]);
		lblNombre.setFont(Constantes.FUENTE_NEGRITA);
		lblNombre.setBounds(11, 24, 99, 14);
		add(lblNombre);

		LabelPropio lblApellidos = new LabelPropio(
				Constantes.TEXTO_CLIENTES[i++]);
		lblApellidos.setBounds(11, 70, 99, 20);
		add(lblApellidos);

		JLabel lblDni = new JLabel(Constantes.TEXTO_CLIENTES[i++]);
		lblDni.setFont(Constantes.FUENTE_NEGRITA);
		lblDni.setBounds(11, 125, 99, 14);
		add(lblDni);

		LabelPropio lblTelefono = new LabelPropio(
				Constantes.TEXTO_CLIENTES[i++]);
		lblTelefono.setBounds(11, 174, 99, 14);
		add(lblTelefono);

		LabelPropio lblNacimiento = new LabelPropio(
				Constantes.TEXTO_CLIENTES[i++]);
		lblNacimiento.setBounds(11, 223, 135, 14);
		add(lblNacimiento);

		LabelPropio lblCarnet = new LabelPropio(Constantes.TEXTO_CLIENTES[i++]);
		lblCarnet.setBounds(11, 267, 135, 14);
		add(lblCarnet);

		LabelPropio lblDireccion = new LabelPropio(Constantes.TEXTO_CLIENTES[i]);
		lblDireccion.setBounds(11, 313, 99, 14);
		add(lblDireccion);

		tfNombre = new TextPropio();
		RestrictedSimple.soloTexto(tfNombre);
		tfNombre.setBounds(181, 18, 120, 26);
		add(tfNombre);
		tfNombre.setColumns(10);

		tfApellidos = new TextPropio();
		RestrictedSimple.soloTextoConLimite(tfApellidos, 20);
		tfApellidos.setBounds(181, 65, 120, 26);
		add(tfApellidos);

		tfDni = new TextPropio();
		RestrictedSimple.setLimite(tfDni, 9);
		tfDni.setBounds(181, 116, 120, 26);
		add(tfDni);
		tfDni.setColumns(10);

		tfTelefono = new TextPropio();
		RestrictedSimple.soloNumeros(tfTelefono);
		tfTelefono.setBounds(181, 164, 120, 26);
		add(tfTelefono);
		tfTelefono.setColumns(10);

		calendarNacimiento = new JCalendarCombo();
		calendarNacimiento.setBounds(181, 213, 120, 26);
		add(calendarNacimiento);

		calendarCarnet = new JCalendarCombo();
		calendarCarnet.setBounds(181, 263, 120, 26);
		add(calendarCarnet);

		tfDireccion = new TextPropio();
		tfDireccion.setBounds(181, 307, 120, 26);
		add(tfDireccion);
		tfDireccion.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 625, 271);
		add(scrollPane);

		tablaCliente = new TablaClientes(tfNombre, tfApellidos, tfDni,
				tfTelefono, calendarNacimiento, calendarCarnet, tfDireccion);
		scrollPane.setViewportView(tablaCliente);

	}

	public void mBuscarCliente(String filtro) {

		if (filtro.length() <= 0 && filtro.equals(""))
			tablaCliente.listar();
		else
			tablaCliente.listar(filtro);

	}

	public boolean mEliminar() {

		if (!clienteSeleccionado())
			return false;

		Clientes cliente = tablaCliente.getClienteSeleccionado();

		HibernateUtil.setData("borrar", cliente,
				"Debe borrar antes la póliza correspondiente.");

		tablaCliente.listar();
		mVaciarCliente();
		return true;

	}

	public boolean mGuardar() {
		// Variables
		String nombre = tfNombre.getText();
		String dni = tfDni.getText();

		if (nombre.equals("") || dni.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Clientes cliente;
		if (esNuevo) {
			String dniSQL = (String) HibernateUtil
					.getCurrentSession()
					.createQuery(
							"select c.dni from Clientes c where c.dni = '"
									+ dni + "'").uniqueResult();

			if (dni.equals(dniSQL)) {
				Util.setMensajeInformacion("DNI ya existente, cámbielo");
				tfDni.setText("");
				return false;
			}
			cliente = new Clientes();
		} else
			cliente = tablaCliente.getClienteSeleccionado();

		cliente.setNombre(nombre);
		cliente.setApellidos(tfApellidos.getText());
		cliente.setDni(dni);
		String telefono = tfTelefono.getText();
		if (telefono.equals(""))
			cliente.setTelefono(0);
		else
			cliente.setTelefono(Integer.parseInt(telefono));

		cliente.setFechaNacimiento(calendarNacimiento.getDate());
		cliente.setFechaCarnet(calendarCarnet.getDate());
		cliente.setDireccion(tfDireccion.getText());

		if (esNuevo)
			HibernateUtil.setData("guardar", cliente, "");

		else {
			HibernateUtil.setData("actualizar", cliente, "");

			esNuevo = true;
			tfNombre.setEnabled(true);
		}

		tablaCliente.listar();
		mVaciarCliente();
		return true;
	}

	public void mEditar() {
		if (!clienteSeleccionado())
			return;

		tfNombre.setEnabled(false);

		esNuevo = false;
	}

	public void mCancelar() {
		mVaciarCliente();
		tfNombre.setEnabled(true);
	}

	private void mVaciarCliente() {

		tfNombre.setText("");
		tfApellidos.setText("");
		tfDni.setText("");
		tfTelefono.setText("");
		Date date = Calendar.getInstance().getTime();
		calendarNacimiento.setDate(date);
		calendarCarnet.setDate(date);
		tfDireccion.setText("");

	}

	public TablaClientes getTablaClientes() {
		return tablaCliente;
	}

	private boolean clienteSeleccionado() {
		if (tablaCliente.getClienteSeleccionado() == null) {
			Util.setMensajeInformacion("Seleccione una fila.");
			return false;
		}
		return true;
	}

}
