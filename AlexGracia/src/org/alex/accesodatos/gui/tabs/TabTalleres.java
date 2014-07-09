package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;
import java.util.List;

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
	 * Constructor que prepara el interfaz.
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
		RestrictedSimple.soloTextoConLimite(tfNombreJefe, 100);
		tfNombreJefe.setBounds(181, 217, 120, 26);
		add(tfNombreJefe);
		tfNombreJefe.setColumns(10);

		tfCifEmpresa = new TextPropio();
		RestrictedSimple.setLimite(tfCifEmpresa, 20);
		tfCifEmpresa.setBounds(181, 264, 120, 26);
		add(tfCifEmpresa);

		tfNumeroTrabajadores = new TextPropio();
		RestrictedSimple.soloNumeros(tfNumeroTrabajadores);
		tfNumeroTrabajadores.setBounds(181, 307, 120, 26);
		add(tfNumeroTrabajadores);

		tfCantidadReparaciones = new TextPropio();
		RestrictedSimple.soloNumeros(tfCantidadReparaciones);
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

	public boolean mEliminar() {
		if (!tallerSeleccionado() || !new JConfirmacion("Borrar").isAceptar())
			return false;

		Talleres taller = tablaTalleres.getTallerSeleccionado();

		if (!HibernateUtil.setData("borrar", taller)) {
			List<?> query = HibernateUtil.getQuery(
					"select s.idSiniestros from Siniestros s where s.talleres.idTalleres = "
							+ taller.getIdTalleres()).list();
			Util.setMensajeError("Debe borrar antes el/los siniestro/s nº "
					+ query + ".");
		}

		tablaTalleres.listar();
		mVaciarTaller();
		return true;
	}

	public boolean mGuardar() {
		// Variables
		String nombre = tfNombre.getText();
		String direccion = tfDireccion.getText();
		String cifEmpresa = tfCifEmpresa.getText();

		if (nombre.equals("") || direccion.equals("") || cifEmpresa.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Talleres taller;
		if (esNuevo) {
			String cifEmpresaSQL = (String) HibernateUtil
					.getCurrentSession()
					.createQuery(
							"select t.cifEmpresa from Talleres t where t.cifEmpresa = '"
									+ cifEmpresa + "'").uniqueResult();

			if (cifEmpresa.equals(cifEmpresaSQL)) {
				Util.setMensajeInformacion("CIF ya existente, cámbielo.");
				tfCifEmpresa.setText("");
				return false;
			}
			taller = new Talleres();
		} else
			taller = tablaTalleres.getTallerSeleccionado();

		taller.setNombre(nombre);
		taller.setDireccion(direccion);
		String texto = tfTelefono.getText();
		if (texto.equals(""))
			taller.setTelefono(0);
		else
			taller.setTelefono(Integer.parseInt(tfTelefono.getText()));
		taller.setFechaInicio(calendarInicio.getDate());
		taller.setNombreJefe(tfNombreJefe.getText());
		taller.setCifEmpresa(cifEmpresa);
		texto = tfNumeroTrabajadores.getText();
		if (texto.equals(""))
			taller.setNumeroTrabajadores(0);
		else
			taller.setNumeroTrabajadores(Integer.parseInt(texto));
		texto = tfCantidadReparaciones.getText();
		if (texto.equals(""))
			taller.setCantidadReparaciones(0);
		else
			taller.setCantidadReparaciones(Integer.parseInt(texto));

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
