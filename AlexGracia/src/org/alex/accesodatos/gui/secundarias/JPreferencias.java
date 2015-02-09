package org.alex.accesodatos.gui.secundarias;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

import org.alex.accesodatos.beans.ComboPropio;
import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.util.Constantes;
import org.alex.libs.Util;

/**
 * JDialog encargado de configurar el programa.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class JPreferencias extends DialogPropio {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane_1;
	private ComboPropio comboFuente;

	/**
	 * Create the dialog.
	 */
	public JPreferencias(JTabbedPane tabbedPane, JPanel contentPane) {

		setTitle("Configuraci�n");
		setSize(new Dimension(500, 350));
		setLocationRelativeTo(this);
		contentPanel.setLayout(new BorderLayout(0, 0));

		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setFont(Constantes.FUENTE);
		contentPanel.add(tabbedPane_1, BorderLayout.CENTER);

		okButton.addActionListener(_getActionListener(tabbedPane, contentPane));

		// Fuente
		JPanel panelFuente = new JPanel();
		tabbedPane_1.addTab("Fuente", null, panelFuente, null);
		panelFuente.setLayout(new MigLayout("", "[][grow]", "[]"));

		LabelPropio lblprpTamaoDeLa = new LabelPropio(
				"Tama\u00F1o de la fuente:");
		panelFuente.add(lblprpTamaoDeLa, "cell 0 0,alignx trailing");

		comboFuente = new ComboPropio();
		comboFuente.addItem("Grande");
		comboFuente.addItem("Mediana");
		comboFuente.addItem("Peque�a");
		panelFuente.add(comboFuente, "cell 1 0,growx");

		// Tabs
		JPanel panelTabs = new JPanel();
		tabbedPane_1.addTab("Pesta�as", null, panelTabs, null);
		panelTabs.setLayout(new MigLayout("", "[]", "[]"));

		LabelPropio lblprpelijaLasPestaufas = new LabelPropio(
				"Elija las pesta\u00F1as que quiera conservar:");
		panelTabs.add(lblprpelijaLasPestaufas, "cell 0 0");

		setVisible(true);
	}

	private ActionListener _getActionListener(final JTabbedPane tabbedPane,
			final JPanel contentPane) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Util.setMensajeInformacion("No funciona.");
				switch (tabbedPane_1.getSelectedIndex()) {
				case 0:
					_setFuente(contentPane);
					break;
				case 1:
					_setTabs(tabbedPane);
					break;
				default:
				}
				dispose();
			}
		};
	}

	/**
	 * M�todo encargado de modificar el tama�o de la fuente.
	 */
	private void _setFuente(JPanel contentPane) {
		int size = 16;

		switch (comboFuente.getSelectedIndex()) {
		case 1:
			size = 14;
			break;
		case 2:
			size = 12;
			break;
		default:
		}
		// TODO
		Constantes.setFuente(size);
		contentPane.updateUI();
		contentPane.repaint();
		// contentPane.validate();
		// contentPane.revalidate();
	}

	private void _setTabs(JTabbedPane tabbedPane) {
		// TODO ocultar pesta�as
		tabbedPane.getComponentAt(0).setVisible(false);
		// tabClientes.setVisible(false);
		// tabbedPane.updateUI();
		// tabbedPane.validate();
		tabbedPane.revalidate();

	}
}
