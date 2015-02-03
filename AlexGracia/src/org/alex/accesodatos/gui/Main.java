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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.alex.accesodatos.beans.tablas.TablaPiezas;
import org.alex.accesodatos.beans.tablas.TablaPolizas;
import org.alex.accesodatos.beans.tablas.TablaProveedores;
import org.alex.accesodatos.beans.tablas.TablaSiniestros;
import org.alex.accesodatos.beans.tablas.TablaTalleres;
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
import org.alex.accesodatos.util.ReportUtil;
import org.alex.libs.BarraEstado;
import org.alex.libs.Util;
import org.hibernate.HibernateException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;

/**
 * Clase principal desde la que se maneja la ventana y se cargan los demas
 * componentes graficos. <h1>Tabs:</h1>
 * <ol>
 * <li><a href="tabs/TabClientes.html">CLIENTES</a>
 * <li><a href="tabs/TabVehiculos.html">VEHICULOS</a>
 * <li><a href="tabs/TabExtras.html">EXTRAS</a>
 * <li><a href="tabs/TabPiezas.html">PIEZAS</a>
 * <li><a href="tabs/TabProveedores.html">PROVEEDORES</a>
 * <li><a href="tabs/TabTalleres.html">TALLERES</a>
 * <li><a href="tabs/TabPolizas.html">POLIZAS</a>
 * <li><a href="tabs/TabSiniestros.html">SINIESTROS</a>
 * </ol>
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
	private TablaPiezas tablaPiezas;
	private TabProveedores tabProveedores;
	private TablaProveedores tablaProveedores;
	private TabTalleres tabTalleres;
	private TablaTalleres tablaTalleres;
	private TabPolizas tabPolizas;
	private TablaPolizas tablaPolizas;
	private TabSiniestros tabSiniestros;
	private TablaSiniestros tablaSiniestros;
	private JMenuBar menuBar;
	private JMenu mnHerramientas;
	private JTabbedPane tabbedPane;
	private JTextField tfBusqueda;
	private BarraEstado barraEstado;
	private JToolBar toolBar;
	private JButton btnEditar, btnBorrar, btnCancelarbusqueda, btnPdf;

	/**
	 * Launch the application.
	 * 
	 * @param args
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
					e.printStackTrace();
					controlErrorGrave("Error de la muerte.");
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		interfaz();
		finalizandoCarga();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void interfaz() {
		// Ventana
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (new JConfirmacion("Salir").isAceptar())
					System.exit(EXIT_ON_CLOSE);
			}
		});
		setTitle(Constantes.TITULO_VENTANA);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						Main.class
								.getResource("/org/alex/accesodatos/iconos/IconoAplicacion.png")));
		setSize(new Dimension(1020, 555));
		Util.estiloPorDefecto(this);
		setLocation((int) getLocation().getX(), 0);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		conectar();

		barraEstado = new BarraEstado();
		barraEstado.setFont(Constantes.FUENTE_NEGRITA);
		barraEstado.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		// Toolbar
		JButton btnGuardar = new JButton();
		btnGuardar.setToolTipText("Aceptar");
		btnGuardar.addActionListener(ActionListener(0));
		btnGuardar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/ok.png")));
		toolBar.add(btnGuardar);

		JButton btnCancelar = new JButton();
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.addActionListener(ActionListener(1));
		btnCancelar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/cancelar.png")));
		toolBar.add(btnCancelar);

		btnEditar = new JButton();
		btnEditar.setToolTipText("Editar");
		btnEditar.addActionListener(ActionListener(2));
		btnEditar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/editar.png")));
		toolBar.add(btnEditar);

		btnBorrar = new JButton();
		btnBorrar.setToolTipText("Borrar");
		btnBorrar.addActionListener(ActionListener(3));
		btnBorrar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/borrar.png")));
		toolBar.add(btnBorrar);

		btnPdf = new JButton();
		btnPdf.setToolTipText("Exportar a PDF");
		btnPdf.addActionListener(ActionListener(4));
		btnPdf.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/pdf.png")));
		toolBar.add(btnPdf);

		JLabel label = new JLabel();
		label.setMaximumSize(new Dimension(300, 0));
		toolBar.add(label);

		// Busqueda
		tfBusqueda = new JTextField(
				Constantes.TEXTO_CLIENTES[Constantes.TEXTO_CLIENTES.length - 1]);
		tfBusqueda.setToolTipText("Buscar");
		tfBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
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
		tfBusqueda.setMaximumSize(new Dimension(400, 26));
		toolBar.add(tfBusqueda);
		tfBusqueda.setColumns(10);

		btnCancelarbusqueda = new JButton();
		btnCancelarbusqueda.setToolTipText("Cancelar b\u00FAsqueda");
		btnCancelarbusqueda.addActionListener(ActionListener(5));
		btnCancelarbusqueda.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/cancelar.png")));
		toolBar.add(btnCancelarbusqueda);

		getContentPane().add(barraEstado, BorderLayout.SOUTH);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(Constantes.FUENTE);
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
				case 3:
					toolbarSearch = Constantes.TEXTO_PIEZAS[Constantes.TEXTO_PIEZAS.length - 1];
					break;
				case 4:
					toolbarSearch = Constantes.TEXTO_PROVEEDORES[Constantes.TEXTO_PROVEEDORES.length - 1];
					break;
				case 5:
					toolbarSearch = Constantes.TEXTO_TALLERES[Constantes.TEXTO_TALLERES.length - 1];
					break;
				case 6:
					toolbarSearch = Constantes.TEXTO_POLIZAS[Constantes.TEXTO_POLIZAS.length - 1];
					break;
				case 7:
					toolbarSearch = Constantes.TEXTO_SINIESTROS[Constantes.TEXTO_SINIESTROS.length - 1];
					break;
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

		mnHerramientas = new JMenu("Herramientas");
		menuBar.add(mnHerramientas);

		JMenuItem mntmPreferencias = new JMenuItem("Preferencias");
		mntmPreferencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new JConfiguracion();
			}
		});
		mnHerramientas.add(mntmPreferencias);

		JMenu menu = new JMenu("?");
		menu.setToolTipText("Ayuda");
		menuBar.add(menu);
		// TODO ayuda
		JMenuItem mntmAcercaDeAlexgracia = new JMenuItem("Acerca de AlexGracia");
		mntmAcercaDeAlexgracia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new JAcercaDe();
			}
		});
		menu.add(mntmAcercaDeAlexgracia);

		tabClientes = new TabClientes(tabbedPane);
		tablaClientes = tabClientes.getTablaClientes();
		tabVehiculos = new TabVehiculos(tabbedPane);
		tablaVehiculos = tabVehiculos.getTablaVehiculos();
		tabExtras = new TabExtras(tabbedPane);
		tablaExtras = tabExtras.getTablaExtras();
		tabPiezas = new TabPiezas(tabbedPane);
		tablaPiezas = tabPiezas.getTablaPiezas();
		tabProveedores = new TabProveedores(tabbedPane);
		tablaProveedores = tabProveedores.getTablaProveedores();
		tabTalleres = new TabTalleres(tabbedPane);
		tablaTalleres = tabTalleres.getTablaTalleres();
		tabPolizas = new TabPolizas(tabbedPane);
		tablaPolizas = tabPolizas.getTablaPolizas();
		tabSiniestros = new TabSiniestros(tabbedPane);
		tablaSiniestros = tabSiniestros.getTablaSiniestros();

	}

	private void finalizandoCarga() {

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
		tablaPiezas.listar();
		tablaProveedores.listar();
		tablaTalleres.listar();
		tablaPolizas.listar();
		tablaSiniestros.listar();

	}

	private boolean hacerLogin(JConecta conecta) {

		if (conecta.mostrarDialogo() != JConecta.Accion.ACEPTAR)
			System.exit(0);

		String query = (String) HibernateUtil.getQuery(
				"select u.rango from Users u where login = '"
						+ conecta.getUser() + "' and password = '"
						+ conecta.getPass() + "'").uniqueResult();

		if (query == null)
			return false;

		byte i = 0;
		// Amoldar la interfaz segun el usuario introducido
		if (query.equals(Constantes.rangos[i++])) {
			mnHerramientas.setEnabled(false);
			setTitle("Eres usuario =)");
		} else if (query.equals(Constantes.rangos[i]))
			setTitle("Eres admin, haz lo que quieras :)");
		else {
			tabbedPane.setVisible(false);
			toolBar.setVisible(false);
			setTitle("Eres tecnico, a currar :p");
		}
		return true;

	}

	/**
	 * Carga la configuracion y conecta con el SGBD
	 */
	private void conectar() {

		try {
			HibernateUtil.buildSessionFactory();
			HibernateUtil.openSession();
			// Probar que MySQL está bien
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

		} catch (HibernateException he) {
			controlErrorGrave("Error de la muerte, Hibernate.");
		}
	}

	// Metodos de botones
	private void aceptar() {
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			if (tabClientes.mGuardar()) {
				tabPolizas.resetComboClientes();
				tabSiniestros.resetComboClientes();
				barraEstado.accionRealizada();
				setEnable(true);
			}
			break;
		case 1:
			if (tabVehiculos.mGuardar()) {
				tabPolizas.resetComboVehiculos();
				barraEstado.accionRealizada();
				setEnable(true);
			}
			break;
		case 2:
			if (tabExtras.mGuardar()) {
				barraEstado.accionRealizada();
				setEnable(true);
			}
			break;
		case 3:
			if (tabPiezas.mGuardar()) {
				barraEstado.accionRealizada();
				setEnable(true);
			}
			break;
		case 4:
			if (tabProveedores.mGuardar()) {
				barraEstado.accionRealizada();
				setEnable(true);
			}
			break;
		case 5:
			if (tabTalleres.mGuardar()) {
				tabSiniestros.resetComboTalleres();
				barraEstado.accionRealizada();
				setEnable(true);
			}
			break;
		case 6:
			if (tabPolizas.mGuardar()) {
				barraEstado.accionRealizada();
				setEnable(true);
			}
			break;
		case 7:
			if (tabSiniestros.mGuardar()) {
				barraEstado.accionRealizada();
				setEnable(true);
			}
			break;
		default:
		}
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
		case 3:
			tabPiezas.mCancelar();
			break;
		case 4:
			tabProveedores.mCancelar();
			break;
		case 5:
			tabTalleres.mCancelar();
			break;
		case 6:
			tabPolizas.mCancelar();
			break;
		case 7:
			tabSiniestros.mCancelar();
			break;
		default:
		}
		barraEstado.accionCancelada();
		setEnable(true);
	}

	private void editar() {
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			if (tabClientes.mEditar())
				setEnable(false);
			break;
		case 1:
			if (tabVehiculos.mEditar())
				setEnable(false);
			break;
		case 2:
			if (tabExtras.mEditar())
				setEnable(false);
			break;
		case 3:
			if (tabPiezas.mEditar())
				setEnable(false);
			break;
		case 4:
			if (tabProveedores.mEditar())
				setEnable(false);
			break;
		case 5:
			if (tabTalleres.mEditar())
				setEnable(false);
			break;
		case 6:
			if (tabPolizas.mEditar())
				setEnable(false);
			break;
		case 7:
			if (tabSiniestros.mEditar())
				setEnable(false);
			break;
		default:
		}
	}

	private void borrar() {
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			if (tabClientes.mEliminar()) {
				tabPolizas.resetComboClientes();
				tabSiniestros.resetComboClientes();
				barraEstado.accionRealizada();
			}
			break;
		case 1:
			if (tabVehiculos.mEliminar()) {
				tabPolizas.resetComboVehiculos();
				barraEstado.accionRealizada();
			}
			break;
		case 2:
			if (tabExtras.mEliminar())
				barraEstado.accionRealizada();
			break;
		case 3:
			if (tabPiezas.mEliminar())
				barraEstado.accionRealizada();
			break;
		case 4:
			if (tabProveedores.mEliminar())
				barraEstado.accionRealizada();
			break;
		case 5:
			if (tabTalleres.mEliminar()) {
				tabSiniestros.resetComboTalleres();
				barraEstado.accionRealizada();
			}
			break;
		case 6:
			if (tabPolizas.mEliminar())
				barraEstado.accionRealizada();
			break;
		case 7:
			if (tabSiniestros.mEliminar())
				barraEstado.accionRealizada();
			break;
		default:
		}
	}

	// TODO
	private void exportar() {

		JOpcionInforme dialogoOpcion = new JOpcionInforme();

		if (!dialogoOpcion.isAceptar()) {
			barraEstado.accionCancelada();
			return;
		}

		int opcion = dialogoOpcion.comboPropio.getSelectedIndex();

		if (opcion == 0) {
			Util.setMensajeInformacion("Seleccione un tipo.");
			exportar();
			return;
		}

		// Volumen de negocio
		if (opcion == 3) {
			new ReportUtil(this, "report_negocio3.jasper", null).ExportToPDF();
			barraEstado.accionRealizada();
			return;
		}

		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			tabClientes.mExportar(this, opcion);
			break;
		case 1:
			tabVehiculos.mExportar(this, opcion);
			break;
		case 2:
			tabExtras.mExportar(this, opcion);
			break;
		case 3:
			tabPiezas.mExportar(this, opcion);
			break;
		case 4:
			tabProveedores.mExportar(this, opcion);
			break;
		case 5:
			tabTalleres.mExportar(this, opcion);
			break;
		case 6:
			tabPolizas.mExportar(this, opcion);
			break;
		case 7:
			tabSiniestros.mExportar(this, opcion);
			break;
		default:
		}

		barraEstado.accionRealizada();

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
			tabPiezas.mBuscarPieza(filtro);
			break;
		case 4:
			tabProveedores.mBuscarProveedor(filtro);
			break;
		case 5:
			tabTalleres.mBuscarProveedor(filtro);
			break;
		case 6:
			tabPolizas.mBuscarPoliza(filtro);
			break;
		case 7:
			tabSiniestros.mBuscarSiniestro(filtro);
			break;
		default:
		}
	}

	private void cancelarBusqueda() {
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
			tablaPiezas.listar();
			break;
		case 4:
			tablaProveedores.listar();
			break;
		case 5:
			tablaTalleres.listar();
			break;
		case 6:
			tablaPolizas.listar();
			break;
		case 7:
			tablaSiniestros.listar();
			break;
		default:
		}
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

	private static void controlErrorGrave(String mensaje) {
		Util.setMensajeError(mensaje);
		System.exit(ERROR);
	}

	private void setEnable(boolean estado) {
		btnEditar.setEnabled(estado);
		btnBorrar.setEnabled(estado);
		btnPdf.setEnabled(estado);
		tfBusqueda.setEditable(estado);
		btnCancelarbusqueda.setEnabled(estado);
		tabbedPane.setEnabled(estado);
		mnHerramientas.setEnabled(estado);

		// Tablas
		tablaClientes.setEnabled(estado);
		tablaVehiculos.setEnabled(estado);
		tablaExtras.setEnabled(estado);
		tablaPiezas.setEnabled(estado);
		tablaProveedores.setEnabled(estado);
		tablaTalleres.setEnabled(estado);
		tablaPolizas.setEnabled(estado);
		tablaSiniestros.setEnabled(estado);

	}

	private ActionListener ActionListener(final int opcion) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				barraEstado.vaciarTexto();
				resetTextSearch(toolbarSearch);
				switch (opcion) {
				case 0:
					aceptar();
					break;
				case 1:
					cancelar();
					break;
				case 2:
					editar();
					break;
				case 3:
					borrar();
					break;
				case 4:
					exportar();
					break;
				case 5:
					cancelarBusqueda();
					break;
				default:
				}
			}
		};
	}
}
