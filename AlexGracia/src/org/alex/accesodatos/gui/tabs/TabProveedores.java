package org.alex.accesodatos.gui.tabs;

import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.TextPropio;
import org.alex.accesodatos.beans.tablas.TablaVehiculos;
import org.alex.accesodatos.gui.JConfirmacion;
import org.alex.accesodatos.hibernate.Vehiculos;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.RestrictedSimple;
import org.alex.libs.Util;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * proveedores.
 * 
 * @author Alex Gracia
 * 
 */
public class TabProveedores extends JPanel {
	// Variables
	private static final long serialVersionUID = 1L;
	private boolean esNuevo = true;

	// TODO Variables graficas
	private TextPropio tfMatricula, tfMarca, tfModelo, tfPotencia, tfPuertas,
			tfKilometros;
	private JCalendarCombo dateNacimiento;
	private ComboPropio cbPago;
	private TablaVehiculos tablaVehiculo;

	/**
	 * Constructor que prepara el interfaz.
	 * 
	 * @param tabbedPane
	 */
	public TabProveedores(JTabbedPane tabbedPane) {

		byte i = 0;
		tabbedPane.addTab(Constantes.TEXTO_VEHICULOS[i++], this);
		setLayout(null);

		JLabel lblMatricula = new JLabel(Constantes.TEXTO_VEHICULOS[i++]);
		lblMatricula.setFont(Constantes.FUENTE_NEGRITA);
		lblMatricula.setBounds(11, 24, 99, 14);
		add(lblMatricula);

		LabelPropio lblMarca = new LabelPropio(Constantes.TEXTO_VEHICULOS[i++]);
		lblMarca.setBounds(11, 70, 99, 20);
		add(lblMarca);

		LabelPropio lblModelo = new LabelPropio(Constantes.TEXTO_VEHICULOS[i++]);
		lblModelo.setBounds(11, 125, 99, 14);
		add(lblModelo);

		LabelPropio lblPotencia = new LabelPropio(
				Constantes.TEXTO_VEHICULOS[i++]);
		lblPotencia.setBounds(11, 174, 99, 14);
		add(lblPotencia);

		LabelPropio lblFabricacion = new LabelPropio(
				Constantes.TEXTO_VEHICULOS[i++]);
		lblFabricacion.setBounds(11, 223, 135, 14);
		add(lblFabricacion);

		LabelPropio lblColor = new LabelPropio(Constantes.TEXTO_VEHICULOS[i++]);
		lblColor.setBounds(11, 267, 135, 14);
		add(lblColor);

		LabelPropio lblPuertas = new LabelPropio(
				Constantes.TEXTO_VEHICULOS[i++]);
		lblPuertas.setBounds(11, 313, 99, 14);
		add(lblPuertas);

		JLabel lblKilometros = new JLabel(Constantes.TEXTO_VEHICULOS[i]);
		lblKilometros.setFont(Constantes.FUENTE_NEGRITA);
		lblKilometros.setBounds(355, 313, 135, 14);
		add(lblKilometros);

		tfMatricula = new TextPropio();
		RestrictedSimple.setLimite(tfMatricula, 200);
		tfMatricula.setBounds(181, 18, 120, 26);
		add(tfMatricula);
		tfMatricula.setColumns(10);

		tfMarca = new TextPropio();
		RestrictedSimple.setLimite(tfMarca, 200);
		tfMarca.setBounds(181, 67, 120, 26);
		add(tfMarca);
		tfMarca.setColumns(10);

		tfModelo = new TextPropio();
		RestrictedSimple.setLimite(tfModelo, 200);
		tfModelo.setBounds(181, 119, 120, 26);
		add(tfModelo);
		tfModelo.setColumns(10);

		tfPotencia = new TextPropio();
		RestrictedSimple.soloNumeros(tfPotencia);
		tfPotencia.setBounds(181, 168, 120, 26);
		add(tfPotencia);
		tfPotencia.setColumns(10);

		dateNacimiento = new JCalendarCombo();
		dateNacimiento.setBounds(181, 213, 120, 26);
		add(dateNacimiento);

		cbPago = new ComboPropio();
		cbPago.addItem("");
		cbPago.addItem("Metalizado");
		cbPago.addItem("Mate");
		cbPago.setBounds(181, 261, 120, 26);
		add(cbPago);

		tfPuertas = new TextPropio();
		RestrictedSimple.soloNumerosConLimite(tfPuertas, 1);
		tfPuertas.setBounds(181, 307, 120, 26);
		add(tfPuertas);

		tfKilometros = new TextPropio();
		RestrictedSimple.soloNumeros(tfKilometros);
		tfKilometros.setBounds(525, 307, 120, 26);
		add(tfKilometros);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(355, 18, 625, 269);
		add(scrollPane);

		tablaVehiculo = new TablaVehiculos(tfMatricula, tfMarca, tfModelo,
				tfPotencia, dateNacimiento, cbPago, tfPuertas, tfKilometros);
		scrollPane.setViewportView(tablaVehiculo);

	}

