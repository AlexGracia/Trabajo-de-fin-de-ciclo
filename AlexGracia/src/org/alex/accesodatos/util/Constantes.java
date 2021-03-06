package org.alex.accesodatos.util;

import java.awt.Font;

import javax.swing.border.Border;

/**
 * Clase con variables globales.
 * 
 * @author Alex Gracia
 * @version 1.0
 */
public class Constantes {
	public final static String[] rangos = { "user", "tecnic", "admin" };

	// Componentes graficos
	public static Border borderDefault;
	
	public static boolean esWindows = false;

	public final static Font FUENTE = new Font("Tahoma", Font.PLAIN, 16);

	public final static Font FUENTE_NEGRITA = new Font("Tahoma", Font.BOLD, 16);
	public final static String TITULO_VENTANA = "AlexGracia - Aseguradora de veh�culos";

	public final static String[] TEXTO_CLIENTES = { "Clientes", "Nombre *",
			"Apellidos", "DNI *", "Tel�fono", "F. Nacimiento", "F. Carnet",
			"Direcci�n", "Nombre, DNI, Id (p�liza), Id (siniestro)" };
	public final static String[] TEXTO_VEHICULOS = { "Veh�culos",
			"Matr�cula *", "Marca", "Modelo", "Potencia", "A�o-fabricaci�n",
			"Color", "Puertas", "Kil�metros *",
			"Matr�cula, Kil�metros, Id (p�liza)" };
	public final static String[] TEXTO_EXTRAS = { "Extras", "A�o-fabricaci�n",
			"Marca", "Nombre *", "Modelo *", "Descripci�n", "Lugar de origen",
			"Dimensiones *", "Fabricante", "Nombre, Modelo" };
	public final static String[] TEXTO_PIEZAS = { "Piezas", "Nombre *",
			"Descripci�n", "Cantidad", "Precio", "Origen *", "F. Solicitud",
			"Marca", "Nombre, Origen" };
	public final static String[] TEXTO_PROVEEDORES = { "Proveedores",
			"Nombre *", "Tel�fono *", "Correo", "F. Nacimiento", "Direcci�n",
			"Pago", "Nombre-Empresa *", "DNI *",
			"Nombre, Tel�fono, Nombre-Empresa, DNI" };
	public final static String[] TEXTO_TALLERES = { "Talleres", "Nombre *",
			"Direcci�n *", "Tel�fono", "F. Inicio", "Nombre-Jefe",
			"CIF-Empresa *", "Trabajadores", "Reparaciones",
			"Nombre, Direcci�n, CIF-Empresa, Id (siniestro)" };
	public final static String[] TEXTO_POLIZAS = { "P�lizas", "Tipo",
			"Importe", "Estado ", "F. Inicio", "Conductores", "Antig�edad",
			"F. Fin", "Id (cliente) *", "Id (veh�culo) *",
			"Id (cliente), Id (veh�culo)" };
	public final static String[] TEXTO_SINIESTROS = { "Siniestros",
			"Datos-P�liza", "Importe-Reparaci�n", "Datos-Cliente",
			"F. Reparaci�n", "Datos-Taller", "F. Siniestro",
			"Vehiculos-Implicados", "Id (cliente) *", "Id (taller) *",
			"Clientes heridos", "Id (cliente), Id (taller)" };

	public final static String[] COLUMNAS_CLIENTE = { "Id", "C�digo", "Nombre",
			"Apellidos", "DNI", "Tel�fono", "Nacimiento", "Carnet", "Direcci�n" };
	public final static String[] COLUMNAS_VEHICULO = { "Id", "Chasis",
			"Matr�cula", "Marca", "Modelo", "Potencia", "Fabricaci�n", "Color",
			"Puertas", "Kil�metros" };
	public final static String[] COLUMNAS_EXTRA = { "Id", "Fabricaci�n",
			"Marca", "Nombre", "Modelo", "Descripci�n", "Origen",
			"Dimensiones", "Fabricante" };
	public final static String[] COLUMNAS_PIEZA = { "Id", "C�digo", "Nombre",
			"Descripci�n", "Cantidad", "Precio", "Origen", "Solicitud", "Marca" };
	public final static String[] COLUMNAS_PROVEEDOR = { "Id", "Nombre",
			"Tel�fono", "Correo", "Nacimiento", "Direcci�n", "Pago", "Empresa",
			"DNI" };
	public final static String[] COLUMNAS_TALLER = { "Id", "Nombre",
			"Direcci�n", "Tel�fono", "Inicio", "Jefe", "CIF", "Trabajadores",
			"Reparaciones" };
	public final static String[] COLUMNAS_POLIZA = { "Id", "N�mero", "Tipo",
			"Importe", "Estado", "Inicio", "Conductores",
			"Antiguedad de conducci�n", "Fin", "Id (cliente)", "Id (vehiculo)" };
	public final static String[] COLUMNAS_SINIESTRO = { "Id", "Datos poliza",
			"Reparaci�n", "Datos cliente", "Reparaci�n", "Datos taller",
			"Siniestro", "Veh�culos implicados", "Id (cliente)", "Id (taller)",
			"Clientes heridos" };
}
