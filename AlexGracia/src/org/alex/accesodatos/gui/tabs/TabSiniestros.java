package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.beans.tablas.TablaPolizas;
import org.alex.accesodatos.beans.tablas.TablaSiniestros;
import org.alex.accesodatos.gui.JConfirmacion;
import org.alex.accesodatos.hibernate.Clientes;
import org.alex.accesodatos.hibernate.Polizas;
import org.alex.accesodatos.hibernate.Siniestros;
import org.alex.accesodatos.hibernate.Talleres;
import org.alex.accesodatos.hibernate.Vehiculos;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * siniestros.
 * 
 * @author Alex Gracia
 * 
 */
public class TabSiniestros extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// TODO Variables graficas
	private ComboPropio cbCliente, cbTaller;
	private TextPropio tfImporte, tfConductores;
	private JCalendarCombo dateInicio, dateConduccion;
	private TablaSiniestros tablaSiniestros;

	// private TextPropio tfDatosPoliza, tfImporteReparacion, tfDatosCliente,
	// tfDatosTaller, tfVehiculosImplicados, tfClientesHeridos;
	// private JCalendarCombo calendarReparacion, calendarSiniestro;
	// private ComboPropio cbIdCliente, cbIdTaller;

	/**
	 * Constructor que prepara el interfaz.
	 * 
	 * @param tabbedPane
	 */
	public TabSiniestros(JTabbedPane tabbedPane) {

		byte i = 0;
		tabbedPane.addTab(Constantes.TEXTO_SINIESTROS[i++], this);
		setLayout(null);

		LabelPropio lblTipo = new LabelPropio(Constantes.TEXTO_SINIESTROS[i++]);
		lblTipo.setBounds(11, 24, 135, 20);
		add(lblTipo);

		LabelPropio lblImporte = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblImporte.setBounds(11, 70, 99, 20);
		add(lblImporte);

		LabelPropio lblEstado = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblEstado.setBounds(11, 125, 99, 14);
		add(lblEstado);

		LabelPropio lblInicio = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblInicio.setBounds(11, 174, 99, 14);
		add(lblInicio);

		LabelPropio lblNumeroConductores = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblNumeroConductores.setBounds(11, 223, 135, 20);
		add(lblNumeroConductores);

		LabelPropio lblFechaConduccion = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblFechaConduccion.setBounds(11, 267, 135, 20);
		add(lblFechaConduccion);

		LabelPropio lblFechaFin = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblFechaFin.setBounds(11, 313, 135, 14);
		add(lblFechaFin);

		JLabel lblIdCliente = new JLabel(Constantes.TEXTO_SINIESTROS[i++]);
		lblIdCliente.setFont(Constantes.FUENTE_NEGRITA);
		lblIdCliente.setBounds(355, 312, 135, 20);
		add(lblIdCliente);

		JLabel lblIdVehiculo = new JLabel(Constantes.TEXTO_SINIESTROS[i]);
		lblIdVehiculo.setFont(Constantes.FUENTE_NEGRITA);
		lblIdVehiculo.setBounds(355, 361, 135, 20);
		add(lblIdVehiculo);

		// TODO cambiar por otro componente
		// cbTipo = new ComboPropio();
		// cbTipo.setBounds(181, 18, 120, 26);
		// add(cbTipo);

		tfImporte = new TextPropio();
		RestrictedSimple.soloNumeros(tfImporte);
		tfImporte.setBounds(181, 67, 120, 26);
		add(tfImporte);
		tfImporte.setColumns(10);

		// TODO cambiar por otro componente
		// cbEstado = new ComboPropio();
		// cbEstado.setBounds(181, 119, 120, 26);
		// add(cbEstado);

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

		// TODO cambiar por otro componente
		// dateFin = new JCalendarCombo();
		// dateFin.setBounds(181, 309, 120, 26);
		// add(dateFin);

		cbCliente = new ComboPropio();
		resetComboClientes();
		cbCliente.setBounds(490, 307, 120, 26);
		add(cbCliente);

		cbTaller = new ComboPropio();
		resetComboVehiculos();
		cbTaller.setBounds(490, 357, 120, 26);
		add(cbTaller);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 625, 269);
		add(scrollPane);

		tablaSiniestros = new TablaSiniestros(tfDatosPoliza,
				tfImporteReparacion, tfDatosCliente, calendarReparacion,
				tfDatosTaller, calendarSiniestro, tfVehiculosImplicados,
				cbIdCliente, cbIdTaller, tfClientesHeridos);
		scrollPane.setViewportView(tablaSiniestros);
	}

	public void mBuscarPoliza(String filtro) {
		if (filtro.equals(""))
			tablaSiniestros.listar();
		else
			tablaSiniestros.listar(filtro);
	}

	public boolean mEliminar() {
		if (!siniestroSeleccionado()
				|| !new JConfirmacion("Borrar").isAceptar())
			return false;

		HibernateUtil.setData("borrar",
				tablaSiniestros.getSiniestrosSeleccionado());

		tablaSiniestros.listar();
		mVaciarPoliza();
		return true;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public boolean mGuardar() {
		// Variables
		String idCliente = cbCliente.getSelectedString();
		String idTaller = cbTaller.getSelectedString();

		if (idCliente.equals("") || idTaller.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Clientes cliente = (Clientes) HibernateUtil.getCurrentSession().get(
				Clientes.class, Integer.parseInt(idCliente));
		Talleres taller = (Talleres) HibernateUtil.getCurrentSession().get(
				Talleres.class, Integer.parseInt(idTaller));

		Siniestros siniestro;
		if (esNuevo)
			siniestro = new Siniestros();
		else
			siniestro = tablaSiniestros.getSiniestrosSeleccionado();

		// TODO rellenar siniestro

		if (esNuevo)
			HibernateUtil.setData("guardar", siniestro);
		else {
			HibernateUtil.setData("actualizar", siniestro);

			esNuevo = true;
		}

		tablaSiniestros.listar();
		mVaciarPoliza();
		return true;
	}

	public boolean mEditar() {
		if (!siniestroSeleccionado())
			return false;

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
		mVaciarPoliza();
	}

	// TODO
	private void mVaciarPoliza() {
		Date date = Calendar.getInstance().getTime();

		tfImporte.setText("");
		dateInicio.setDate(date);
		tfConductores.setText("");
		dateConduccion.setDate(date);
		cbCliente.setSelectedIndex(0);
		cbTaller.setSelectedIndex(0);

	}

	public TablaSiniestros getTablaSiniestros() {
		return tablaSiniestros;
	}

	private boolean siniestroSeleccionado() {
		if (tablaSiniestros.getSiniestrosSeleccionado() == null) {
			Util.setMensajeInformacion("Seleccione una fila.");
			return false;
		}

		return true;
	}

	// TODO ordenar combos.
	/**
	 * Resetea el combo cbCliente.
	 */
	@SuppressWarnings("unchecked")
	public void resetComboClientes() {
		cbCliente.removeAllItems();
		cbCliente.addItem("");
		List<String> listaClientes = HibernateUtil.getQuery(
				"select c.idClientes from Clientes c").list();
		for (int i = 0; i < listaClientes.size(); i++)
			cbCliente.addItem(String.valueOf(listaClientes.get(i)));
	}

	/**
	 * Resetea el combo cbVehiculo.
	 */
	@SuppressWarnings("unchecked")
	public void resetComboVehiculos() {
		cbTaller.removeAllItems();
		cbTaller.addItem("");
		List<String> listaVehiculos = HibernateUtil.getQuery(
				"select v.idVehiculos from Vehiculos v").list();
		for (int i = 0; i < listaVehiculos.size(); i++)
			cbTaller.addItem(String.valueOf(listaVehiculos.get(i)));
	}
}
