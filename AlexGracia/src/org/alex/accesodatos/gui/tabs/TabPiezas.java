package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.beans.tablas.TablaPiezas;
import org.alex.accesodatos.gui.JConfirmacion;
import org.alex.accesodatos.hibernate.Piezas;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * piezas.
 * 
 * @author Alex Gracia
 * 
 */
public class TabPiezas extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// Variables graficas
	private JCalendarCombo calendarSolicitud;
	private TextPropio tfNombre, tfDescripcion, tfCantidad, tfPrecio, tfOrigen,
			tfMarca;
	private TablaPiezas tablaPiezas;

	/**
	 * Constructor que prepara el interfaz.
	 * 
	 * @param tabbedPane
	 */
	public TabPiezas(JTabbedPane tabbedPane) {

		byte i = 0;
		tabbedPane.addTab(Constantes.TEXTO_PIEZAS[i++], this);
		setLayout(null);

		JLabel lblNombre = new JLabel(Constantes.TEXTO_PIEZAS[i++]);
		lblNombre.setFont(Constantes.FUENTE_NEGRITA);
		lblNombre.setBounds(11, 24, 135, 14);
		add(lblNombre);

		LabelPropio lblDescripcion = new LabelPropio(
				Constantes.TEXTO_PIEZAS[i++]);
		lblDescripcion.setBounds(11, 70, 99, 20);
		add(lblDescripcion);

		LabelPropio lblCantidad = new LabelPropio(Constantes.TEXTO_PIEZAS[i++]);
		lblCantidad.setBounds(11, 125, 99, 14);
		add(lblCantidad);

		LabelPropio lblPercio = new LabelPropio(Constantes.TEXTO_PIEZAS[i++]);
		lblPercio.setBounds(11, 174, 99, 14);
		add(lblPercio);

		JLabel lblOrigen = new JLabel(Constantes.TEXTO_PIEZAS[i++]);
		lblOrigen.setFont(Constantes.FUENTE_NEGRITA);
		lblOrigen.setBounds(11, 223, 135, 20);
		add(lblOrigen);

		LabelPropio lblFechaSolicitud = new LabelPropio(
				Constantes.TEXTO_PIEZAS[i++]);
		lblFechaSolicitud.setBounds(11, 267, 135, 20);
		add(lblFechaSolicitud);

		LabelPropio lblMarca = new LabelPropio(Constantes.TEXTO_PIEZAS[i++]);
		lblMarca.setBounds(11, 318, 135, 14);
		add(lblMarca);

		tfNombre = new TextPropio();
		RestrictedSimple.setLimite(tfNombre, 100);
		tfNombre.setBounds(181, 18, 120, 26);
		add(tfNombre);

		tfDescripcion = new TextPropio();
		RestrictedSimple.setLimite(tfDescripcion, 250);
		tfDescripcion.setBounds(181, 67, 120, 26);
		add(tfDescripcion);
		tfDescripcion.setColumns(10);

		tfCantidad = new TextPropio();
		RestrictedSimple.soloNumeros(tfCantidad);
		tfCantidad.setBounds(181, 119, 120, 26);
		add(tfCantidad);
		tfCantidad.setColumns(10);

		tfPrecio = new TextPropio();
		RestrictedSimple.soloNumeros(tfPrecio);
		tfPrecio.setBounds(181, 168, 120, 26);
		add(tfPrecio);
		tfPrecio.setColumns(10);

		tfOrigen = new TextPropio();
		RestrictedSimple.soloTextoConLimite(tfOrigen, 200);
		tfOrigen.setBounds(181, 217, 120, 26);
		add(tfOrigen);
		tfOrigen.setColumns(10);

		calendarSolicitud = new JCalendarCombo();
		calendarSolicitud.setBounds(181, 266, 120, 26);
		add(calendarSolicitud);

		tfMarca = new TextPropio();
		RestrictedSimple.setLimite(tfMarca, 100);
		tfMarca.setBounds(181, 306, 120, 26);
		add(tfMarca);

		tablaPiezas = new TablaPiezas(tfNombre, tfDescripcion, tfCantidad,
				tfPrecio, tfOrigen, calendarSolicitud, tfMarca);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 625, 269);
		add(scrollPane);
		scrollPane.setViewportView(tablaPiezas);

	}

	public void mBuscarExtra(String filtro) {

		if (filtro.equals(""))
			tablaPiezas.listar();
		else
			tablaPiezas.listar(filtro);

	}

	public boolean mEliminar() {

		if (!piezaSeleccionada() || !new JConfirmacion("Borrar").isAceptar())
			return false;

		Piezas pieza = tablaPiezas.getPiezaSeleccionada();

		HibernateUtil.setData("borrar", pieza);

		tablaPiezas.listar();
		mVaciarPieza();
		return true;

	}

	public boolean mGuardar() {
		// Variables
		String nombre = tfNombre.getText();
		String origen = tfOrigen.getText();

		if (nombre.equals("") || origen.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Piezas pieza;
		if (esNuevo)
			pieza = new Piezas();
		else
			pieza = tablaPiezas.getPiezaSeleccionada();

		pieza.setNombre(nombre);
		pieza.setDescripcion(tfDescripcion.getText());
		String texto = tfCantidad.getText();
		if (texto.equals(""))
			pieza.setCantidad(0);
		else
			pieza.setCantidad(Integer.parseInt(tfCantidad.getText()));
		texto = tfPrecio.getText();
		if (texto.equals(""))
			pieza.setPrecio(0);
		else
			pieza.setPrecio(Integer.parseInt(tfPrecio.getText()));
		pieza.setLugarOrigen(tfOrigen.getText());
		pieza.setFechaSolicitud(calendarSolicitud.getDate());
		pieza.setMarca(tfMarca.getText());

		if (esNuevo)
			HibernateUtil.setData("guardar", pieza);
		else {
			HibernateUtil.setData("actualizar", pieza);

			esNuevo = true;
			tfNombre.setEnabled(true);
		}

		tablaPiezas.listar();
		mVaciarPieza();
		return true;
	}

	public boolean mEditar() {
		if (!piezaSeleccionada())
			return false;

		tfNombre.setEnabled(false);

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
		mVaciarPieza();
		tfNombre.setEnabled(true);
	}

	private void mVaciarPieza() {
		tfNombre.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfOrigen.setText("");
		calendarSolicitud.setDate(Calendar.getInstance().getTime());
		tfMarca.setText("");
	}

	public TablaPiezas getTablaPiezas() {
		return tablaPiezas;
	}

	private boolean piezaSeleccionada() {
		if (tablaPiezas.getPiezaSeleccionada() == null) {
			Util.setMensajeInformacion("Seleccione una fila.");
			return false;
		}

		return true;
	}
}
