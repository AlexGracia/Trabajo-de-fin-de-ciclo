package org.alex.accesodatos.gui.tabs;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabProveedores extends JPanel {

	public TabProveedores(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Proveedores", this);
		setLayout(null);
	}
}
