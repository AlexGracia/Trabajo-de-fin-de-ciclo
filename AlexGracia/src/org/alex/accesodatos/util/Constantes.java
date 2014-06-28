package org.alex.accesodatos.util;

import java.awt.Font;

/**
 * Clase con variables globales.
 * 
 * @author Alex Gracia
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
	// TODO add search bar text
	public final static String[] TEXTO_PIEZAS = { "Piezas", "Nombre *",
			"Descripción", "Cantidad", "Precio", "Origen *", "F. Solicitud",
			"Marca", "" };
	public final static String[] TEXTO_PROVEEDORES = { "Proveedores",
			"Nombre *", "Teléfono *", "Correo", "F. Nacimiento", "Dirección",
			"Pago", "Nombre-Empresa *", "DNI *", "" };
	public final static String[] TEXTO_TALLERES = { "Talleres", "Nombre *",
			"Dirección *", "Teléfono", "F. Inicio", "Nombre-Jefe",
			"CIF-Empresa *", "Trabajadores", "Reparaciones", "" };

	public final static String[] TEXTO_POLIZAS = { "Pólizas", "Tipo",
			"Importe", "Estado ", "F. Inicio", "Conductores", "Antigüedad",
			"F. Fin", "Id (cliente) *", "Id (vehículo) *",
			"Id (cliente), Id (vehículo)" };
	// TODO add search bar text
	public final static String[] TEXTO_SINIESTROS = { "Siniestros",
			"Datos-Póliza", "Importe-Reparación", "Datos-Cliente",
			"F. Reparación", "Datos-Taller", "F. Siniestro",
			"Vehiculos-Implicados", "Id (cliente) *", "Id (taller) *",
			"Clientes heridos", "" };

	public final static String[] COLUMNAS_CLIENTE = { "Id", "Código", "Nombre",
			"Apellidos", "DNI", "Teléfono", "Nacimiento", "Carnet", "Dirección" };
	public final static String[] COLUMNAS_VEHICULO = { "Id", "Chasis",
			"Matrícula", "Marca", "Modelo", "Potencia", "Fabricación", "Color",
			"Puertas", "Kilómetros" };
	public final static String[] COLUMNAS_EXTRA = { "Id", "Fabricación",
			"Marca", "Nombre", "Modelo", "Descripción", "Origen",
			"Dimensiones", "Fabricante" };
	public final static String[] COLUMNAS_PIEZA = { "Id", "Código", "Nombre",
			"Descripción", "Cantidad", "Precio", "Origen", "Solicitud", "Marca" };
	public final static String[] COLUMNAS_PROVEEDOR = { "Id", "Nombre",
			"Teléfono", "Correo", "Nacimiento", "Dirección", "Pago", "Empresa",
			"DNI" };
	public final static String[] COLUMNAS_TALLERE = { "Id", "Nombre",
			"Dirección", "Teléfono", "Inicio", "Jefe", "CIF", "Trabajadores",
			"Reparaciones" };
	public final static String[] COLUMNAS_POLIZA = { "Id", "Número", "Tipo",
			"Importe", "Estado", "Inicio", "Conductores",
			"Antiguedad de conducción", "Fin", "Id (cliente)", "Id (vehiculo)" };
	public final static String[] COLUMNAS_SINIESTRO = { "Id", "Datos poliza",
			"Reparación", "Datos cliente", "Reparación", "Datos taller",
			"Siniestro", "Vehículos implicados", "Id (cliente)", "Id (taller)",
			"Clientes heridos" };
}
