package org.alex.accesodatos.beans.tablas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.hibernate.Polizas;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.Tabla;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * Clase que implementa una tabla para manejar las Polizas.
 * 
 * @author Alex Gracia
 * 
 */
public class TablaPolizas extends Tabla {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;

	// Variables graficas
	private ComboPropio cbTipo, cbEstado, cbCliente, cbVehiculo;
	private TextPropio tfImporte, tfConductores;
	private JCalendarCombo dateInicio, dateConduccion, dateFin;

	public TablaPolizas(ComboPropio cbTipo, TextPropio tfImporte,
			ComboPropio cbEstado, JCalendarCombo dateInicio,
			TextPropio tfConductores, JCalendarCombo dateConduccion,
			JCalendarCombo dateFin, ComboPropio cbCliente,
			ComboPropio cbVehiculo) {

		this.cbTipo = cbTipo;
		this.tfImporte = tfImporte;
		this.cbEstado = cbEstado;
		this.dateInicio = dateInicio;
		this.tfConductores = tfConductores;
		this.dateConduccion = dateConduccion;
		this.dateFin = dateFin;
		this.cbCliente = cbCliente;
		this.cbVehiculo = cbVehiculo;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				pintarDatos();
			}
		});

		modelo = getModelo(Constantes.COLUMNAS_POLIZA);
		setModel(modelo);
	}

	public void listar() {
		listarComodin("FROM Polizas");
	}

	public void listar(String filtro) {

		listarComodin("select p from Polizas p where p.clientes.idClientes like '%"
				+ filtro
				+ "%' or p.vehiculos.idVehiculos like '%"
				+ filtro
				+ "%'");
	}

	@SuppressWarnings("unchecked")
	private void listarComodin(String consulta) {

		modelo.setNumRows(0);

		for (Polizas poliza : (List<Polizas>) HibernateUtil.getQuery(consulta)
				.list())
			modelo.addRow(new Object[] { poliza.getIdPolizas(),
					poliza.getNumero(), poliza.getTipo(), poliza.getImporte(),
					poliza.getEstado(), poliza.getFechaInicio(),
					poliza.getCantidadConductores(),
					poliza.getAntiguedadConduccion(), poliza.getFechaFin(),
					poliza.getClientes().getIdClientes(),
					poliza.getVehiculos().getIdVehiculos() });

	}

	private void pintarDatos() {
		if (getPolizaSeleccionada() == null)
			return;

		cbTipo.setSelectedItem(getPolizaSeleccionada().getTipo());
		tfImporte.setText(String.valueOf(getPolizaSeleccionada().getImporte()));
		cbEstado.setSelectedItem(getPolizaSeleccionada().getEstado());
		dateInicio.setDate(getPolizaSeleccionada().getFechaInicio());
		tfConductores.setText(String.valueOf(getPolizaSeleccionada()
				.getCantidadConductores()));
		dateConduccion.setDate(getPolizaSeleccionada()
				.getAntiguedadConduccion());
		dateFin.setDate(getPolizaSeleccionada().getFechaFin());
		cbCliente.setSelectedItem(getPolizaSeleccionada().getClientes()
				.getIdClientes());
		cbVehiculo.setSelectedItem(getPolizaSeleccionada().getVehiculos()
				.getIdVehiculos());

	}

	public Polizas getPolizaSeleccionada() {

		int fila = getSelectedRow();
		if (fila == -1)
			return null;

		int id = (Integer) modelo.getValueAt(fila, 0);

		return (Polizas) HibernateUtil.getCurrentSession().get(Polizas.class,
				id);

	}

}
