package org.alex.accesodatos.beans;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * JDialog personalizado.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class DialogPropio extends JDialog {
	private static final long serialVersionUID = 1L;
	protected JButton okButton, cancelButton;
	protected JPanel buttonPane, contentPanel;
	private boolean aceptar;

	/**
	 * Create the dialog.
	 */
	public DialogPropio() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						DialogPropio.class
								.getResource("/org/alex/accesodatos/iconos/IconoAplicacion.png")));
		getContentPane().setLayout(new BorderLayout());

		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("");
				okButton.addActionListener(aceptar(true));
				okButton.setIcon(new ImageIcon(DialogPropio.class
						.getResource("/org/alex/accesodatos/iconos/ok.png")));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("");
				cancelButton.addActionListener(aceptar(false));
				cancelButton
						.setIcon(new ImageIcon(
								DialogPropio.class
										.getResource("/org/alex/accesodatos/iconos/cancelar.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

	public boolean isAceptar() {
		return aceptar;
	}

	private ActionListener aceptar(final boolean valor) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				aceptar = valor;

				if (!aceptar)
					dispose();
			}
		};
	}

	/**
	 * Cierra el JDialog al pulsar el botón okButton.
	 * 
	 * @return
	 */
	public void okButtonDispose() {
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
	}

}
