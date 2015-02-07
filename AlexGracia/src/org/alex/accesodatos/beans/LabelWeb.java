package org.alex.accesodatos.beans;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * JLabel personalizado para crear texto con enlace web.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class LabelWeb extends LabelPropio {
	private static final long serialVersionUID = 1L;

	public LabelWeb(String texto, final String url) {
		super("<html><u>" + texto + "</u></html>");
		setToolTipText(url);
		setForeground(new Color(0, 0, 128));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				try {
					setForeground(new Color(128, 0, 128));
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
	}

}
