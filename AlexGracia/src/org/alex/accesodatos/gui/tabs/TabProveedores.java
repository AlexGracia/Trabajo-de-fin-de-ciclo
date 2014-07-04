package org.alex.accesodatos.gui.tabs;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * JPanel con los componentes necesarios para guardar, editar, borrar y buscar
 * proveedores.
 * 
 * @author Alex Gracia
 * 
 */
public class TabProveedores extends JPanel {

	public TabProveedores(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Proveedores", this);
		setLayout(null);
	}
}
