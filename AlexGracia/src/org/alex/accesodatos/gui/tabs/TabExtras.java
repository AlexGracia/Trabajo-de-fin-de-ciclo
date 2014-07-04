package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.beans.tablas.TablaExtras;
import org.alex.accesodatos.gui.JConfirmacion;
import org.alex.accesodatos.hibernate.Extras;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * extras.
 * 
 * @author Alex Gracia
 * 
 */
public class TabExtras extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// Variables graficas
	private JCalendarCombo yearFabricacion;
	private TextPropio tfMarca, tfNombre, tfModelo, tfDescripcion, tfOrigen,
			tfDimensiones, tfFabricante;
	private TablaExtras tablaExtras;

	/**
	 * Constructor que prepara el interfaz.
	 * 
	 * @param tabbedPane
	 */
	public TabExtras(JTabbedPane tabbedPane) {

		byte i = 0;
		tabbedPane.addTab(Constantes.TEXTO_EXTRAS[i++], this);
		setLayout(null);

		LabelPropio lblYearFabricacion = new LabelPropio(
				Constantes.TEXTO_EXTRAS[i++]);
		lblYearFabricacion.setBounds(11, 24, 135, 14);
		add(lblYearFabricacion);

		LabelPropio lblMarca = new LabelPropio(Constantes.TEXTO_EXTRAS[i++]);
		lblMarca.setBounds(11, 70, 99, 20);
		add(lblMarca);

		JLabel lblNombre = new JLabel(Constantes.TEXTO_EXTRAS[i++]);
		lblNombre.setFont(Constantes.FUENTE_NEGRITA);
		lblNombre.setBounds(11, 125, 99, 14);
		add(lblNombre);

		JLabel lblModelo = new JLabel(Constantes.TEXTO_EXTRAS[i++]);
		lblModelo.setFont(Constantes.FUENTE_NEGRITA);
		lblModelo.setBounds(11, 174, 99, 14);
		add(lblModelo);

		LabelPropio lblDescripcion = new LabelPropio(
				Constantes.TEXTO_EXTRAS[i++]);
		lblDescripcion.setBounds(11, 223, 135, 20);
		add(lblDescripcion);

		LabelPropio lblOrigen = new LabelPropio(Constantes.TEXTO_EXTRAS[i++]);
		lblOrigen.setBounds(11, 267, 135, 20);
		add(lblOrigen);

		JLabel lblDimensiones = new JLabel(Constantes.TEXTO_EXTRAS[i++]);
		lblDimensiones.setFont(Constantes.FUENTE_NEGRITA);
		lblDimensiones.setBounds(11, 318, 135, 14);
		add(lblDimensiones);

		LabelPropio lblFabricante = new LabelPropio(Constantes.TEXTO_EXTRAS[i]);
		lblFabricante.setBounds(355, 318, 135, 14);
		add(lblFabricante);

		yearFabricacion = new JCalendarCombo();
		yearFabricacion.setBounds(181, 18, 120, 26);
		add(yearFabricacion);

		tfMarca = new TextPropio();
		tfMarca.setBounds(181, 67, 120, 26);
		add(tfMarca);
		tfMarca.setColumns(10);

		tfNombre = new TextPropio();
		tfNombre.setBounds(181, 119, 120, 26);
		add(tfNombre);
		tfNombre.setColumns(10);

		tfModelo = new TextPropio();
		tfModelo.setBounds(181, 168, 120, 26);
		add(tfModelo);
		tfModelo.setColumns(10);

		tfDescripcion = new TextPropio();
		tfDescripcion.setBounds(181, 217, 120, 26);
		add(tfDescripcion);
		tfDescripcion.setColumns(10);

		tfOrigen = new TextPropio();
		RestrictedSimple.soloTexto(tfOrigen);
		tfOrigen.setBounds(181, 261, 120, 26);
		add(tfOrigen);

		tfDimensiones = new TextPropio();
		tfDimensiones.setBounds(181, 306, 120, 26);
		add(tfDimensiones);

		tfFabricante = new TextPropio();
		RestrictedSimple.soloTexto(tfFabricante);
		tfFabricante.setBounds(525, 307, 120, 26);
		add(tfFabricante);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 625, 269);
		add(scrollPane);

		tablaExtras = new TablaExtras(yearFabricacion, tfMarca, tfNombre,
				tfModelo, tfDescripcion, tfOrigen, tfDimensiones, tfFabricante);
		scrollPane.setViewportView(tablaExtras);

	}

	public void mBuscarExtra(String filtro) {

		if (filtro.equals(""))
			tablaExtras.listar();
		else
			tablaExtras.listar(filtro);

	}

	public boolean mEliminar() {

		if (!extraSeleccionado() || !new JConfirmacion("Borrar").isAceptar())
			return false;

		Extras extra = tablaExtras.getExtraSeleccionado();

		HibernateUtil.setData("borrar", extra);

		tablaExtras.listar();
		mVaciarExtra();
		return true;

	}

	public boolean mGuardar() {
		// Variables
		String nombre = tfNombre.getText();
		String modelo = tfModelo.getText();
		String dimensiones = tfDimensiones.getText();

		if (nombre.equals("") || modelo.equals("") || dimensiones.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Extras extra;
		if (esNuevo)
			extra = new Extras();
		else
			extra = tablaExtras.getExtraSeleccionado();

		extra.setAnoFabricacion(yearFabricacion.getDate());
		extra.setMarca(tfMarca.getText());
		extra.setNombre(nombre);
		extra.setModelo(tfModelo.getText());
		extra.setDescripcion(tfDescripcion.getText());
		extra.setLugarOrigen(tfOrigen.getText());
		extra.setDimensiones(dimensiones);
		extra.setFabricante(tfFabricante.getText());

		if (esNuevo)
			HibernateUtil.setData("guardar", extra);

		else {
			HibernateUtil.setData("actualizar", extra);

			esNuevo = true;
			tfNombre.setEnabled(true);
		}

		tablaExtras.listar();
		mVaciarExtra();
		return true;
	}

	public boolean mEditar() {
		if (!extraSeleccionado())
			return false;

		tfNombre.setEnabled(false);

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
		mVaciarExtra();
		tfNombre.setEnabled(true);
	}

	private void mVaciarExtra() {
		yearFabricacion.setDate(Calendar.getInstance().getTime());
		tfMarca.setText("");
		tfNombre.setText("");
		tfModelo.setText("");
		tfDescripcion.setText("");
		tfOrigen.setText("");
		tfDimensiones.setText("");
		tfFabricante.setText("");
	}

	public TablaExtras getTablaExtras() {
		return tablaExtras;
	}

	private boolean extraSeleccionado() {
		if (tablaExtras.getExtraSeleccionado() == null) {
			Util.setMensajeInformacion("Seleccione una fila.");
			return false;
		}

		return true;
	}
}
