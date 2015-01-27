package org.alex.accesodatos.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.alex.libs.Util;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Clase que facilita el trabajo para crear informes.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class ReportUtil {

	// Variables
	private String connectionUrl = "jdbc:mysql://localhost:3306/alex_gracia";
	private String user = "root", pass;
	private JasperPrint print;
	private File ficheroSeleccionado;
	private boolean cancelar = false;

	public ReportUtil(JFrame parent, String jasper,
			HashMap<String, Object> parametro) {

		_FileChooser(parent);

		if (cancelar)
			return;

		try {
			Connection conexion = DriverManager.getConnection(connectionUrl,
					user, pass);

			// Comprobar si el fichero existe
			File ficheroJasper = new File(jasper);
			if (!ficheroJasper.exists()) {
				Util.setMensajeError("El fichero, "
						+ ficheroJasper.getAbsolutePath() + ", no existe.");
				_cancelar();
				return;
			}

			JasperReport report = (JasperReport) JRLoader
					.loadObject(ficheroJasper);

			print = JasperFillManager.fillReport(report, parametro, conexion);

		} catch (CommunicationsException ce) {
			Util.setMensajeError("Posibles causas:\n"
					+ "1. Programa MySQL no instalado.\n"
					+ "2. Servicio MySQL inoperativo.");
			_cancelar();
		} catch (Exception e) {
			e.printStackTrace();
			_cancelar();
		}

	}

	/**
	 * Método encargado de exportar el informe en formato PDF.
	 */
	public void ExportToPDF() {

		if (cancelar)
			return;

		try {
			JasperExportManager.exportReportToPdfFile(print,
					ficheroSeleccionado.getAbsolutePath());
		} catch (JRException e) {
			Util.setMensajeError("Exportando informe a PDF.");
			e.printStackTrace();
			_cancelar();
		}
	}

	/**
	 * Método encargado de crear un JFileChooser para guardar ficheros PDF.
	 * 
	 * @param parent
	 *            (este parámetro es necesario para que el JFileChooser tenga el
	 *            icono de la aplicación).
	 */
	private void _FileChooser(JFrame parent) {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter(
				"Archivos PDF", "pdf");
		fc.setFileFilter(filtro);

		File ficheroDefault = new File("Informe.pdf");
		fc.setSelectedFile(ficheroDefault);

		fc.setDialogTitle("Guardar informe");

		if (fc.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION)
			_cancelar();

		else
			ficheroSeleccionado = fc.getSelectedFile();
	}

	/**
	 * Método encargado de poner a true la variable cancelar.
	 */
	private void _cancelar() {
		cancelar = true;
	}
}
