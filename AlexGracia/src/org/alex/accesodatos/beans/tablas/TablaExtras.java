package org.alex.accesodatos.beans.tablas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.hibernate.Extras;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.Tabla;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * Clase que implementa una tabla para manejar los Extras.
 * 
 * @author Alex Gracia
 * 
 */
public class TablaExtras extends Tabla {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;

	// Variables graficas
	private JCalendarCombo yearFabricacion;
	private TextPropio tfMarca;
	private TextPropio tfNombre;
	private TextPropio tfModelo;
	private TextPropio tfDescripcion;
	private TextPropio tfOrigen;
	private TextPropio tfDimensiones;
	private TextPropio tfFabricante;

	public TablaExtras(JCalendarCombo yearFabricacion, TextPropio tfMarca,
			TextPropio tfNombre, TextPropio tfModelo, TextPropio tfDescripcion,
			TextPropio tfOrigen, TextPropio tfDimensiones,
			TextPropio tfFabricante) {

		this.yearFabricacion = yearFabricacion;
		this.tfMarca = tfMarca;
		this.tfNombre = tfNombre;
		this.tfModelo = tfModelo;
		this.tfDescripcion = tfDescripcion;
		this.tfOrigen = tfOrigen;
		this.tfDimensiones = tfDimensiones;
		this.tfFabricante = tfFabricante;

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

		modelo = getModelo(Constantes.COLUMNAS_EXTRA);
		setModel(modelo);
	}

	public void listar() {
		listarComodin("FROM Extras");
	}

	public void listar(String filtro) {

		listarComodin("select e from Extras e where e.nombre like '%" + filtro
				+ "%' or e.modelo like '%" + filtro + "%'");
	}

	@SuppressWarnings("unchecked")
	private void listarComodin(String consulta) {

		modelo.setNumRows(0);

		for (Extras extra : (List<Extras>) HibernateUtil.getQuery(consulta)
				.list())
			modelo.addRow(new Object[] { extra.getIdExtras(),
					extra.getAnoFabricacion(), extra.getMarca(),
					extra.getNombre(), extra.getModelo(),
					extra.getDescripcion(), extra.getLugarOrigen(),
					extra.getDimensiones(), extra.getFabricante() });

	}

	private void pintarDatos() {
		Extras ExtraSeleccionado = getExtraSeleccionado();

		yearFabricacion.setDate(ExtraSeleccionado.getAnoFabricacion());
		tfMarca.setText(ExtraSeleccionado.getMarca());
		tfNombre.setText(ExtraSeleccionado.getNombre());
		tfModelo.setText(ExtraSeleccionado.getModelo());
		tfDescripcion.setText(ExtraSeleccionado.getDescripcion());
		tfOrigen.setText(ExtraSeleccionado.getLugarOrigen());
		tfDimensiones.setText(ExtraSeleccionado.getDimensiones());
		tfFabricante.setText(ExtraSeleccionado.getFabricante());
	}

	public Extras getExtraSeleccionado() {

		int fila = getSelectedRow();
		if (fila == -1)
			return null;

		int id = (Integer) modelo.getValueAt(fila, 0);

		return (Extras) HibernateUtil.getCurrentSession().get(Extras.class, id);

	}

}
