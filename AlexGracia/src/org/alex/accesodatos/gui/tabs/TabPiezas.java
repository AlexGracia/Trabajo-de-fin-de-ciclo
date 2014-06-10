package org.alex.accesodatos.gui.tabs;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabPiezas extends JPanel {

	public TabPiezas(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Piezas", this);
		setLayout(null);
	}
}
