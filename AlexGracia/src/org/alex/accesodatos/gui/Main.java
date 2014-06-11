package org.alex.accesodatos.gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.alex.accesodatos.beans.tablas.TablaClientes;
import org.alex.accesodatos.beans.tablas.TablaExtras;
import org.alex.accesodatos.beans.tablas.TablaPolizas;
import org.alex.accesodatos.beans.tablas.TablaVehiculos;
import org.alex.accesodatos.gui.tabs.TabClientes;
import org.alex.accesodatos.gui.tabs.TabExtras;
import org.alex.accesodatos.gui.tabs.TabPiezas;
import org.alex.accesodatos.gui.tabs.TabPolizas;
import org.alex.accesodatos.gui.tabs.TabProveedores;
import org.alex.accesodatos.gui.tabs.TabSiniestros;
import org.alex.accesodatos.gui.tabs.TabTalleres;
import org.alex.accesodatos.gui.tabs.TabVehiculos;
import org.alex.accesodatos.util.Constantes;
import org.alex.accesodatos.util.HibernateUtil;
import org.alex.libs.BarraEstado;
import org.alex.libs.Util;
import org.hibernate.HibernateException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;

/**
 * Clase principal desde la que se maneja la ventana y se cargan los demas
 * componentes graficos.
 * 
 * @author Alex Gracia
 * @version 1.0
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private boolean primeraBusqueda = true;
	private String toolbarSearch = "";
	private static Start start;

	// Variables
	private TabClientes tabClientes;
	private TablaClientes tablaClientes;
	private TabVehiculos tabVehiculos;
	private TablaVehiculos tablaVehiculos;
	private TabExtras tabExtras;
	private TablaExtras tablaExtras;
	private TabPiezas tabPiezas;
	private TabProveedores tabProveedores;
	private TabTalleres tabTalleres;
	private TabPolizas tabPolizas;
	private TablaPolizas tablaPolizas;
	private TabSiniestros tabSiniestros;
	private JMenuBar menuBar;
	private JTabbedPane tabbedPane;
	private JTextField tfBusqueda;
	private BarraEstado barraEstado;
	private JToolBar toolBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Main window = null;
				try {
					start = new Start();
					window = new Main();
					window.setVisible(true);
				} catch (Exception e) {
					window.controlErrorGrave("Error de la muerte.");
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		interfaz();
		baseDeDatos();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void interfaz() {
		// Ventana
		setTitle(Constantes.TITULO_VENTANA);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						Main.class
								.getResource("/org/alex/accesodatos/iconos/IconoAplicacion.png")));
		setSize(new Dimension(1000, 550));
		Util.estiloPorDefecto(this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		barraEstado = new BarraEstado();
		barraEstado.setFont(Constantes.FUENTE_NEGRITA);
		barraEstado.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		// Toolbar
		JButton btnGuardar = new JButton();
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
			}
		});
		btnGuardar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/ok.png")));
		toolBar.add(btnGuardar);

		JButton btnCancelar = new JButton();
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		btnCancelar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/cancelar.png")));
		toolBar.add(btnCancelar);

		JButton btnEditar = new JButton();
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/editar.png")));
		toolBar.add(btnEditar);

		JButton btnBorrar = new JButton();
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrar();
			}
		});
		btnBorrar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/borrar.png")));
		toolBar.add(btnBorrar);

		JLabel label = new JLabel("");
		label.setMaximumSize(new Dimension(600, 0));
		toolBar.add(label);

		// Busqueda
		tfBusqueda = new JTextField(
				Constantes.TEXTO_CLIENTES[Constantes.TEXTO_CLIENTES.length - 1]);
		tfBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				buscar(tfBusqueda.getText());
			}
		});
		tfBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {

				if (primeraBusqueda) {
					tfBusqueda.setText("");
					primeraBusqueda = false;
					barraEstado.vaciarTexto();
				}

			}
		});
		tfBusqueda.setFont(Constantes.FUENTE);
		tfBusqueda.setMaximumSize(new Dimension(300, 26));
		toolBar.add(tfBusqueda);
		tfBusqueda.setColumns(10);

		JButton btnCancelarbusqueda = new JButton();
		btnCancelarbusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarBusqueda();
			}
		});
		btnCancelarbusqueda.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/cancelar.png")));
		toolBar.add(btnCancelarbusqueda);

		getContentPane().add(barraEstado, BorderLayout.SOUTH);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				switch (tabbedPane.getSelectedIndex()) {
				case 0:
					toolbarSearch = Constantes.TEXTO_CLIENTES[Constantes.TEXTO_CLIENTES.length - 1];
					break;
				case 1:
					toolbarSearch = Constantes.TEXTO_VEHICULOS[Constantes.TEXTO_VEHICULOS.length - 1];
					break;
				case 2:
					toolbarSearch = Constantes.TEXTO_EXTRAS[Constantes.TEXTO_EXTRAS.length - 1];
					break;
				case 6:
					toolbarSearch = Constantes.TEXTO_POLIZAS[Constantes.TEXTO_POLIZAS.length - 1];
					break;
				// TODO Piezas, Proveedores, Talleres, Siniestros.
				default:
				}
				resetTextSearch(toolbarSearch);
				barraEstado.vaciarTexto();
			}
		});
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// Herramientas
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnHerramientas = new JMenu("Herramientas");
		menuBar.add(mnHerramientas);

		JMenuItem mntmPreferencias = new JMenuItem("Preferencias");
		mntmPreferencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new JConfiguracion();
			}
		});
		mnHerramientas.add(mntmPreferencias);

		tabClientes = new TabClientes(tabbedPane);
		tablaClientes = tabClientes.getTablaClientes();
		tabVehiculos = new TabVehiculos(tabbedPane);
		tablaVehiculos = tabVehiculos.getTablaVehiculos();
		tabExtras = new TabExtras(tabbedPane);
		tablaExtras = tabExtras.getTablaExtras();
		// TODO Piezas, Proveedores, Talleres, Siniestros.
		tabPiezas = new TabPiezas(tabbedPane);
		tabProveedores = new TabProveedores(tabbedPane);
		tabTalleres = new TabTalleres(tabbedPane);
		tabPolizas = new TabPolizas(tabbedPane);
		tablaPolizas = tabPolizas.getTablaPolizas();
		tabSiniestros = new TabSiniestros(tabbedPane);
	}

	private void baseDeDatos() {
		conectar();

		// Probar que MySQL está bien
		try {
			HibernateUtil.getQuery(
					"select u.rango from Users u where rango = 'user'")
					.uniqueResult();
		} catch (GenericJDBCException ge) {
			controlErrorConexion("User o password de MySQL no válidos.\n"
					+ "Valores por defecto:\n" + "User: root\n"
					+ "Password: \n"
					+ "\nModifique si quiere el archivo 'hibernate.cfg',\n"
					+ "apartado 'connection.password'");
		} catch (JDBCConnectionException ce) {
			controlErrorConexion("Posibles causas:\n"
					+ "1. Programa MySQL no instalado.\n"
					+ "2. Servicio MySQL inoperativo.");
		} catch (SQLGrammarException sqe) {
			controlErrorConexion("Base de datos, alex_gracia, no encontrada.");
		}

		if (!start.esLento())
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		start.setParar(true);

		JConecta conecta = new JConecta();
		while (!hacerLogin(conecta))
			;
		conecta.dispose();

		tablaClientes.listar();
		tablaVehiculos.listar();
		tablaExtras.listar();
		tablaPolizas.listar();

	}

	private boolean hacerLogin(JConecta conecta) {

		if (conecta.mostrarDialogo() == JConecta.Accion.CANCELAR)
			System.exit(0);

		String user = conecta.getUser();
		String pass = conecta.getPass();

		String query = (String) HibernateUtil.getQuery(
				"select u.rango from Users u where login = '" + user
						+ "' and password = '" + pass + "'").uniqueResult();

		try {

			byte i = 0;
			// Amoldar la interfaz segun el usuario introducido
			if (query.equals(Constantes.rangos[i++])) {
				menuBar.setVisible(false);
				setTitle("Eres usuario =)");
			} else if (query.equals(Constantes.rangos[i++]))
				setTitle("Eres admin, haz lo que quieras :)");
			else {
				tabbedPane.setVisible(false);
				toolBar.setVisible(false);
				setTitle("Eres tecnico, a currar :p");
			}
			return true;
		} catch (NullPointerException npe) {
			return false;
		}

	}

	/**
	 * Carga la configuracion y conecta con el SGBD
	 */
	private void conectar() {

		try {
			HibernateUtil.buildSessionFactory();
			HibernateUtil.openSession();

		} catch (HibernateException he) {
			controlErrorGrave("Error de la muerte, Hibernate.");
		}
	}

	// Metodos de botones
	private void aceptar() {
		barraEstado.vaciarTexto();
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			if (tabClientes.mGuardar())
				barraEstado.accionRealizada();
			break;
		case 1:
			if (tabVehiculos.mGuardar())
				barraEstado.accionRealizada();
			break;
		case 2:
			if (tabExtras.mGuardar())
				barraEstado.accionRealizada();
			break;
		case 6:
			if (tabPolizas.mGuardar())
				barraEstado.accionRealizada();
			break;
		default:
		}
		resetTextSearch(toolbarSearch);
	}

	private void cancelar() {
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			tabClientes.mCancelar();
			break;
		case 1:
			tabVehiculos.mCancelar();
			break;
		case 2:
			tabExtras.mCancelar();
			break;
		case 6:
			tabPolizas.mCancelar();
			break;
		default:
		}
		resetTextSearch(toolbarSearch);
		barraEstado.accionCancelada();
	}

	private void editar() {
		barraEstado.vaciarTexto();
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			tabClientes.mEditar();
			break;
		case 1:
			tabVehiculos.mEditar();
			break;
		case 2:
			tabExtras.mEditar();
			break;
		case 6:
			tabPolizas.mEditar();
			break;
		default:
		}
		resetTextSearch(toolbarSearch);
	}

	private void borrar() {
		barraEstado.vaciarTexto();
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			if (tabClientes.mEliminar())
				barraEstado.accionRealizada();
			break;
		case 1:
			if (tabVehiculos.mEliminar())
				barraEstado.accionRealizada();
			break;
		case 2:
			if (tabExtras.mEliminar())
				barraEstado.accionRealizada();
			break;
		case 6:
			if (tabPolizas.mEliminar())
				barraEstado.accionRealizada();
			break;
		default:
		}
		resetTextSearch(toolbarSearch);
	}

	private void buscar(String filtro) {
		barraEstado.vaciarTexto();
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			tabClientes.mBuscarCliente(filtro);
			break;
		case 1:
			tabVehiculos.mBuscarVehiculo(filtro);
			break;
		case 2:
			tabExtras.mBuscarExtra(filtro);
			break;
		case 3:
			tabPolizas.mBuscarPoliza(filtro);
			break;
		default:
		}
	}

	private void cancelarBusqueda() {
		barraEstado.vaciarTexto();
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			tablaClientes.listar();
			break;
		case 1:
			tablaVehiculos.listar();
			break;
		case 2:
			tablaExtras.listar();
			break;
		case 3:
			tablaPolizas.listar();
			break;
		default:
		}
		resetTextSearch(toolbarSearch);
		barraEstado.accionCancelada();
	}

	/**
	 * Pone el texto por defecto del tfBusqueda y activa su click.
	 */
	private void resetTextSearch(String texto) {
		tfBusqueda.setText(texto);
		primeraBusqueda = true;
	}

	private void controlErrorConexion(String mensaje) {
		start.setParar(true);
		controlErrorGrave(mensaje);
	}

	private void controlErrorGrave(String mensaje) {
		Util.setMensajeError(mensaje);
		System.exit(ERROR);
	}
}