	public void mBuscarVehiculo(String filtro) {

		if (filtro.equals(""))
			tablaVehiculo.listar();
		else
			tablaVehiculo.listar(filtro);

	}

	public boolean mEliminar() {

		if (!vehiculoSeleccionado() || !new JConfirmacion("Borrar").isAceptar())
			return false;

		Vehiculos vehiculo = tablaVehiculo.getVehiculoSeleccionado();

		if (!HibernateUtil.setData("borrar", vehiculo)) {
			List<?> query = HibernateUtil.getQuery(
					"select p.idPolizas from Polizas p where p.vehiculos.idVehiculos = '"
							+ vehiculo.getIdVehiculos() + "'").list();
			Util.setMensajeError("Debe borrar antes la/s póliza/s nº " + query
					+ ".");
		}

		tablaVehiculo.listar();
		mVaciarVehiculo();
		return true;

	}

	public boolean mGuardar() {
		// Variables
		String matricula = tfMatricula.getText();
		String kilometros = tfKilometros.getText();

		if (matricula.equals("") || kilometros.equals("")) {
			Util.setMensajeInformacion("Rellene los campos obligatorios (*)");
			return false;
		}

		Vehiculos vehiculo;
		if (esNuevo) {
			String matriculaSQL = (String) HibernateUtil
					.getCurrentSession()
					.createQuery(
							"select v.matricula from Vehiculos v where v.matricula = '"
									+ matricula + "'").uniqueResult();

			if (matricula.equals(matriculaSQL)) {
				Util.setMensajeInformacion("Matricula ya existente, cámbiela");
				tfMatricula.setText("");
				return false;
			}
			vehiculo = new Vehiculos();
		} else
			vehiculo = tablaVehiculo.getVehiculoSeleccionado();

		vehiculo.setMatricula(matricula);
		vehiculo.setMarca(tfMarca.getText());
		vehiculo.setModelo(tfModelo.getText());

		String potencia = tfPotencia.getText();
		String puertas = tfPuertas.getText();
		if (potencia.equals(""))
			vehiculo.setPotencia(0);
		else
			vehiculo.setPotencia(Integer.parseInt(potencia));
		if (puertas.equals(""))
			vehiculo.setNumeroPuertas(0);
		else
			vehiculo.setNumeroPuertas(Integer.parseInt(puertas));

		vehiculo.setAnoFabricacion(dateNacimiento.getDate());
		vehiculo.setColor(cbPago.getSelectedString());
		vehiculo.setKilometros(Integer.parseInt(kilometros));

		if (esNuevo)
			HibernateUtil.setData("guardar", vehiculo);
		else {
			HibernateUtil.setData("actualizar", vehiculo);

			esNuevo = true;
			tfMatricula.setEnabled(true);
		}

		tablaVehiculo.listar();
		mVaciarVehiculo();
		return true;
	}

	public boolean mEditar() {
		if (!vehiculoSeleccionado())
			return false;

		tfMatricula.setEnabled(false);

		esNuevo = false;
		return true;
	}

	public void mCancelar() {
		esNuevo = true;
		mVaciarVehiculo();
		tfMatricula.setEnabled(true);
	}

	private void mVaciarVehiculo() {

		tfMatricula.setText("");
		tfMarca.setText("");
		tfModelo.setText("");
		tfPotencia.setText("");
		dateNacimiento.setDate(Calendar.getInstance().getTime());
		cbPago.setSelectedIndex(0);
		tfPuertas.setText("");
		tfKilometros.setText("");

	}

	public TablaVehiculos getTablaVehiculos() {
		return tablaVehiculo;
	}

	private boolean vehiculoSeleccionado() {
		if (tablaVehiculo.getVehiculoSeleccionado() == null) {
			Util.setMensajeInformacion("Seleccione una fila.");
			return false;
		}

		return true;
	}
}
