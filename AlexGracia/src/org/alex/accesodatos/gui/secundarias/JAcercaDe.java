package org.alex.accesodatos.gui.secundarias;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.beans.LabelPropio;
import org.alex.accesodatos.beans.LabelWeb;

/**
 * JDialog encargado de mostrar información referente al programa (autor,
 * versión, ...).
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class JAcercaDe extends DialogPropio {
	private static final long serialVersionUID = 1L;
	private String urlHome = "http://alexgracia.github.io/Trabajo-de-fin-de-ciclo",
			urlVersion = "https://github.com/AlexGracia/Trabajo-de-fin-de-ciclo/releases/latest";

	public JAcercaDe() {
		okButtonDispose();
		setTitle("Acerca de AlexGracia");
		setSize(new Dimension(500, 350));
		setLocationRelativeTo(this);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// Logo
		JLabel lblImage = new JLabel();
		lblImage.setVerticalAlignment(SwingConstants.TOP);
		lblImage.setIcon(new ImageIcon(JAcercaDe.class
				.getResource("/org/alex/accesodatos/iconos/AlexGracia.png")));
		contentPanel.add(lblImage, BorderLayout.WEST);

		// Info
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[][]", "[][][][]"));

		LabelPropio lblprpAutor = new LabelPropio("Autor:");
		panel.add(lblprpAutor, "cell 0 0");

		LabelPropio lblprpAlexGracia = new LabelPropio("Alex Gracia");
		panel.add(lblprpAlexGracia, "cell 1 0");

		LabelPropio lblprpVersin = new LabelPropio("Versi\u00F3n:");
		panel.add(lblprpVersin, "cell 0 1");

		LabelPropio lblVersion = new LabelPropio("1.0.1");
		panel.add(lblVersion, "cell 1 1");

		// Enlaces web
		LabelPropio lblprpPginaWeb = new LabelPropio("P\u00E1gina web:");
		panel.add(lblprpPginaWeb, "cell 0 2");

		LabelWeb lblUrlHome = new LabelWeb("visitar", urlHome);
		panel.add(lblUrlHome, "cell 1 2");

		LabelPropio lblprpltimaVersin = new LabelPropio(
				"\u00DAltima versi\u00F3n:");
		panel.add(lblprpltimaVersin, "cell 0 3");

		LabelWeb lblUrlVersion = new LabelWeb("visitar", urlVersion);
		panel.add(lblUrlVersion, "cell 1 3");

		// Licencia
		JPanel paneLicencia = new JPanel();
		contentPanel.add(paneLicencia, BorderLayout.SOUTH);

		JTextArea textArea = new JTextArea(
				"This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version."
						+ "\n\nThis program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details."
						+ "\n\nYou should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.");
		textArea.setWrapStyleWord(true);
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textArea.setOpaque(false);
		textArea.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(new TitledBorder(null,
				"GNU GENERAL PUBLIC LICENSE", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		scrollPane.setPreferredSize(new Dimension(470, 100));
		paneLicencia.add(scrollPane);

		cancelButton.setVisible(false);
		setVisible(true);
	}

}
