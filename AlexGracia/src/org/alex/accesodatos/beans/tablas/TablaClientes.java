package org.alex.accesodatos.beans.tablas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.hibernate.Clientes;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.Tabla;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * Clase que implementa una tabla para manejar los clientes.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class TablaClientes extends Tabla {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;

	// Variables graficas
	private TextPropio tfNombre, tfApellidos, tfDni, tfTelefono, tfDireccion;
	private JCalendarCombo calendarNacimiento, calendarCarnet;

	public TablaClientes(TextPropio tfNombre, TextPropio tfApellidos,
			TextPropio tfDni, TextPropio tfTelefono,
			JCalendarCombo calendarNacimiento, JCalendarCombo calendarCarnet,
			TextPropio tfDireccion) {

		this.tfNombre = tfNombre;
		this.tfApellidos = tfApellidos;
		this.tfDni = tfDni;
		this.tfTelefono = tfTelefono;
		this.calendarNacimiento = calendarNacimiento;
		this.calendarCarnet = calendarCarnet;
		this.tfDireccion = tfDireccion;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				pintarDatos();
			}
		});

		modelo = getModelo(Constantes.COLUMNAS_CLIENTE);
		setModel(modelo);

	}

	public void listar() {

		listarComodin("FROM Clientes");

	}

	public void listar(String filtro) {

		if (filtro.startsWith("p") || filtro.startsWith("s")
				&& !filtro.substring(1).equals("")
				&& Util.esNumero(filtro.substring(1))) {
			String id = filtro.substring(1);
			if (filtro.substring(0, 1).equals("p"))
				listarComodin("select p.clientes from Polizas p where p.idPolizas = "
						+ id);
			else
				listarComodin("select s.clientes from Siniestros s where s.idSiniestros = "
						+ id);
		}

		else
			listarComodin("select c from Clientes c where c.nombre like '%"
					+ filtro + "%' or c.dni like '%" + filtro + "%'");
	}

	@SuppressWarnings("unchecked")
	private void listarComodin(String consulta) {

		modelo.setNumRows(0);

		for (Clientes cliente : (List<Clientes>) HibernateUtil.getQuery(
				consulta).list())
			modelo.addRow(new Object[] { cliente.getIdClientes(),
					cliente.getCodigoClientes(), cliente.getNombre(),
					cliente.getApellidos(), cliente.getDni(),
					cliente.getTelefono(), cliente.getFechaNacimiento(),
					cliente.getFechaCarnet(), cliente.getDireccion() });

	}

	private void pintarDatos() {
		if (getClienteSeleccionado() == null)
			return;

		tfNombre.setText(getClienteSeleccionado().getNombre());
		tfApellidos.setText(getClienteSeleccionado().getApellidos());
		tfDni.setText(getClienteSeleccionado().getDni());
		tfTelefono.setText(String.valueOf(getClienteSeleccionado()
				.getTelefono()));
		calendarNacimiento.setDate(getClienteSeleccionado()
				.getFechaNacimiento());
		calendarCarnet.setDate(getClienteSeleccionado().getFechaCarnet());
		tfDireccion.setText(getClienteSeleccionado().getDireccion());
	}

	public Clientes getClienteSeleccionado() {
		int fila = getSelectedRow();
		if (fila == -1)
			return null;

		int id = (Integer) modelo.getValueAt(fila, 0);

		return (Clientes) HibernateUtil.getCurrentSession().get(Clientes.class,
				id);
	}

}
