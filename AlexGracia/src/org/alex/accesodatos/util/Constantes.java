package org.alex.accesodatos.util;

import java.awt.Font;

/**
 * Clase con variables globales.
 * 
 * @author Alex Gracia
 * @Por_hacer TODO Piezas, Proveedores, Talleres, Siniestros.
 * 
 */
public class Constantes {
	public final static String[] rangos = { "user", "admin", "tecnic" };

	// Componentes graficos
	public final static Font FUENTE = new Font("Tahoma", Font.PLAIN, 16);
	public final static Font FUENTE_NEGRITA = new Font("Tahoma", Font.BOLD, 16);
	public final static String TITULO_VENTANA = "Aseguradora de veh�culos.";

	public final static String[] TEXTO_CLIENTES = { "Clientes", "Nombre *",
			"Apellidos", "DNI *", "Tel�fono", "F. Nacimiento", "F. Carnet",
			"Direcci�n", "Nombre, dni, id_poliza" };
	public final static String[] TEXTO_VEHICULOS = { "Veh�culos",
			"Matr�cula *", "Marca", "Modelo", "Potencia", "A�o-fabricaci�n",
			"Color", "Puertas", "Kil�metros *",
			"Matr�cula, kil�metros, *id_poliza" };
	public final static String[] TEXTO_EXTRAS = { "Extras", "A�o-fabricaci�n",
			"Marca", "Nombre *", "Modelo *", "Descripci�n", "Lugar de origen",
			"Dimensiones *", "Fabricante", "Nombre, Modelo" };
	public final static String[] TEXTO_POLIZAS = { "P�lizas", "Tipo",
			"Importe", "Estado ", "F. Inicio", "Conductores", "Antig�edad",
			"F. Fin", "id (cliente) *", "id (veh�culo) *",
			"id (cliente), id (veh�culo)" };

	public final static String[] COLUMNAS_CLIENTE = { "Id", "C�digo", "Nombre",
			"Apellidos", "DNI", "Tel�fono", "Nacimiento", "Carnet", "Direcci�n" };
	public final static String[] COLUMNAS_VEHICULO = { "Id", "Chasis",
			"Matr�cula", "Marca", "Modelo", "Potencia", "Fabricaci�n", "Color",
			"Puertas", "Kil�metros" };
	public final static String[] COLUMNAS_EXTRA = { "Id", "Fabricaci�n",
			"Marca", "Nombre", "Modelo", "Descripci�n", "Origen",
			"Dimensiones", "Fabricante" };
	public final static String[] COLUMNAS_POLIZA = { "Id", "N�mero", "Tipo",
			"Importe", "Estado", "Inicio", "Conductores",
			"Antiguedad de conducci�n", "Fin", "Id (cliente)", "Id (vehiculo)" };

}
