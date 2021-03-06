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
import org.alex.accesodatos.beans.tablas.TablaSiniestros;
import org.alex.accesodatos.gui.secundarias.JConfirmacion;
import org.alex.accesodatos.hibernate.Clientes;
import org.alex.accesodatos.hibernate.Siniestros;
import org.alex.accesodatos.hibernate.Talleres;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.accesodatos.util.ReportUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * siniestros.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class TabSiniestros extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// Variables graficas
	private ComboPropio cbIdCliente, cbIdTaller;
	private TextPropio tfDatosPoliza, tfImporteReparacion, tfDatosCliente,
			tfDatosTaller, tfVehiculosImplicados, tfClientesHeridos;
	private JCalendarCombo calendarReparacion, calendarSiniestro;
	private TablaSiniestros tablaSiniestros;

	/**
	 * Constructor que prepara el interfaz.
	 */
	public TabSiniestros() {

		byte i = 1;

		setLayout(null);

		LabelPropio lblDatosPoliza = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblDatosPoliza.setBounds(11, 24, 135, 20);
		add(lblDatosPoliza);

		LabelPropio lblImporteReparacion = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblImporteReparacion.setBounds(11, 70, 160, 20);
		add(lblImporteReparacion);

		LabelPropio lblDatosCliente = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblDatosCliente.setBounds(11, 125, 99, 14);
		add(lblDatosCliente);

		LabelPropio lblFechaReparacion = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblFechaReparacion.setBounds(11, 174, 99, 14);
		add(lblFechaReparacion);

		LabelPropio lblDatosTaller = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblDatosTaller.setBounds(11, 223, 135, 20);
		add(lblDatosTaller);

		LabelPropio lblFechaSiniestro = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblFechaSiniestro.setBounds(11, 267, 135, 20);
		add(lblFechaSiniestro);

		LabelPropio lblVehiculosImplicados = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i++]);
		lblVehiculosImplicados.setBounds(11, 312, 160, 20);
		add(lblVehiculosImplicados);

		JLabel lblIdCliente = new JLabel(Constantes.TEXTO_SINIESTROS[i++]);
		lblIdCliente.setFont(Constantes.FUENTE_NEGRITA);
		lblIdCliente.setBounds(355, 312, 135, 20);
		add(lblIdCliente);

		JLabel lblIdVehiculo = new JLabel(Constantes.TEXTO_SINIESTROS[i++]);
		lblIdVehiculo.setFont(Constantes.FUENTE_NEGRITA);
		lblIdVehiculo.setBounds(355, 361, 135, 20);
		add(lblIdVehiculo);

		LabelPropio lblClientesHeridos = new LabelPropio(
				Constantes.TEXTO_SINIESTROS[i]);
		lblClientesHeridos.setBounds(11, 358, 135, 20);
		add(lblClientesHeridos);

		tfDatosPoliza = new TextPropio();
		RestrictedSimple.setLimite(tfDatosPoliza, 200);
		tfDatosPoliza.setBounds(181, 18, 120, 26);
		add(tfDatosPoliza);

		tfImporteReparacion = new TextPropio();
		tfImporteReparacion.setBounds(181, 67, 120, 26);
		add(tfImporteReparacion);
		tfImporteReparacion.setColumns(10);

		tfDatosCliente = new TextPropio();
		RestrictedSimple.setLimite(tfDatosCliente, 200);
		tfDatosCliente.setBounds(181, 119, 120, 26);
		add(tfDatosCliente);

		calendarReparacion = new JCalendarCombo();
		calendarReparacion.setBounds(181, 170, 120, 26);
		add(calendarReparacion);

		tfDatosTaller = new TextPropio();
		RestrictedSimple.setLimite(tfDatosTaller, 200);
		tfDatosTaller.setBounds(181, 220, 120, 26);
		add(tfDatosTaller);

		calendarSiniestro = new JCalendarCombo();
		calendarSiniestro.setBounds(181, 270, 120, 26);
		add(calendarSiniestro);

		tfVehiculosImplicados = new TextPropio();
		RestrictedSimple.soloNumeros(tfVehiculosImplicados);
		tfVehiculosImplicados.setBounds(181, 313, 120, 26);
		add(tfVehiculosImplicados);
		tfVehiculosImplicados.setColumns(10);

		tfClientesHeridos = new TextPropio();
		RestrictedSimple.soloNumeros(tfClientesHeridos);
		tfClientesHeridos.setBounds(181, 358, 120, 26);
		add(tfClientesHeridos);

		cbIdCliente = new ComboPropio();
		resetComboClientes();
		cbIdCliente.setBounds(490, 307, 120, 26);
		add(cbIdCliente);

		cbIdTaller = new ComboPropio();
		resetComboTalleres();
		cbIdTaller.setBounds(490, 357, 120, 26);
		add(cbIdTaller);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 635, 270);
		add(scrollPane);

		tablaSiniestros = new TablaSiniestros(tfDatosPoliza,
				tfImporteReparacion, tfDatosCliente, calendarReparacion,
				tfDatosTaller, calendarSiniestro, tfVehiculosImplicados,
				cbIdCliente, cbIdTaller, tfClientesHeridos);
		scrollPane.setViewportView(tablaSiniestros);
	}

	public void mBuscarSiniestro(String filtro) {
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
		mVaciarSiniestro();
		return true;
	}

	public boolean mGuardar() {
		// Variables
		String idCliente = cbIdCliente.getSelectedString();
		String idTaller = cbIdTaller.getSelectedString();

		if (idCliente.equals("") || idTaller.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Clientes cliente = (Clientes) HibernateUtil.getCurrentSession().get(
				Clientes.class, Integer.parseInt(idCliente));
		Talleres taller = (Talleres) HibernateUtil.getCurrentSession().get(
				Talleres.class, Integer.parseInt(idTaller));

		// Comprobar que las fechas no sean posteriores a la actual.
		Date fechaReparacion = calendarReparacion.getDate();
		Date fechaSiniestro = calendarSiniestro.getDate();

		if (Util.esFechaFuturista(fechaReparacion, "de reparación")
				|| Util.esFechaFuturista(fechaSiniestro, "de siniestro"))
			return false;

		Siniestros siniestro;
		if (esNuevo)
			siniestro = new Siniestros();
		else
			siniestro = tablaSiniestros.getSiniestrosSeleccionado();

		siniestro.setDatosPoliza(tfDatosPoliza.getText());
		String texto = tfImporteReparacion.getText();
		if (texto.equals(""))
			siniestro.setImporteReparacion(0.0);
		else
			siniestro.setImporteReparacion(Double
					.parseDouble(tfImporteReparacion.getText()));
		siniestro.setDatosCliente(tfDatosCliente.getText());
		siniestro.setFechaReparacion(fechaReparacion);
		siniestro.setDatosTaller(tfDatosTaller.getText());
		siniestro.setFechaSiniestro(fechaSiniestro);
		texto = tfVehiculosImplicados.getText();
		if (texto.equals(""))
			siniestro.setCantidadVehiculosImplicados(1);
		else
			siniestro.setCantidadVehiculosImplicados(Integer
					.parseInt(tfVehiculosImplicados.getText()));
		siniestro.setClientes(cliente);
		siniestro.setTalleres(taller);
		texto = tfClientesHeridos.getText();
		if (texto.equals(""))
			siniestro.setClientesHeridos(0);
		else
			siniestro.setClientesHeridos(Integer.parseInt(tfClientesHeridos
					.getText()));

		if (esNuevo)
			HibernateUtil.setData("guardar", siniestro);
		else {
			HibernateUtil.setData("actualizar", siniestro);

			esNuevo = true;
		}

		tablaSiniestros.listar();
		mVaciarSiniestro();
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

			if (siniestroSeleccionado()) {
				Siniestros siniestro = tablaSiniestros
						.getSiniestrosSeleccionado();

				parametro.put("id", siniestro.getIdSiniestros());

			} else
				return;

		new ReportUtil(parent, "report/siniestros/report_siniestros" + opcion
				+ ".jasper", parametro);

	}

	public boolean mEditar() {
		if (!siniestroSeleccionado())
			return false;

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
		mVaciarSiniestro();
	}

	private void mVaciarSiniestro() {
		Date date = Calendar.getInstance().getTime();

		tfDatosPoliza.setText("");
		tfImporteReparacion.setText("");
		tfDatosCliente.setText("");
		calendarReparacion.setDate(date);
		tfDatosTaller.setText("");
		calendarSiniestro.setDate(date);
		tfVehiculosImplicados.setText("");
		cbIdCliente.setSelectedIndex(0);
		cbIdTaller.setSelectedIndex(0);
		tfClientesHeridos.setText("");
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

	/**
	 * Refresca el combo cbIdCliente.
	 */
	public void resetComboClientes() {
		HibernateUtil.resetCombo(cbIdCliente,
				"select c.idClientes from Clientes c");
	}

	/**
	 * Refresca el combo cbIdTaller.
	 */
	public void resetComboTalleres() {
		HibernateUtil.resetCombo(cbIdTaller,
				"select t.idTalleres from Talleres t");
	}
}
