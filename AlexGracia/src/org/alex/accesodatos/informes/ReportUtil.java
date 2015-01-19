package org.alex.accesodatos.informes;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportUtil {

	// Variables
	String connectionUrl = "jdbc:mysql://localhost:3306/alex_gracia";
	String user = "root";
	String pass, jasper;
	JasperPrint print;

	public ReportUtil(String jasper) {
		try {
			Connection conexion = DriverManager.getConnection(connectionUrl,
					user, pass);

			JasperReport report = (JasperReport) JRLoader.loadObject(new File(
					jasper));

			print = JasperFillManager.fillReport(report,
					new HashMap<String, Object>(), conexion);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// TODO Ventana para exportar el pdf
	public String ExportToPDF() {
		String ruta = "Informe.pdf";
		try {
			JasperExportManager.exportReportToPdfFile(print, ruta);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ruta;
	}
}
