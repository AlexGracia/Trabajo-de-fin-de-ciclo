package org.alex.accesodatos.gui.tabs;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabSiniestros extends JPanel {

	public TabSiniestros(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Siniestros", this);
		setLayout(null);
	}
}
