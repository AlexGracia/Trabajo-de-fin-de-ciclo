package org.alex.accesodatos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
	private final String url = "https://github.com/AlexGracia/Trabajo-de-fin-de-ciclo";

	public JAcercaDe() {
		setTitle("Acerca de AlexGracia");
		setSize(new Dimension(500, 350));
		setLocationRelativeTo(null);
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
		panel.setLayout(new MigLayout("", "[][]", "[][][]"));

		LabelPropio lblprpAutor = new LabelPropio("Autor:");
		panel.add(lblprpAutor, "cell 0 0");

		LabelPropio lblprpAlexGracia = new LabelPropio("Alex Gracia");
		panel.add(lblprpAlexGracia, "cell 1 0");

		LabelPropio lblprpVersin = new LabelPropio("Versi\u00F3n:");
		panel.add(lblprpVersin, "cell 0 1");

		LabelPropio labelPropio = new LabelPropio("1.0.1");
		panel.add(labelPropio, "cell 1 1");

		LabelPropio lblprpPginaWeb = new LabelPropio("P\u00E1gina web:");
		panel.add(lblprpPginaWeb, "cell 0 2");

		final LabelPropio lblUrl = new LabelPropio(
				"<html><u>visitar</u></html>");

		lblUrl.setToolTipText(url);
		lblUrl.setForeground(new Color(0, 0, 128));
		lblUrl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					lblUrl.setForeground(new Color(128, 0, 128));
					Desktop.getDesktop().browse(new URI(url));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		panel.add(lblUrl, "cell 1 2");

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
		scrollPane.setPreferredSize(new Dimension(450, 100));
		paneLicencia.add(scrollPane);

		cancelButton.setVisible(false);
		setVisible(true);
	}

}
