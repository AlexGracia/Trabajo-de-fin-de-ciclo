package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.beans.tablas.TablaPolizas;
import org.alex.accesodatos.gui.secundarias.JConfirmacion;
import org.alex.accesodatos.hibernate.Clientes;
import org.alex.accesodatos.hibernate.Polizas;
import org.alex.accesodatos.hibernate.Vehiculos;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.accesodatos.util.ReportUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * polizas.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class TabPolizas extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// Variables graficas
	private ComboPropio cbTipo, cbEstado, cbCliente, cbVehiculo;
	private TextPropio tfImporte, tfConductores;
	private JCalendarCombo dateInicio, dateConduccion, dateFin;
	private TablaPolizas tablaPolizas;

	/**
	 * Constructor que prepara el interfaz.
	 */
	public TabPolizas() {

		byte i = 1;

		setLayout(null);

		LabelPropio lblTipo = new LabelPropio(Constantes.TEXTO_POLIZAS[i++]);
		lblTipo.setBounds(11, 24, 135, 20);
		add(lblTipo);

		LabelPropio lblImporte = new LabelPropio(Constantes.TEXTO_POLIZAS[i++]);
		lblImporte.setBounds(11, 70, 99, 20);
		add(lblImporte);

		LabelPropio lblEstado = new LabelPropio(Constantes.TEXTO_POLIZAS[i++]);
		lblEstado.setBounds(11, 125, 99, 14);
		add(lblEstado);

		LabelPropio lblInicio = new LabelPropio(Constantes.TEXTO_POLIZAS[i++]);
		lblInicio.setBounds(11, 174, 99, 14);
		add(lblInicio);

		LabelPropio lblNumeroConductores = new LabelPropio(
				Constantes.TEXTO_POLIZAS[i++]);
		lblNumeroConductores.setBounds(11, 223, 135, 20);
		add(lblNumeroConductores);

		LabelPropio lblFechaConduccion = new LabelPropio(
				Constantes.TEXTO_POLIZAS[i++]);
		lblFechaConduccion.setBounds(11, 267, 135, 20);
		add(lblFechaConduccion);

		LabelPropio lblFechaFin = new LabelPropio(Constantes.TEXTO_POLIZAS[i++]);
		lblFechaFin.setBounds(11, 313, 135, 14);
		add(lblFechaFin);

		JLabel lblIdCliente = new JLabel(Constantes.TEXTO_POLIZAS[i++]);
		lblIdCliente.setFont(Constantes.FUENTE_NEGRITA);
		lblIdCliente.setBounds(355, 312, 135, 20);
		add(lblIdCliente);

		JLabel lblIdVehiculo = new JLabel(Constantes.TEXTO_POLIZAS[i]);
		lblIdVehiculo.setFont(Constantes.FUENTE_NEGRITA);
		lblIdVehiculo.setBounds(355, 361, 135, 20);
		add(lblIdVehiculo);

		cbTipo = new ComboPropio();
		cbTipo.addItem("");
		cbTipo.addItem("A_todo_riesgo");
		cbTipo.addItem("A_terceros");
		cbTipo.setBounds(181, 18, 120, 26);
		add(cbTipo);

		tfImporte = new TextPropio();
		RestrictedSimple.soloNumeros(tfImporte);
		tfImporte.setBounds(181, 67, 120, 26);
		add(tfImporte);
		tfImporte.setColumns(10);

		cbEstado = new ComboPropio();
		cbEstado.addItem("");
		cbEstado.addItem("Alta");
		cbEstado.addItem("Suspension");
		cbEstado.addItem("Baja");
		cbEstado.setBounds(181, 119, 120, 26);
		add(cbEstado);

		dateInicio = new JCalendarCombo();
		dateInicio.setBounds(181, 170, 120, 26);
		add(dateInicio);

		tfConductores = new TextPropio();
		RestrictedSimple.soloNumeros(tfConductores);
		tfConductores.setBounds(181, 220, 120, 26);
		add(tfConductores);
		tfConductores.setColumns(10);

		dateConduccion = new JCalendarCombo();
		dateConduccion.setBounds(181, 266, 120, 26);
		add(dateConduccion);

		dateFin = new JCalendarCombo();
		dateFin.setBounds(181, 309, 120, 26);
		add(dateFin);

		cbCliente = new ComboPropio();
		resetComboClientes();
		cbCliente.setBounds(490, 307, 120, 26);
		add(cbCliente);

		cbVehiculo = new ComboPropio();
		resetComboVehiculos();
		cbVehiculo.setBounds(490, 357, 120, 26);
		add(cbVehiculo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 635, 270);
		add(scrollPane);

		tablaPolizas = new TablaPolizas(cbTipo, tfImporte, cbEstado,
				dateInicio, tfConductores, dateConduccion, dateFin, cbCliente,
				cbVehiculo);
		scrollPane.setViewportView(tablaPolizas);
	}

	public void mBuscarPoliza(String filtro) {
		if (filtro.equals(""))
			tablaPolizas.listar();
		else
			tablaPolizas.listar(filtro);
	}

	public boolean mEliminar() {
		if (!polizaSeleccionada() || !new JConfirmacion("Borrar").isAceptar())
			return false;

		HibernateUtil.setData("borrar", tablaPolizas.getPolizaSeleccionada());

		tablaPolizas.listar();
		mVaciarPoliza();
		return true;
	}

	public boolean mGuardar() {
		// Variables
		String id_cliente = cbCliente.getSelectedString();
		String id_vehiculo = cbVehiculo.getSelectedString();

		if (id_cliente.equals("") || id_vehiculo.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Clientes cliente = (Clientes) HibernateUtil.getCurrentSession().get(
				Clientes.class, Integer.parseInt(id_cliente));
		Vehiculos vehiculo = (Vehiculos) HibernateUtil.getCurrentSession().get(
				Vehiculos.class, Integer.parseInt(id_vehiculo));

		// Comprobar que las fechas no sean posteriores a la actual.
		Date fechaInicio = dateInicio.getDate();
		Date fechaConduccion = dateConduccion.getDate();
		Date fechaFin = dateFin.getDate();

		if (Util.esFechaFuturista(fechaInicio, "de inicio")
				|| Util.esFechaFuturista(fechaConduccion, "de conducción")
				|| Util.esFechaFuturista(fechaFin, "de fin"))
			return false;

		Polizas poliza;
		if (esNuevo)
			poliza = new Polizas();
		else
			poliza = tablaPolizas.getPolizaSeleccionada();

		String texto = tfImporte.getText();

		poliza.setTipo(cbTipo.getSelectedString());
		if (texto.equals(""))
			poliza.setImporte(0);
		else
			poliza.setImporte(Integer.parseInt(texto));
		poliza.setEstado(cbEstado.getSelectedString());
		poliza.setFechaInicio(fechaInicio);
		texto = tfConductores.getText();
		if (texto.equals(""))
			poliza.setCantidadConductores(0);
		else
			poliza.setCantidadConductores(Integer.parseInt(texto));
		poliza.setAntiguedadConduccion(fechaConduccion);
		poliza.setFechaFin(fechaFin);
		poliza.setClientes(cliente);
		poliza.setVehiculos(vehiculo);

		if (esNuevo)
			HibernateUtil.setData("guardar", poliza);
		else {
			HibernateUtil.setData("actualizar", poliza);

			esNuevo = true;
		}

		tablaPolizas.listar();
		mVaciarPoliza();
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
	public void mInforme(JFrame parent, int opcion) {
		HashMap<String, Object> parametro = new HashMap<String, Object>();

		if (opcion == 2)

			if (polizaSeleccionada()) {
				Polizas poliza = tablaPolizas.getPolizaSeleccionada();

				parametro.put("id", poliza.getIdPolizas());

			} else
				return;

		new ReportUtil(parent, "report/polizas/report_polizas" + opcion
				+ ".jasper", parametro);

	}

	public boolean mEditar() {
		if (!polizaSeleccionada())
			return false;

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
		mVaciarPoliza();
	}

	private void mVaciarPoliza() {
		Date date = Calendar.getInstance().getTime();

		cbTipo.setSelectedIndex(0);
		tfImporte.setText("");
		cbEstado.setSelectedIndex(0);
		dateInicio.setDate(date);
		tfConductores.setText("");
		dateConduccion.setDate(date);
		dateFin.setDate(date);
		cbCliente.setSelectedIndex(0);
		cbVehiculo.setSelectedIndex(0);

	}

	public TablaPolizas getTablaPolizas() {
		return tablaPolizas;
	}

	private boolean polizaSeleccionada() {
		if (tablaPolizas.getPolizaSeleccionada() == null) {
			Util.setMensajeInformacion("Seleccione una fila.");
			return false;
		}

		return true;
	}

	/**
	 * Refresca el combo cbCliente.
	 */
	public void resetComboClientes() {
		HibernateUtil.resetCombo(cbCliente,
				"select c.idClientes from Clientes c");
	}

	/**
	 * Refresca el combo cbVehiculo.
	 */
	public void resetComboVehiculos() {
		HibernateUtil.resetCombo(cbVehiculo,
				"select v.idVehiculos from Vehiculos v");
	}
}
