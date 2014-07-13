package org.alex.accesodatos.beans.tablas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.hibernate.Proveedores;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.Tabla;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * Clase que implementa una tabla para manejar los proveedores.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class TablaProveedores extends Tabla {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;

	// Variables graficas
	private ComboPropio cbPago;
	private TextPropio tfNombre, tfTelefono, tfCorreo, tfDireccion,
			tfNombreEmpresa, tfDni;
	private JCalendarCombo dateNacimiento;

	public TablaProveedores(ComboPropio cbPago, TextPropio tfNombre,
			JCalendarCombo dateNacimiento, TextPropio tfTelefono,
			TextPropio tfCorreo, TextPropio tfDireccion,
			TextPropio tfNombreEmpresa, TextPropio tfDni) {

		this.cbPago = cbPago;
		this.tfNombre = tfNombre;
		this.dateNacimiento = dateNacimiento;
		this.tfTelefono = tfTelefono;
		this.tfCorreo = tfCorreo;
		this.tfDireccion = tfDireccion;
		this.tfNombreEmpresa = tfNombreEmpresa;
		this.tfDni = tfDni;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				pintarDatos();
			}
		});

		modelo = getModelo(Constantes.COLUMNAS_PROVEEDOR);
		setModel(modelo);
	}

	public void listar() {
		listarComodin("FROM Proveedores");
	}

	public void listar(String filtro) {

		listarComodin("select p from Proveedores p where p.nombre like '%"
				+ filtro + "%' or p.telefono like '%" + filtro
				+ "%' or p.nombreEmpresa like '%" + filtro
				+ "%' or p.dni like '%" + filtro + "%'");
	}

	@SuppressWarnings("unchecked")
	private void listarComodin(String consulta) {

		modelo.setNumRows(0);

		for (Proveedores proveedor : (List<Proveedores>) HibernateUtil
				.getQuery(consulta).list())
			modelo.addRow(new Object[] { proveedor.getIdProveedores(),
					proveedor.getNombre(), proveedor.getTelefono(),
					proveedor.getCorreoElectronico(),
					proveedor.getFechaNacimiento(), proveedor.getDireccion(),
					proveedor.getFacilidadPago(), proveedor.getNombreEmpresa(),
					proveedor.getDni() });

	}

	private void pintarDatos() {
		if (getProveedorSeleccionado() == null)
			return;

		cbPago.setSelectedItem(getProveedorSeleccionado().getFacilidadPago());
		tfNombre.setText(getProveedorSeleccionado().getNombre());
		dateNacimiento.setDate(getProveedorSeleccionado().getFechaNacimiento());
		tfTelefono.setText(String.valueOf(getProveedorSeleccionado()
				.getTelefono()));
		tfCorreo.setText(getProveedorSeleccionado().getCorreoElectronico());
		tfDireccion.setText(getProveedorSeleccionado().getDireccion());
		tfNombreEmpresa.setText(getProveedorSeleccionado().getNombreEmpresa());
		tfDni.setText(getProveedorSeleccionado().getDni());

	}

	public Proveedores getProveedorSeleccionado() {

		int fila = getSelectedRow();
		if (fila == -1)
			return null;

		int id = (Integer) modelo.getValueAt(fila, 0);

		return (Proveedores) HibernateUtil.getCurrentSession().get(
				Proveedores.class, id);

	}

}