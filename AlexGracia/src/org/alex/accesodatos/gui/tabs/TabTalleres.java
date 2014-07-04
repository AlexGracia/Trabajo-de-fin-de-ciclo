package org.alex.accesodatos.gui.tabs;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * talleres.
 * 
 * @author Alex Gracia
 * 
 */
public class TabTalleres extends JPanel {

	public TabTalleres(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Talleres", this);
		setLayout(null);
	}
}
