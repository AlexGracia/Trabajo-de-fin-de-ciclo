package org.alex.accesodatos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Clase encargada de mostrar una imagen mientras la aplicación se inicia.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class Start extends JFrame {
	private static final long serialVersionUID = 1L;
	private boolean parar = false;
	private byte segundos = 0;

	/**
	 * Create the panel.
	 */
	public Start() {
		setTitle("Cargando . . .");
		setSize(new Dimension(250, 250));
		setLocationRelativeTo(this);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						Start.class
								.getResource("/org/alex/accesodatos/iconos/IconoAplicacion.png")));
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel(
				new ImageIcon(
						Start.class
								.getResource("/org/alex/accesodatos/iconos/StartIcon.png")));
		getContentPane().add(label, BorderLayout.CENTER);

		setVisible(true);
		comprobarTiempoArranque();
	}

	private void comprobarTiempoArranque() {
		Thread hilo = new Thread() {

			@Override
			public void run() {
				while (!parar) {
					segundos++;
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		};
		hilo.start();
	}

	public void setParar(boolean parar) {
		this.parar = parar;
		dispose();
	}

	public boolean esLento() {
		if (segundos > 2)
			return true;

		return false;
	}

}
