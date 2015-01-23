package org.alex.accesodatos.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private JFileChooser fc;
	private FileNameExtensionFilter filtro;

	public ReportUtil(String jasper) {
		try {
			Connection conexion = DriverManager.getConnection(connectionUrl,
					user, pass);

			JasperReport report = (JasperReport) JRLoader.loadObject(new File(
					jasper));

			print = JasperFillManager.fillReport(report,
					new HashMap<String, Object>(), conexion);

			// JFileChooser
			fc = new JFileChooser();
			filtro = new FileNameExtensionFilter("Archivos PDF", "pdf");
			fc.setFileFilter(filtro);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ExportToPDF(JFrame parent) {
		// JFileChooser
		File ficheroSeleccionado;

		File ficheroDefault = new File("Informe.pdf");
		fc.setSelectedFile(ficheroDefault);

		fc.setDialogTitle("Guardar informe");

		if (fc.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION)
			return;

		ficheroSeleccionado = fc.getSelectedFile();

		// Exportar a PDF
		try {
			JasperExportManager.exportReportToPdfFile(print,
					ficheroSeleccionado.getAbsolutePath());
		} catch (JRException e) {
			e.printStackTrace();
		}
		return;
	}
}
