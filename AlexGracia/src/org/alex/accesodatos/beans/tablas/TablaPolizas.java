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
	private ComboPropio cbTipo;
	private TextPropio tfImporte;
	private ComboPropio cbEstado;
	private JCalendarCombo dateInicio;
	private TextPropio tfConductores;
	private JCalendarCombo dateConduccion;
	private JCalendarCombo dateFin;
	private TextPropio tfCliente;
	private TextPropio tfVehiculo;

	public TablaPolizas(ComboPropio cbTipo, TextPropio tfImporte,
			ComboPropio cbEstado, JCalendarCombo dateInicio,
			TextPropio tfConductores, JCalendarCombo dateConduccion,
			JCalendarCombo dateFin, TextPropio tfCliente, TextPropio tfVehiculo) {

		this.cbTipo = cbTipo;
		this.tfImporte = tfImporte;
		this.cbEstado = cbEstado;
		this.dateInicio = dateInicio;
		this.tfConductores = tfConductores;
		this.dateConduccion = dateConduccion;
		this.dateFin = dateFin;
		this.tfCliente = tfCliente;
		this.tfVehiculo = tfVehiculo;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					pintarDatos();
				} catch (NullPointerException npe) {
					npe.printStackTrace();
				}
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
		Polizas PolizaSeleccionada = getPolizaSeleccionada();

		cbTipo.setSelectedItem(PolizaSeleccionada.getTipo());
		tfImporte.setText(String.valueOf(PolizaSeleccionada.getImporte()));
		cbEstado.setSelectedItem(PolizaSeleccionada.getEstado());
		dateInicio.setDate(PolizaSeleccionada.getFechaInicio());
		tfConductores.setText(String.valueOf(PolizaSeleccionada
				.getCantidadConductores()));
		dateConduccion.setDate(PolizaSeleccionada.getAntiguedadConduccion());
		dateFin.setDate(PolizaSeleccionada.getFechaFin());
		tfCliente.setText(String.valueOf(PolizaSeleccionada.getClientes()
				.getIdClientes()));
		tfVehiculo.setText(String.valueOf(PolizaSeleccionada.getVehiculos()
				.getIdVehiculos()));

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
