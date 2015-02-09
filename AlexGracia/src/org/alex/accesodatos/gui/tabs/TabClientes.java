package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.beans.tablas.TablaClientes;
import org.alex.accesodatos.gui.secundarias.JConfirmacion;
import org.alex.accesodatos.hibernate.Clientes;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.accesodatos.util.ReportUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * clientes.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class TabClientes extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// Variables graficas
	private TextPropio tfNombre, tfDni, tfTelefono, tfDireccion, tfApellidos;
	private JCalendarCombo calendarNacimiento, calendarCarnet;
	private TablaClientes tablaCliente;

	/**
	 * Constructor que prepara el interfaz.
	 * 
	 * @param tabbedPane
	 */
	public TabClientes() {
		byte i = 1;

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
		RestrictedSimple.soloTextoConLimite(tfNombre, 50);
		tfNombre.setBounds(181, 18, 120, 26);
		add(tfNombre);
		tfNombre.setColumns(10);

		tfApellidos = new TextPropio();
		RestrictedSimple.soloTextoConLimite(tfApellidos, 100);
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
		RestrictedSimple.setLimite(tfDireccion, 200);
		tfDireccion.setBounds(181, 307, 120, 26);
		add(tfDireccion);
		tfDireccion.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 635, 270);
		add(scrollPane);

		tablaCliente = new TablaClientes(tfNombre, tfApellidos, tfDni,
				tfTelefono, calendarNacimiento, calendarCarnet, tfDireccion);
		scrollPane.setViewportView(tablaCliente);

	}

	public void mBuscarCliente(String filtro) {
		if (filtro.equals(""))
			tablaCliente.listar();
		else
			tablaCliente.listar(filtro);
	}

	public boolean mEliminar() {
		if (!clienteSeleccionado() || !new JConfirmacion("Borrar").isAceptar())
			return false;

		Clientes cliente = tablaCliente.getClienteSeleccionado();

		if (!HibernateUtil.setData("borrar", cliente)) {
			int idCliente = cliente.getIdClientes();
			List<?> queryPolizas = HibernateUtil.getQuery(
					"select p.idPolizas from Polizas p where p.clientes.idClientes = "
							+ idCliente).list();
			List<?> querySiniestros = HibernateUtil.getQuery(
					"select s.idSiniestros from Siniestros s where s.clientes.idClientes = "
							+ idCliente).list();
			if (!queryPolizas.isEmpty() && !querySiniestros.isEmpty())
				Util.setMensajeError("Debe borrar antes la/s póliza/s nº "
						+ queryPolizas + "," + "\n y el/los siniestro/s nº "
						+ querySiniestros + ".");
			else if (!queryPolizas.isEmpty())
				Util.setMensajeError("Debe borrar antes la/s póliza/s nº "
						+ queryPolizas + ".");
			else
				Util.setMensajeError("Debe borrar antes el/los siniestro/s nº "
						+ querySiniestros + ".");
		}

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

		// Comprobar que las fechas no sean posteriores a la actual.
		Date fechaNacimiento = calendarNacimiento.getDate();
		Date fechaCarnet = calendarCarnet.getDate();

		if (Util.esMenorDeEdad(fechaNacimiento)
				|| Util.esFechaFuturista(fechaCarnet, "del carnet de conducir"))
			return false;

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

		cliente.setFechaNacimiento(fechaNacimiento);
		cliente.setFechaCarnet(fechaCarnet);
		cliente.setDireccion(tfDireccion.getText());

		if (esNuevo)
			HibernateUtil.setData("guardar", cliente);
		else {
			HibernateUtil.setData("actualizar", cliente);
			esNuevo = true;
			tfNombre.setEnabled(true);
		}

		tablaCliente.listar();
		mVaciarCliente();
		return true;
	}

	/**
	 * <b>Tipos:</b> <br>
	 * 1. General <br>
	 * 2. Detallado
	 * 
	 * @param parent
	 * @param opcion
	 */
	public void mExportar(JFrame parent, int opcion) {
		HashMap<String, Object> parametro = new HashMap<String, Object>();

		if (opcion == 2)

			if (clienteSeleccionado()) {
				Clientes cliente = tablaCliente.getClienteSeleccionado();

				parametro.put("id", cliente.getIdClientes());

			} else
				return;

		new ReportUtil(parent, "report/clientes/report_clientes" + opcion
				+ ".jasper", parametro).ExportToPDF();

	}

	public boolean mEditar() {
		if (!clienteSeleccionado())
			return false;

		tfNombre.setEnabled(false);

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
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
