package org.alex.accesodatos.beans;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DialogPropio extends JDialog {
	private static final long serialVersionUID = 1L;
	protected JButton okButton, cancelButton;
	protected JPanel buttonPane, contentPanel;

	/**
	 * Create the dialog.
	 */
	public DialogPropio() {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
				okButton.setIcon(new ImageIcon(DialogPropio.class
						.getResource("/org/alex/accesodatos/iconos/ok.png")));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("");
				cancelButton
						.setIcon(new ImageIcon(
								DialogPropio.class
										.getResource("/org/alex/accesodatos/iconos/cancelar.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
