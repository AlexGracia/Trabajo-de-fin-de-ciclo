package org.alex.accesodatos.beans.tablas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.hibernate.Vehiculos;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.Tabla;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * Clase que implementa una tabla para manejar los Vehiculos.
 * 
 * @author Alex Gracia
 * 
 */
public class TablaVehiculos extends Tabla {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;

	// Variables graficas
	private TextPropio tfMatricula, tfMarca, tfModelo, tfPotencia, tfPuertas,
			tfKilometros;
	private JCalendarCombo yearFabricacion;
	private ComboPropio cbColor;

	public TablaVehiculos(TextPropio tfMatricula, TextPropio tfMarca,
			TextPropio tfModelo, TextPropio tfPotencia,
			JCalendarCombo yearFabricacion, ComboPropio cbColor,
			TextPropio tfPuertas, TextPropio tfKilometros) {

		this.tfMatricula = tfMatricula;
		this.tfMarca = tfMarca;
		this.tfModelo = tfModelo;
		this.tfPotencia = tfPotencia;
		this.yearFabricacion = yearFabricacion;
		this.cbColor = cbColor;
		this.tfPuertas = tfPuertas;
		this.tfKilometros = tfKilometros;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				pintarDatos();
			}
		});

		modelo = getModelo(Constantes.COLUMNAS_VEHICULO);
		setModel(modelo);
	}

	public void listar() {
		listarComodin("FROM Vehiculos");
	}

	public void listar(String filtro) {

		if (filtro.startsWith("p") && !filtro.substring(1).equals("")
				&& Util.esNumero(filtro.substring(1)))
			listarComodin("select p.vehiculos from Polizas p where p.idPolizas = "
					+ filtro.substring(1));
		else
			listarComodin("select v from Vehiculos v where v.matricula like '%"
					+ filtro + "%' or v.kilometros like '" + filtro + "'");
	}

	@SuppressWarnings("unchecked")
	private void listarComodin(String consulta) {

		modelo.setNumRows(0);

		for (Vehiculos vehiculo : (List<Vehiculos>) HibernateUtil.getQuery(
				consulta).list())
			modelo.addRow(new Object[] { vehiculo.getIdVehiculos(),
					vehiculo.getNumeroChasis(), vehiculo.getMatricula(),
					vehiculo.getMarca(), vehiculo.getModelo(),
					vehiculo.getPotencia(), vehiculo.getAnoFabricacion(),
					vehiculo.getColor(), vehiculo.getNumeroPuertas(),
					vehiculo.getKilometros() });

	}

	private void pintarDatos() {
		if (getVehiculoSeleccionado() == null)
			return;

		tfMatricula.setText(getVehiculoSeleccionado().getMatricula());
		tfMarca.setText(getVehiculoSeleccionado().getMarca());
		tfModelo.setText(getVehiculoSeleccionado().getModelo());
		tfPotencia.setText(String.valueOf(getVehiculoSeleccionado()
				.getPotencia()));
		yearFabricacion.setDate(getVehiculoSeleccionado().getAnoFabricacion());
		cbColor.setSelectedItem(getVehiculoSeleccionado().getColor());
		tfPuertas.setText(String.valueOf(getVehiculoSeleccionado()
				.getNumeroPuertas()));
		tfKilometros.setText(String.valueOf(getVehiculoSeleccionado()
				.getKilometros()));
	}

	public Vehiculos getVehiculoSeleccionado() {

		int fila = getSelectedRow();
		if (fila == -1)
			return null;

		int id = (Integer) modelo.getValueAt(fila, 0);

		return (Vehiculos) HibernateUtil.getCurrentSession().get(
				Vehiculos.class, id);

	}

}
