package org.alex.accesodatos.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.alex.libs.Util;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase con la que testeo los metodos esFechaFuturista() y esMenorDeEdad() de la libreria Util.
 * 
 * @author Alex Gracia
 * @version 1.0
 * 
 */
public class Tests {

	private Calendar calendario;
	private Date fechaActual;

	@Before
	public void inicializar() {
		calendario = Calendar.getInstance();
		fechaActual = calendario.getTime();
	}

	@Test
	public void testFechaActual() {
		assertFalse(Util.esFechaFuturista(fechaActual, "actual"));
	}

	@Test
	public void testFechaFutura() {
		calendario.add(Calendar.DAY_OF_MONTH, +1);
		assertTrue(Util.esFechaFuturista(calendario.getTime(), "futura"));
	}

	@Test
	public void testMenorDeEdad() {
		assertTrue(Util.esMenorDeEdad(fechaActual));
	}

	@Test
	public void testMayorDeEdad() {
		calendario.add(Calendar.DAY_OF_MONTH, -1);
		calendario.add(Calendar.YEAR, -18);
		assertFalse(Util.esMenorDeEdad(calendario.getTime()));
	}

}
