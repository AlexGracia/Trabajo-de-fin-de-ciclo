package org.alex.accesodatos.gui.tabs;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabTalleres extends JPanel {

	public TabTalleres(JTabbedPane tabbedPane) {
		tabbedPane.addTab("Talleres", this);
		setLayout(null);
	}
}
