package org.alex.accesodatos.util;

import java.awt.Cursor;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.alex.accesodatos.beans.JViewerPropio;
import org.alex.libs.Util;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

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

	public ReportUtil(JFrame parent, String jasper,
			HashMap<String, Object> parametro) {

		parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			Connection conexion = DriverManager.getConnection(connectionUrl,
					user, pass);

			// Comprobar si el fichero existe
			File ficheroJasper = new File(jasper);
			if (!ficheroJasper.exists()) {
				Util.setMensajeError("El fichero, "
						+ ficheroJasper.getAbsolutePath() + ", no existe.");
				return;
			}

			JasperReport report = (JasperReport) JRLoader
					.loadObject(ficheroJasper);

			JasperPrint print = JasperFillManager.fillReport(report, parametro,
					conexion);

			// JasperViewer
			new JViewerPropio(print, false);

			parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		} catch (CommunicationsException ce) {
			Util.setMensajeError("Posibles causas:\n"
					+ "1. Programa MySQL no instalado.\n"
					+ "2. Servicio MySQL inoperativo.");
		} catch (Exception e) {
			e.printStackTrace();
			Util.setMensajeError("Exportando, más detalles:\n" + e.getMessage());
		}

	}

}
