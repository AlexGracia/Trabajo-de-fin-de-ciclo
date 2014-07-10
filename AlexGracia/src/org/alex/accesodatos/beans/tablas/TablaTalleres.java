package org.alex.accesodatos.beans.tablas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.hibernate.Talleres;
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
public class TablaTalleres extends Tabla {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;

	// Variables graficas
	private TextPropio tfNombre, tfDireccion, tfTelefono, tfNombreJefe,
			tfCifEmpresa, tfNumeroTrabajadores, tfCantidadReparaciones;
	private JCalendarCombo calendarInicio;

	public TablaTalleres(TextPropio tfNombre, TextPropio tfDireccion,
			TextPropio tfTelefono, JCalendarCombo calendarInicio,
			TextPropio tfNombreJefe, TextPropio tfCifEmpresa,
			TextPropio tfNumeroTrabajadores, TextPropio tfCantidadReparaciones) {

		this.tfNombre = tfNombre;
		this.tfDireccion = tfDireccion;
		this.tfTelefono = tfTelefono;
		this.calendarInicio = calendarInicio;
		this.tfNombreJefe = tfNombreJefe;
		this.tfCifEmpresa = tfCifEmpresa;
		this.tfNumeroTrabajadores = tfNumeroTrabajadores;
		this.tfCantidadReparaciones = tfCantidadReparaciones;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				pintarDatos();
			}
		});

		modelo = getModelo(Constantes.COLUMNAS_TALLER);
		setModel(modelo);

	}

	public void listar() {

		listarComodin("FROM Talleres");

	}

	// TODO
	public void listar(String filtro) {

		if (filtro.startsWith("*id") && !filtro.substring(3).equals(""))
			listarComodin("select s.talleres from Siniestros s where s.idSiniestros = "
					+ filtro.substring(3));

		else
			listarComodin("select t from Talleres t where t.nombre like '%"
					+ filtro + "%' or t.direccion like '%" + filtro
					+ "%' or t.cifEmpresa like '%" + filtro + "%'");
	}

	@SuppressWarnings("unchecked")
	private void listarComodin(String consulta) {

		modelo.setNumRows(0);

		for (Talleres taller : (List<Talleres>) HibernateUtil
				.getQuery(consulta).list())
			modelo.addRow(new Object[] { taller.getIdTalleres(),
					taller.getNombre(), taller.getDireccion(),
					taller.getTelefono(), taller.getFechaInicio(),
					taller.getNombreJefe(), taller.getCifEmpresa(),
					taller.getNumeroTrabajadores(),
					taller.getCantidadReparaciones() });

	}

	private void pintarDatos() {
		if (getTallerSeleccionado() == null)
			return;

		tfNombre.setText(getTallerSeleccionado().getNombre());
		tfDireccion.setText(getTallerSeleccionado().getDireccion());
		tfTelefono.setText(String
				.valueOf(getTallerSeleccionado().getTelefono()));
		calendarInicio.setDate(getTallerSeleccionado().getFechaInicio());
		tfNombreJefe.setText(getTallerSeleccionado().getNombreJefe());
		tfCifEmpresa.setText(getTallerSeleccionado().getCifEmpresa());
		tfNumeroTrabajadores.setText(String.valueOf(getTallerSeleccionado()
				.getNumeroTrabajadores()));
		tfCantidadReparaciones.setText(String.valueOf(getTallerSeleccionado()
				.getCantidadReparaciones()));

	}

	public Talleres getTallerSeleccionado() {
		int fila = getSelectedRow();
		if (fila == -1)
			return null;

		int id = (Integer) modelo.getValueAt(fila, 0);

		return (Talleres) HibernateUtil.getCurrentSession().get(Talleres.class,
				id);
	}

}
