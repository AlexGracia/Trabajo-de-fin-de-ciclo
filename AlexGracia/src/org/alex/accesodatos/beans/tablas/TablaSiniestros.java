package org.alex.accesodatos.beans.tablas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.hibernate.Siniestros;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.Tabla;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * Clase que implementa una tabla para manejar los clientes.
 * 
 * @author Alex Gracia
 * 
 */
public class TablaSiniestros extends Tabla {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;

	// Variables graficas
	private TextPropio tfDatosPoliza, tfImporteReparacion, tfDatosCliente,
			tfDatosTaller, tfVehiculosImplicados, tfClientesHeridos;
	private JCalendarCombo calendarReparacion, calendarSiniestro;
	private ComboPropio cbIdCliente, cbIdTaller;

	public TablaSiniestros(TextPropio tfDatosPoliza,
			TextPropio tfImporteReparacion, TextPropio tfDatosCliente,
			JCalendarCombo calendarReparacion, TextPropio tfDatosTaller,
			JCalendarCombo calendarSiniestro, TextPropio tfVehiculosImplicados,
			ComboPropio cbIdCliente, ComboPropio cbIdTaller,
			TextPropio tfClientesHeridos) {

		this.tfDatosPoliza = tfDatosPoliza;
		this.tfImporteReparacion = tfImporteReparacion;
		this.tfDatosCliente = tfDatosCliente;
		this.calendarReparacion = calendarReparacion;
		this.tfDatosTaller = tfDatosTaller;
		this.calendarSiniestro = calendarSiniestro;
		this.tfVehiculosImplicados = tfVehiculosImplicados;
		this.cbIdCliente = cbIdCliente;
		this.cbIdTaller = cbIdTaller;
		this.tfClientesHeridos = tfClientesHeridos;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				pintarDatos();
			}
		});

		modelo = getModelo(Constantes.COLUMNAS_SINIESTRO);
		setModel(modelo);

	}

	public void listar() {

		listarComodin("FROM Siniestros");

	}

	public void listar(String filtro) {

		listarComodin("select s from Siniestros s where s.clientes.idClientes = "
				+ filtro + " or s.talleres.idTalleres = " + filtro);

	}

	@SuppressWarnings("unchecked")
	private void listarComodin(String consulta) {

		modelo.setNumRows(0);

		for (Siniestros siniestro : (List<Siniestros>) HibernateUtil.getQuery(
				consulta).list())
			modelo.addRow(new Object[] { siniestro.getIdSiniestros(),
					siniestro.getDatosPoliza(),
					siniestro.getImporteReparacion(),
					siniestro.getDatosCliente(),
					siniestro.getFechaReparacion(), siniestro.getDatosTaller(),
					siniestro.getFechaSiniestro(),
					siniestro.getCantidadVehiculosImplicados(),
					siniestro.getClientes().getIdClientes(),
					siniestro.getTalleres().getIdTalleres(),
					siniestro.getClientesHeridos() });

	}

	private void pintarDatos() {
		if (getSiniestrosSeleccionado() == null)
			return;

		tfDatosPoliza.setText(getSiniestrosSeleccionado().getDatosPoliza());
		tfImporteReparacion.setText(String.valueOf(getSiniestrosSeleccionado()
				.getImporteReparacion()));
		tfDatosCliente.setText(getSiniestrosSeleccionado().getDatosCliente());
		calendarReparacion.setDate(getSiniestrosSeleccionado()
				.getFechaReparacion());
		tfDatosTaller.setText(getSiniestrosSeleccionado().getDatosTaller());
		calendarSiniestro.setDate(getSiniestrosSeleccionado()
				.getFechaSiniestro());
		tfVehiculosImplicados.setText(String
				.valueOf(getSiniestrosSeleccionado()
						.getCantidadVehiculosImplicados()));
		cbIdCliente.setSelectedItem(String.valueOf(getSiniestrosSeleccionado()
				.getClientes().getIdClientes()));
		cbIdTaller.setSelectedItem(String.valueOf(getSiniestrosSeleccionado()
				.getTalleres().getIdTalleres()));
		tfClientesHeridos.setText(String.valueOf(getSiniestrosSeleccionado()
				.getClientesHeridos()));
	}

	public Siniestros getSiniestrosSeleccionado() {
		int fila = getSelectedRow();
		if (fila == -1)
			return null;

		int id = (Integer) modelo.getValueAt(fila, 0);

		return (Siniestros) HibernateUtil.getCurrentSession().get(
				Siniestros.class, id);
	}

}
