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
	public final static String TITULO_VENTANA = "Aseguradora de vehículos.";

	public final static String[] TEXTO_CLIENTES = { "Clientes", "Nombre *",
			"Apellidos", "DNI *", "Teléfono", "F. Nacimiento", "F. Carnet",
			"Dirección", "Nombre, dni, id_poliza" };
	public final static String[] TEXTO_VEHICULOS = { "Vehículos",
			"Matrícula *", "Marca", "Modelo", "Potencia", "Año-fabricación",
			"Color", "Puertas", "Kilómetros *",
			"Matrícula, kilómetros, *id_poliza" };
	public final static String[] TEXTO_EXTRAS = { "Extras", "Año-fabricación",
			"Marca", "Nombre *", "Modelo *", "Descripción", "Lugar de origen",
			"Dimensiones *", "Fabricante", "Nombre, Modelo" };
	public final static String[] TEXTO_POLIZAS = { "Pólizas", "Tipo",
			"Importe", "Estado ", "F. Inicio", "Conductores", "Antigüedad",
			"F. Fin", "id (cliente) *", "id (vehículo) *",
			"id (cliente), id (vehículo)" };

	public final static String[] COLUMNAS_CLIENTE = { "Id", "Código", "Nombre",
			"Apellidos", "DNI", "Teléfono", "Nacimiento", "Carnet", "Dirección" };
	public final static String[] COLUMNAS_VEHICULO = { "Id", "Chasis",
			"Matrícula", "Marca", "Modelo", "Potencia", "Fabricación", "Color",
			"Puertas", "Kilómetros" };
	public final static String[] COLUMNAS_EXTRA = { "Id", "Fabricación",
			"Marca", "Nombre", "Modelo", "Descripción", "Origen",
			"Dimensiones", "Fabricante" };
	public final static String[] COLUMNAS_POLIZA = { "Id", "Número", "Tipo",
			"Importe", "Estado", "Inicio", "Conductores",
			"Antiguedad de conducción", "Fin", "Id (cliente)", "Id (vehiculo)" };

}
