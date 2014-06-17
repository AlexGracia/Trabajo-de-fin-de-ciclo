package org.alex.accesodatos.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

import org.alex.accesodatos.beans.DialogPropio;
import org.alex.accesodatos.util.Constantes;

public class JConfirmacion extends DialogPropio {
	private static final long serialVersionUID = 1L;
	private boolean aceptar;

	/**
	 * Create the dialog.
	 */
	public JConfirmacion() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				aceptar = false;
			}
		});

		setTitle("Salir");
		setSize(new Dimension(180, 120));
		setLocationRelativeTo(this);

		{
			JLabel lblestsSeguro = new JLabel("\u00BFEst\u00E1s seguro?");
			lblestsSeguro.setFont(Constantes.FUENTE);
			contentPanel.add(lblestsSeguro);
		}
		{

			okButton.addActionListener(aceptar(true));

			cancelButton.addActionListener(aceptar(false));
		}

		setVisible(true);
	}

	public boolean isAceptar() {
		return aceptar;
	}

	private ActionListener aceptar(final boolean valor) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				aceptar = valor;
				dispose();
			}
		};
	}

}
