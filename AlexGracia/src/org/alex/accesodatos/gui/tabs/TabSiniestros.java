package org.alex.accesodatos.gui.tabs;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * siniestros.
 * 
 * @author Alex Gracia
 * 
 */
public class TabSiniestros extends JPanel {

	public TabSiniestros(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Siniestros", this);
		setLayout(null);
	}
}
