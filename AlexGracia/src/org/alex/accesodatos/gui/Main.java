package org.alex.accesodatos.gui;

import java.awt.BorderLayout;
import java.awt.Component;
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
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
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
import org.alex.accesodatos.gui.secundarias.JAcercaDe;
import org.alex.accesodatos.gui.secundarias.JConfirmacion;
import org.alex.accesodatos.gui.secundarias.JOpcionInforme;
import org.alex.accesodatos.gui.secundarias.JConfiguracion;
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
	private JPanel contentPane;
	private boolean primeraBusqueda = true, noEsUsuario = true,
			intentarStartMySQL = true, intentarInstalarBD = true;
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
	private JButton btnEditar, btnBorrar, btnCancelarbusqueda, btnInforme;
	private JSeparator separator;

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
		if ("Windows".equals(System.getProperty("os.name").substring(0, 7)))
			Constantes.esWindows = true;
		interfaz();
		Constantes.borderDefault = tfBusqueda.getBorder();
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
				else
					barraEstado.accionCancelada();
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
		// En caso de que la pantalla sea muy peque�a
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension screenSize = tool.getScreenSize();
		int altura = screenSize.height;
		if (altura <= 600)
			setLocation(getLocation().x, 0);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		conectar();

		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		// Toolbar
		JButton btnGuardar = new JButton();
		btnGuardar.setToolTipText("Guardar");
		btnGuardar.addActionListener(actionListener(0));
		btnGuardar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/guardar.png")));
		toolBar.add(btnGuardar);

		JButton btnCancelar = new JButton();
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.addActionListener(actionListener(1));
		btnCancelar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/cancelar.png")));
		toolBar.add(btnCancelar);

		btnEditar = new JButton();
		btnEditar.setToolTipText("Editar");
		btnEditar.addActionListener(actionListener(2));
		btnEditar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/editar.png")));
		toolBar.add(btnEditar);

		btnBorrar = new JButton();
		btnBorrar.setToolTipText("Borrar");
		btnBorrar.addActionListener(actionListener(3));
		btnBorrar.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/borrar.png")));
		toolBar.add(btnBorrar);

		btnInforme = new JButton();
		btnInforme.setToolTipText("Informe");
		btnInforme.addActionListener(actionListener(4));
		btnInforme.setIcon(new ImageIcon(Main.class
				.getResource("/org/alex/accesodatos/iconos/informe.png")));
		toolBar.add(btnInforme);

		JLabel lblBlank = new JLabel();
		lblBlank.setMaximumSize(new Dimension(300, 0));
		toolBar.add(lblBlank);

		// Barra de estado
		barraEstado = new BarraEstado();
		barraEstado.setFont(Constantes.FUENTE_NEGRITA);
		barraEstado.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPane.add(barraEstado, BorderLayout.SOUTH);

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
		btnCancelarbusqueda.addActionListener(actionListener(5));
		btnCancelarbusqueda
				.setIcon(new ImageIcon(
						Main.class
								.getResource("/org/alex/accesodatos/iconos/cancelar_busqueda.png")));
		toolBar.add(btnCancelarbusqueda);

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
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		// Tabs y tablas
		tabClientes = new TabClientes();
		tabbedPane.addTab(Constantes.TEXTO_CLIENTES[0], tabClientes);
		tablaClientes = tabClientes.getTablaClientes();

		tabVehiculos = new TabVehiculos();
		tabbedPane.addTab(Constantes.TEXTO_VEHICULOS[0], tabVehiculos);
		tablaVehiculos = tabVehiculos.getTablaVehiculos();

		tabExtras = new TabExtras();
		tabbedPane.addTab(Constantes.TEXTO_EXTRAS[0], tabExtras);
		tablaExtras = tabExtras.getTablaExtras();

		tabPiezas = new TabPiezas();
		tabbedPane.addTab(Constantes.TEXTO_PIEZAS[0], tabPiezas);
		tablaPiezas = tabPiezas.getTablaPiezas();

		tabProveedores = new TabProveedores();
		tabbedPane.addTab(Constantes.TEXTO_PROVEEDORES[0], tabProveedores);
		tablaProveedores = tabProveedores.getTablaProveedores();

		tabTalleres = new TabTalleres();
		tabbedPane.addTab(Constantes.TEXTO_TALLERES[0], tabTalleres);
		tablaTalleres = tabTalleres.getTablaTalleres();

		tabPolizas = new TabPolizas();
		tabbedPane.addTab(Constantes.TEXTO_POLIZAS[0], tabPolizas);
		tablaPolizas = tabPolizas.getTablaPolizas();

		tabSiniestros = new TabSiniestros();
		tabbedPane.addTab(Constantes.TEXTO_SINIESTROS[0], tabSiniestros);
		tablaSiniestros = tabSiniestros.getTablaSiniestros();

		// Herramientas
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnHerramientas = new JMenu("Herramientas");
		menuBar.add(mnHerramientas);

		JMenuItem mntmConfiguracion = new JMenuItem("Configuraci�n");
		mntmConfiguracion.addActionListener(actionPreferencias());
		mnHerramientas.add(mntmConfiguracion);

		if (Constantes.esWindows) {
			separator = new JSeparator();
			mnHerramientas.add(separator);

			// Base de datos
			JMenuItem mnExportarBD = new JMenuItem("Exportar BD...");
			mnExportarBD.addActionListener(_exportarBD());
			mnHerramientas.add(mnExportarBD);

			// Parar el servicio de MySQL
			JMenuItem mntmPararMysql = new JMenuItem("Parar MySQL...");
			mntmPararMysql.addActionListener(_pararMySQL());
			mnHerramientas.add(mntmPararMysql);
		}

		// Ayuda
		JMenu mnAyuda = new JMenu("?");
		mnAyuda.setToolTipText("Ayuda");
		menuBar.add(mnAyuda);

		JMenuItem mntmAcercaDeAlexgracia = new JMenuItem("Acerca de AlexGracia");
		mntmAcercaDeAlexgracia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new JAcercaDe();
			}
		});
		mnAyuda.add(mntmAcercaDeAlexgracia);

	}

	private void finalizandoCarga() {

		if (!start.esLento())
			Util._esperar(2000);

		start.setParar(true);

		_amoldarPrograma();

		tablaClientes.listar();
		tablaVehiculos.listar();
		tablaExtras.listar();
		tablaPiezas.listar();
		tablaProveedores.listar();
		tablaTalleres.listar();
		tablaPolizas.listar();
		tablaSiniestros.listar();

	}

	/**
	 * Metodo que amolda el programa segun el usuario introducido.
	 */
	private void _amoldarPrograma() {
		JLogin login = new JLogin();

		if (!login.isAceptar())
			System.exit(EXIT_ON_CLOSE);

		if (login.isUser()) {
			mnHerramientas.setEnabled(false);
			noEsUsuario = false;
		} else if (login.isTecnic()) {
			_deshabilitarComponentes();

			for (Component c : tabClientes.getComponents())
				c.setEnabled(false);
			tablaClientes.setVisible(false);
		}

	}

	/**
	 * Carga la configuracion y conecta con el SGBD
	 */
	private void conectar() {

		try {
			HibernateUtil.buildSessionFactory();
			HibernateUtil.openSession();
			// Probar que MySQL est� bien
			HibernateUtil.getQuery(
					"select u.rango from Users u where rango = 'user'")
					.uniqueResult();
		} catch (GenericJDBCException ge) {
			controlErrorConexion("User o password de MySQL no v�lidos.\n"
					+ "Valores por defecto:\n" + "User: root\n"
					+ "Password: \n"
					+ "\nModifique, si quiere, el archivo 'hibernate.cfg',\n"
					+ "apartado 'connection.password'");
		} catch (JDBCConnectionException ce) {

			if (Constantes.esWindows) {
				if (intentarStartMySQL) {
					Util.openFile("C:/xampp/mysql/bin/mysqld.exe");
					intentarStartMySQL = false;
					Util._esperar(4000);
					conectar();
				} else
					controlErrorConexion("Posibles causas:\n"
							+ "1. Programa MySQL no instalado.\n"
							+ "2. Servicio MySQL inoperativo.");

			} else
				controlErrorConexion("Posibles causas:\n"
						+ "1. Programa MySQL no instalado.\n"
						+ "2. Servicio MySQL inoperativo.");

		} catch (SQLGrammarException sqe) {

			if (Constantes.esWindows) {
				if (intentarInstalarBD) {
					Util.openFile("database/install_database.bat");
					intentarInstalarBD = false;
					Util._esperar(6000);
					conectar();
				} else
					controlErrorConexion("Base de datos, alex_gracia, no encontrada.");

			} else
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

	private void _informe() {

		JOpcionInforme dialogoOpcion = new JOpcionInforme();

		if (!dialogoOpcion.isAceptar()) {
			barraEstado.accionCancelada();
			return;
		}

		int opcion = dialogoOpcion.getOpcionSeleccionada();

		// Volumen de negocio
		if (opcion == 3) {
			new ReportUtil(this, "report/report_negocio3.jasper", null);
			barraEstado.accionRealizada();
			return;
		}

		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			tabClientes.mInforme(this, opcion);
			break;
		case 1:
			tabVehiculos.mInforme(this, opcion);
			break;
		case 2:
			tabExtras.mInforme(this, opcion);
			break;
		case 3:
			tabPiezas.mInforme(this, opcion);
			break;
		case 4:
			tabProveedores.mInforme(this, opcion);
			break;
		case 5:
			tabTalleres.mInforme(this, opcion);
			break;
		case 6:
			tabPolizas.mInforme(this, opcion);
			break;
		case 7:
			tabSiniestros.mInforme(this, opcion);
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
		btnInforme.setEnabled(estado);
		tfBusqueda.setEditable(estado);
		btnCancelarbusqueda.setEnabled(estado);
		tabbedPane.setEnabled(estado);
		if (noEsUsuario)
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

	private ActionListener actionListener(final int opcion) {
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
					_informe();
					break;
				case 5:
					cancelarBusqueda();
					break;
				default:
				}
			}
		};
	}

	private ActionListener actionPreferencias() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!new JConfiguracion().isAceptar())
					barraEstado.accionCancelada();
				else
					barraEstado.accionRealizada();
			}
		};
	}

	/**
	 * Exporta la base de datos.
	 * 
	 * @return ActionListener
	 */
	private ActionListener _exportarBD() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!Util.ficheroReal("c:/xampp/mysql/bin/mysqldump.exe"))
					return;

				if (Util.openFile("database/export_database.bat")) {
					File backupBD = new File("database/SQL/full_database.sql");
					String rutaBackup = backupBD.getAbsolutePath();

					if (JOptionPane
							.showConfirmDialog(
									Main.this,
									"La base de datos ha sido exportada, o se est� exportando:\n"
											+ rutaBackup
											+ "\n�Deseas abrirla?"
											+ "\n\nEn caso de que a�n se est� exportando, espere.",
									"BD exportada", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
						Util.openFile(rutaBackup);

					barraEstado.accionRealizada();
				}
			}
		};
	}

	/**
	 * Para el servicio de MySQL.
	 * 
	 * @return ActionListener
	 */
	private ActionListener _pararMySQL() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!new JConfirmacion("Parar MySQL").isAceptar())
					barraEstado.accionCancelada();

				else {
					if (Util.openFile("C:/xampp/mysql_stop.bat")) {
						_deshabilitarComponentes();
						mnHerramientas.setEnabled(false);
						barraEstado.accionRealizada();
					}
				}

			}
		};
	}

	/**
	 * Deshabilita toolBar y tabbedPane.
	 */
	private void _deshabilitarComponentes() {
		for (Component c : toolBar.getComponents())
			c.setEnabled(false);

		tabbedPane.setEnabled(false);
	}

}
