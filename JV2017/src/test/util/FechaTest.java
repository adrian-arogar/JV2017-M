/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit de prueba automatizada de las características de la clase Fecha según el modelo 2.
 *  @since: prototipo2
 *  @source: FechaTest.java 
 *  @version: 2.0 - 2018/04/23
 *  @author: ajp
 */

package test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Fecha;

public class FechaTest {
	private Fecha fecha1; 
	private static Fecha fecha2;
	private static Fecha fecha3;
	
	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		// Objetos fijos en las pruebas.
		fecha2 = new Fecha(2000, 02, 20);
		fecha3 = new Fecha(2000, 02, 20, 15, 10, 5);
	}
	
	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosVariables() {
		// Objetos modificados en las pruebas.
		fecha1 = new Fecha();
	}
	
	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After	
	public void borrarDatosPrueba() {
		fecha1 = null;	
	}

	// Test CON DATOS VALIDOS
	@Test
	public void testFechaParcial() {
		assertNotNull(fecha2);
		assertEquals(fecha2.getAño(), 2000);
		assertEquals(fecha2.getMes(), 2);
		assertEquals(fecha2.getDia(), 20);
	}

	@Test
	public void testFechaConvencional() {
		assertNotNull(fecha3);
		assertEquals(fecha3.getAño(), 2000);
		assertEquals(fecha3.getMes(), 2);
		assertEquals(fecha3.getDia(), 20);
		assertEquals(fecha3.getHora(), 15);
		assertEquals(fecha3.getMinuto(), 10);
		assertEquals(fecha3.getSegundo(), 5);
	}
	
	@Test
	public void testFechaDefecto() {
		assertNotNull(fecha1);
	}

	@Test
	public void testFechaCopia() {
		fecha1 = new Fecha(fecha2);
		assertNotSame(fecha1, fecha2);
	}

	@Test
	public void testGetAño() {
		assertEquals(fecha2.getAño(), 2000);
	}

	@Test
	public void testGetMes() {
		assertEquals(fecha2.getMes(), 2);
	}

	@Test
	public void testGetDia() {
		assertEquals(fecha2.getDia(), 20);
	}

	@Test
	public void testGetHora() {
		assertEquals(fecha3.getHora(), 15);
	}
	
	@Test
	public void testGetMinuto() {
		assertEquals(fecha3.getMinuto(), 10);
	}
	
	@Test
	public void testGetSegundo() {
		assertEquals(fecha3.getSegundo(), 5);
	}
	
	@Test
	public void testSetAño() {
		fecha1.setAño(2000);
		assertEquals(fecha1.getAño(), 2000);
	}

	@Test
	public void testSetMes() {
		fecha1.setMes(2);
		assertEquals(fecha1.getMes(), 2);
	}

	@Test
	public void testSetDia() {
		fecha1.setDia(20);
		assertEquals(fecha1.getDia(), 20);
	}

	@Test
	public void testSetHora() {
		fecha1.setHora(15);
		assertEquals(fecha1.getHora(), 15);
	}
	
	@Test
	public void testSetMinuto() {
		fecha1.setMinuto(10);
		assertEquals(fecha1.getMinuto(), 10);
	}
	
	@Test
	public void testSetSegundo() {
		fecha1.setSegundo(5);
		assertEquals(fecha1.getSegundo(), 5);
	}
	
	@Test
	public void testDifSegundos() {
		Fecha fecha = new Fecha(2000, 02, 21);
		assertEquals(fecha.difSegundos(fecha2), 86400);
	}

	@Test
	public void testDifMinutos() {
		Fecha fecha = new Fecha(2000, 02, 21);
		assertEquals(fecha.difMinutos(fecha2), 86400/60);
	}

	@Test
	public void testDifHoras() {
		Fecha fecha = new Fecha(2000, 02, 21);
		assertEquals(fecha.difHoras(fecha2), 24);
	}

	@Test
	public void testDifDias() {
		Fecha fecha = new Fecha(2000, 02, 21);
		assertEquals(fecha.difDias(fecha2), 1);
	}

	@Test
	public void testDifSemanas() {
		Fecha fecha = new Fecha(2000, 02, 27);
		assertEquals(fecha.difSemanas(fecha2), 1);
	}

	@Test
	public void testDifMeses() {
		Fecha fecha = new Fecha(2000, 03, 20);
		assertEquals(fecha.difMeses(fecha2), 1);
	}

	@Test
	public void testDifAños() {
		Fecha fecha = new Fecha(2001, 02, 20);
		assertEquals(fecha.difAños(fecha2), 1);
	}

	@Test
	public void testAddSegundos() {
		Fecha fecha = new Fecha(2000, 02, 19);
		fecha.addSegundos(86400);
		assertEquals(fecha.getDia(), 20);
	}

	@Test
	public void testAddMinutos() {
		Fecha fecha = new Fecha(2000, 02, 19);
		fecha.addMinutos(86400/60);
		assertEquals(fecha.getDia(), 20);
	}

	@Test
	public void testAddHoras() {
		Fecha fecha = new Fecha(2000, 02, 19);
		fecha.addHoras(24);
		assertEquals(fecha.getDia(), 20);
	}

	@Test
	public void testAddDias() {
		Fecha fecha = new Fecha(2000, 02, 19);
		fecha.addDias(1);
		assertEquals(fecha.getDia(), 20);
	}

	@Test
	public void testAddSemanas() {
		Fecha fecha = new Fecha(2000, 02, 19);
		fecha.addSemanas(4);
		assertEquals(fecha.getMes(), 3);
	}

	@Test
	public void testAddMeses() {
		Fecha fecha = new Fecha(2000, 02, 19);
		fecha.addMeses(1);
		assertEquals(fecha.getMes(), 3);
	}

	@Test
	public void testAddAños() {
		Fecha fecha = new Fecha(2000, 02, 19);
		fecha.addAños(1);
		assertEquals(fecha.getAño(), 2001);
	}

	@Test
	public void testToDate() {
		Date hoy = fecha1.toDate();
		assertNotNull(hoy);
	}

	@Test
	public void testToGregorianCalendar() {
		GregorianCalendar hoy = fecha1.toGregorianCalendar();
		assertNotNull(hoy);
	}

	@Test
	public void testCompareTo() {
		Fecha fecha = new Fecha(2000, 02, 21);
		assertTrue(fecha2.compareTo(fecha2) == 0);
		assertTrue(fecha.compareTo(fecha2) > 0);
		assertTrue(fecha2.compareTo(fecha) < 0);
	}

	@Test
	public void testToString() {
		assertEquals(fecha3.toString(), "2000.02.20 - 15:10:05");
	}
	
	@Test
	public void marcaTiempoMilisegundos() {
		assertEquals(fecha3.marcaTiempoMilisegundos(), 951055805000L);
	}
	
	@Test
	public void marcaTiempo() {
		assertEquals(fecha3.marcaTiempo(), 20000220151005L);
	}
	
	@Test
	public void testToStringMarcaTiempo() {
		assertEquals(fecha3.toStringMarcaTiempo(), "20000220151005");
	}
	
	@Test
	public void testEquals() {
		fecha1 = new Fecha(2000, 02, 20);
		assertTrue(fecha1.equals(fecha2));
	}

	@Test
	public void testClone() {
		fecha1 = (Fecha) fecha2.clone();
		assertNotSame(fecha1, fecha2);
	}

	@Test
	public void testHashCode() {
		assertEquals(fecha2.hashCode(), -40233105);
	}
	
} //class
