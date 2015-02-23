package org.alex.accesodatos.beans;

import java.awt.Toolkit;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class JViewerPropio extends JasperViewer {

	private static final long serialVersionUID = 1L;

	public JViewerPropio(JasperPrint jasperPrint, boolean isExitOnClose) {
		super(jasperPrint, isExitOnClose);

		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						JViewerPropio.class
								.getResource("/org/alex/accesodatos/iconos/IconoAplicacion.png")));

		setTitle("Vista previa");

		setVisible(true);
	}

}
