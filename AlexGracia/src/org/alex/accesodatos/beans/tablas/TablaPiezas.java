package org.alex.accesodatos.beans.tablas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.hibernate.Piezas;
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
public class TablaPiezas extends Tabla {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;

	// Variables graficas
	private TextPropio tfNombre, tfDescripcion, tfCantidad, tfPrecio, tfOrigen,
			tfMarca;
	private JCalendarCombo calendarSolicitud;

	public TablaPiezas(TextPropio tfNombre, TextPropio tfDescripcion,
			TextPropio tfCantidad, TextPropio tfPrecio,
			JCalendarCombo calendarSolicitud, TextPropio tfOrigen,
			TextPropio tfMarca) {

		this.tfNombre = tfNombre;
		this.tfDescripcion = tfDescripcion;
		this.tfCantidad = tfCantidad;
		this.tfPrecio = tfPrecio;
		this.calendarSolicitud = calendarSolicitud;
		this.tfOrigen = tfOrigen;
		this.tfMarca = tfMarca;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				pintarDatos();
			}
		});

		modelo = getModelo(Constantes.COLUMNAS_PIEZA);
		setModel(modelo);

	}

	public void listar() {

		listarComodin("FROM Piezas");

	}

	public void listar(String filtro) {

		listarComodin("select p from Piezas p where p.nombre like '%" + filtro
				+ "%' or p.lugarOrigen like '%" + filtro + "%'");
	}

	@SuppressWarnings("unchecked")
	private void listarComodin(String consulta) {

		modelo.setNumRows(0);

		for (Piezas pieza : (List<Piezas>) HibernateUtil.getQuery(consulta)
				.list())
			modelo.addRow(new Object[] { pieza.getIdPiezas(),
					pieza.getCodigoPiezas(), pieza.getNombre(),
					pieza.getDescripcion(), pieza.getCantidad(),
					pieza.getPrecio(), pieza.getLugarOrigen(),
					pieza.getFechaSolicitud(), pieza.getMarca() });

	}

	private void pintarDatos() {
		if (getPiezaSeleccionada() == null)
			return;

		tfNombre.setText(getPiezaSeleccionada().getNombre());
		tfDescripcion.setText(getPiezaSeleccionada().getDescripcion());
		tfCantidad
				.setText(String.valueOf(getPiezaSeleccionada().getCantidad()));
		tfPrecio.setText(String.valueOf(getPiezaSeleccionada().getPrecio()));
		calendarSolicitud.setDate(getPiezaSeleccionada().getFechaSolicitud());
		tfOrigen.setText(getPiezaSeleccionada().getLugarOrigen());
		tfMarca.setText(getPiezaSeleccionada().getMarca());

	}

	public Piezas getPiezaSeleccionada() {
		int fila = getSelectedRow();
		if (fila == -1)
			return null;

		int id = (Integer) modelo.getValueAt(fila, 0);

		return (Piezas) HibernateUtil.getCurrentSession().get(Piezas.class, id);
	}

}
