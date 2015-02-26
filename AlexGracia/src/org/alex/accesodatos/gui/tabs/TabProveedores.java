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
import org.alex.accesodatos.beans.tablas.TablaProveedores;
import org.alex.accesodatos.gui.secundarias.JConfirmacion;
import org.alex.accesodatos.hibernate.Proveedores;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.accesodatos.util.ReportUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * proveedores.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class TabProveedores extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// Variables graficas
	private TextPropio tfNombre, tfTelefono, tfCorreo, tfDireccion,
			tfNombreEmpresa, tfDni;
	private JCalendarCombo dateNacimiento;
	private ComboPropio cbPago;
	private TablaProveedores tablaProveedores;

	/**
	 * Constructor que prepara el interfaz.
	 */
	public TabProveedores() {

		byte i = 1;

		setLayout(null);

		JLabel lblNombre = new JLabel(Constantes.TEXTO_PROVEEDORES[i++]);
		lblNombre.setFont(Constantes.FUENTE_NEGRITA);
		lblNombre.setBounds(11, 24, 99, 14);
		add(lblNombre);

		JLabel lblTelefono = new JLabel(Constantes.TEXTO_PROVEEDORES[i++]);
		lblTelefono.setFont(Constantes.FUENTE_NEGRITA);
		lblTelefono.setBounds(11, 70, 99, 20);
		add(lblTelefono);

		LabelPropio lblCorreo = new LabelPropio(
				Constantes.TEXTO_PROVEEDORES[i++]);
		lblCorreo.setBounds(11, 125, 99, 14);
		add(lblCorreo);

		LabelPropio lblFecha = new LabelPropio(
				Constantes.TEXTO_PROVEEDORES[i++]);
		lblFecha.setBounds(11, 174, 99, 14);
		add(lblFecha);

		LabelPropio lblDireccion = new LabelPropio(
				Constantes.TEXTO_PROVEEDORES[i++]);
		lblDireccion.setBounds(11, 223, 135, 14);
		add(lblDireccion);

		LabelPropio lblPago = new LabelPropio(Constantes.TEXTO_PROVEEDORES[i++]);
		lblPago.setBounds(11, 267, 135, 20);
		add(lblPago);

		JLabel lblEmpresa = new JLabel(Constantes.TEXTO_PROVEEDORES[i++]);
		lblEmpresa.setFont(Constantes.FUENTE_NEGRITA);
		lblEmpresa.setBounds(11, 313, 160, 20);
		add(lblEmpresa);

		JLabel lblDni = new JLabel(Constantes.TEXTO_PROVEEDORES[i]);
		lblDni.setFont(Constantes.FUENTE_NEGRITA);
		lblDni.setBounds(355, 313, 135, 14);
		add(lblDni);

		tfNombre = new TextPropio();
		RestrictedSimple.soloTextoConLimite(tfNombre, 100);
		tfNombre.setBounds(181, 18, 120, 26);
		add(tfNombre);
		tfNombre.setColumns(10);

		tfTelefono = new TextPropio();
		RestrictedSimple.soloNumeros(tfTelefono);
		tfTelefono.setBounds(181, 67, 120, 26);
		add(tfTelefono);
		tfTelefono.setColumns(10);

		tfCorreo = new TextPropio();
		RestrictedSimple.setLimite(tfCorreo, 100);
		tfCorreo.setBounds(181, 119, 120, 26);
		add(tfCorreo);
		tfCorreo.setColumns(10);

		dateNacimiento = new JCalendarCombo();
		dateNacimiento.setBounds(181, 170, 120, 26);
		add(dateNacimiento);

		tfDireccion = new TextPropio();
		RestrictedSimple.setLimite(tfDireccion, 200);
		tfDireccion.setBounds(181, 217, 120, 26);
		add(tfDireccion);
		tfDireccion.setColumns(10);

		cbPago = new ComboPropio();
		cbPago.addItem("");
		cbPago.addItem("Con_plazos");
		cbPago.addItem("Sin_plazos");
		cbPago.setBounds(181, 261, 120, 26);
		add(cbPago);

		tfNombreEmpresa = new TextPropio();
		RestrictedSimple.setLimite(tfNombreEmpresa, 100);
		tfNombreEmpresa.setBounds(181, 307, 120, 26);
		add(tfNombreEmpresa);

		tfDni = new TextPropio();
		RestrictedSimple.setLimite(tfDni, 9);
		tfDni.setBounds(525, 307, 120, 26);
		add(tfDni);

		tablaProveedores = new TablaProveedores(cbPago, tfNombre,
				dateNacimiento, tfTelefono, tfCorreo, tfDireccion,
				tfNombreEmpresa, tfDni);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 635, 270);
		add(scrollPane);
		scrollPane.setViewportView(tablaProveedores);

	}

	public void mBuscarProveedor(String filtro) {
		if (filtro.equals(""))
			tablaProveedores.listar();
		else
			tablaProveedores.listar(filtro);
	}

	public boolean mEliminar() {
		if (!proveedorSeleccionado()
				|| !new JConfirmacion("Borrar").isAceptar())
			return false;

		HibernateUtil.setData("borrar",
				tablaProveedores.getProveedorSeleccionado());

		tablaProveedores.listar();
		mVaciarProveedor();
		return true;
	}

	public boolean mGuardar() {
		// Variables
		String nombre = tfNombre.getText();
		String telefono = tfTelefono.getText();
		String nombreEmpresa = tfNombreEmpresa.getText();
		String dni = tfDni.getText();

		if (nombre.equals("") || telefono.equals("")
				|| nombreEmpresa.equals("") || dni.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		// Comprobar que la fecha no sea posterior a la actual.
		Date fechaNacimiento = dateNacimiento.getDate();

		if (Util.esMenorDeEdad(fechaNacimiento))
			return false;

		Proveedores proveedor;
		if (esNuevo) {
			String dniSQL = (String) HibernateUtil
					.getCurrentSession()
					.createQuery(
							"select p.dni from Proveedores p where p.dni = '"
									+ dni + "'").uniqueResult();

			if (dni.equals(dniSQL)) {
				Util.setMensajeInformacion("DNI ya existente, cámbielo.");
				tfDni.setText("");
				return false;
			}
			proveedor = new Proveedores();
		} else
			proveedor = tablaProveedores.getProveedorSeleccionado();

		proveedor.setNombre(nombre);
		proveedor.setTelefono(Integer.parseInt(telefono));
		proveedor.setCorreoElectronico(tfCorreo.getText());
		proveedor.setFechaNacimiento(fechaNacimiento);
		proveedor.setDireccion(tfDireccion.getText());
		proveedor.setFacilidadPago(cbPago.getSelectedString());
		proveedor.setNombreEmpresa(nombreEmpresa);
		proveedor.setDni(dni);

		if (esNuevo)
			HibernateUtil.setData("guardar", proveedor);
		else {
			HibernateUtil.setData("actualizar", proveedor);

			esNuevo = true;
			tfNombre.setEnabled(true);
		}

		tablaProveedores.listar();
		mVaciarProveedor();
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

			if (proveedorSeleccionado()) {
				Proveedores proveedor = tablaProveedores
						.getProveedorSeleccionado();

				parametro.put("id", proveedor.getIdProveedores());

			} else
				return;

		new ReportUtil(parent, "report/proveedores/report_proveedores" + opcion
				+ ".jasper", parametro);

	}

	public boolean mEditar() {
		if (!proveedorSeleccionado())
			return false;

		tfNombre.setEnabled(false);

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
		mVaciarProveedor();
		tfNombre.setEnabled(true);
	}

	private void mVaciarProveedor() {

		tfNombre.setText("");
		tfTelefono.setText("");
		tfCorreo.setText("");
		dateNacimiento.setDate(Calendar.getInstance().getTime());
		tfDireccion.setText("");
		cbPago.setSelectedIndex(0);
		tfNombreEmpresa.setText("");
		tfDni.setText("");

	}

	public TablaProveedores getTablaProveedores() {
		return tablaProveedores;
	}

	private boolean proveedorSeleccionado() {
		if (tablaProveedores.getProveedorSeleccionado() == null) {
			Util.setMensajeInformacion("Seleccione una fila.");
			return false;
		}

		return true;
	}
}
