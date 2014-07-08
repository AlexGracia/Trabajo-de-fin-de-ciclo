package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.beans.tablas.TablaTalleres;
import org.alex.accesodatos.gui.JConfirmacion;
import org.alex.accesodatos.hibernate.Talleres;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * talleres.
 * 
 * @author Alex Gracia
 * 
 */
public class TabTalleres extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// Variables graficas
	private TextPropio tfNombre, tfDireccion, tfTelefono, tfNombreJefe,
			tfCifEmpresa, tfNumeroTrabajadores, tfCantidadReparaciones;
	private JCalendarCombo calendarInicio;
	private TablaTalleres tablaTalleres;

	/**
	 * TODO Constructor que prepara el interfaz.
	 * 
	 * @param tabbedPane
	 */
	public TabTalleres(JTabbedPane tabbedPane) {

		byte i = 0;
		tabbedPane.addTab(Constantes.TEXTO_TALLERES[i++], this);
		setLayout(null);

		JLabel lblNombre = new JLabel(Constantes.TEXTO_TALLERES[i++]);
		lblNombre.setFont(Constantes.FUENTE_NEGRITA);
		lblNombre.setBounds(11, 21, 99, 20);
		add(lblNombre);

		JLabel lblDireccion = new JLabel(Constantes.TEXTO_TALLERES[i++]);
		lblDireccion.setFont(Constantes.FUENTE_NEGRITA);
		lblDireccion.setBounds(11, 70, 99, 20);
		add(lblDireccion);

		LabelPropio lblTelefono = new LabelPropio(
				Constantes.TEXTO_TALLERES[i++]);
		lblTelefono.setBounds(11, 125, 99, 14);
		add(lblTelefono);

		LabelPropio lblFecha = new LabelPropio(Constantes.TEXTO_TALLERES[i++]);
		lblFecha.setBounds(11, 174, 99, 14);
		add(lblFecha);

		LabelPropio lblNombreJefe = new LabelPropio(
				Constantes.TEXTO_TALLERES[i++]);
		lblNombreJefe.setBounds(11, 223, 135, 14);
		add(lblNombreJefe);

		JLabel lblCifEmpresa = new JLabel(Constantes.TEXTO_TALLERES[i++]);
		lblCifEmpresa.setFont(Constantes.FUENTE_NEGRITA);
		lblCifEmpresa.setBounds(11, 267, 135, 20);
		add(lblCifEmpresa);

		LabelPropio lblTrabajadores = new LabelPropio(
				Constantes.TEXTO_TALLERES[i++]);
		lblTrabajadores.setBounds(11, 313, 160, 20);
		add(lblTrabajadores);

		LabelPropio lblReparaciones = new LabelPropio(
				Constantes.TEXTO_TALLERES[i]);
		lblReparaciones.setBounds(355, 313, 135, 20);
		add(lblReparaciones);

		tfNombre = new TextPropio();
		RestrictedSimple.soloTextoConLimite(tfNombre, 100);
		tfNombre.setBounds(181, 18, 120, 26);
		add(tfNombre);
		tfNombre.setColumns(10);

		tfDireccion = new TextPropio();
		RestrictedSimple.setLimite(tfDireccion, 200);
		tfDireccion.setBounds(181, 67, 120, 26);
		add(tfDireccion);
		tfDireccion.setColumns(10);

		tfTelefono = new TextPropio();
		RestrictedSimple.soloNumeros(tfTelefono);
		tfTelefono.setBounds(181, 119, 120, 26);
		add(tfTelefono);
		tfTelefono.setColumns(10);

		calendarInicio = new JCalendarCombo();
		calendarInicio.setBounds(181, 170, 120, 26);
		add(calendarInicio);

		tfNombreJefe = new TextPropio();
		RestrictedSimple.setLimite(tfNombreJefe, 100);
		tfNombreJefe.setBounds(181, 217, 120, 26);
		add(tfNombreJefe);
		tfNombreJefe.setColumns(10);

		tfCifEmpresa = new TextPropio();
		RestrictedSimple.setLimite(tfCifEmpresa, 9);
		tfCifEmpresa.setBounds(181, 264, 120, 26);
		add(tfCifEmpresa);

		tfNumeroTrabajadores = new TextPropio();
		RestrictedSimple.setLimite(tfNumeroTrabajadores, 100);
		tfNumeroTrabajadores.setBounds(181, 307, 120, 26);
		add(tfNumeroTrabajadores);
		
		tfCantidadReparaciones = new TextPropio();
		RestrictedSimple.setLimite(tfCantidadReparaciones, 100);
		tfCantidadReparaciones.setBounds(510, 307, 120, 26);
		add(tfCantidadReparaciones);

		tablaTalleres = new TablaTalleres(tfNombre, tfDireccion, tfTelefono,
				calendarInicio, tfNombreJefe, tfCifEmpresa,
				tfNumeroTrabajadores, tfCantidadReparaciones);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 625, 269);
		add(scrollPane);
		scrollPane.setViewportView(tablaTalleres);

	}

	public void mBuscarProveedor(String filtro) {
		if (filtro.equals(""))
			tablaTalleres.listar();
		else
			tablaTalleres.listar(filtro);
	}

	// TODO
	public boolean mEliminar() {
		if (!tallerSeleccionado() || !new JConfirmacion("Borrar").isAceptar())
			return false;

		HibernateUtil.setData("borrar", tablaTalleres.getTallerSeleccionado());

		tablaTalleres.listar();
		mVaciarTaller();
		return true;
	}

	// TODO
	public boolean mGuardar() {
		// Variables
		String nombre = tfNombre.getText();
		String telefono = tfTelefono.getText();
		String nombreEmpresa = tfNumeroTrabajadores.getText();
		String dni = tfCifEmpresa.getText();

		if (nombre.equals("") || telefono.equals("")
				|| nombreEmpresa.equals("") || dni.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Talleres taller;
		if (esNuevo) {
			String dniSQL = (String) HibernateUtil
					.getCurrentSession()
					.createQuery(
							"select p.dni from Proveedores p where p.dni = '"
									+ dni + "'").uniqueResult();

			if (dni.equals(dniSQL)) {
				Util.setMensajeInformacion("DNI ya existente, cámbielo.");
				tfCifEmpresa.setText("");
				return false;
			}
			taller = new Talleres();
		} else
			taller = tablaTalleres.getTallerSeleccionado();

		taller.setNombre(nombre);
		taller.setTelefono(Integer.parseInt(telefono));
		taller.setFechaInicio(calendarInicio.getDate());
		taller.setDireccion(tfDireccion.getText());

		if (esNuevo)
			HibernateUtil.setData("guardar", taller);
		else {
			HibernateUtil.setData("actualizar", taller);

			esNuevo = true;
			tfNombre.setEnabled(true);
		}

		tablaTalleres.listar();
		mVaciarTaller();
		return true;
	}

	public boolean mEditar() {
		if (!tallerSeleccionado())
			return false;

		tfNombre.setEnabled(false);

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
		mVaciarTaller();
		tfNombre.setEnabled(true);
	}

	private void mVaciarTaller() {
		tfNombre.setText("");
		tfDireccion.setText("");
		tfTelefono.setText("");
		calendarInicio.setDate(Calendar.getInstance().getTime());
		tfNombreJefe.setText("");
		tfCifEmpresa.setText("");
		tfNumeroTrabajadores.setText("");
		tfCantidadReparaciones.setText("");
	}

	public TablaTalleres getTablaTalleres() {
		return tablaTalleres;
	}

	private boolean tallerSeleccionado() {
		if (tablaTalleres.getTallerSeleccionado() == null) {
			Util.setMensajeInformacion("Seleccione una fila.");
			return false;
		}

		return true;
	}
}
