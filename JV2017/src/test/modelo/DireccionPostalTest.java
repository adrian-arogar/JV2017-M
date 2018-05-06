/** 
 * Proyecto: Juego de la vida.
 *  Prueba Junit4 de la clase DireccionPostal según el modelo 2.
 *  @since: prototipo2.0
 *  @source: CorreoTest.java 
 *  @version: 2.0 - 2018/04/14
 *  @author: ajp
 */

package test.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.DireccionPostal;
import modelo.ModeloException;

public class DireccionPostalTest {

	private DireccionPostal direccion1;
	private static DireccionPostal direccion2;

	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		// Objetos no modicados en las pruebas.
		try {
			direccion2 = new DireccionPostal("Flan", "21", "88888", "Murcia");
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosVariables() {
		// Objetos para la prueba.
		try {
			direccion1 = new DireccionPostal();
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After
	public void borrarDatosPrueba() {
		direccion1 = null;
	}

	// Test con DATOS VALIDOS
	@Test
	public void testDireccionPostalConvencional() {
		assertEquals(direccion2.getCalle(), "Flan");
		assertEquals(direccion2.getNumero(), "21");
		assertEquals(direccion2.getCp(), "88888");
		assertEquals(direccion2.getPoblacion(), "Murcia");
	}

	@Test
	public void testDireccionPostal() {
		assertEquals(direccion1.getCalle(), "Calle");
		assertEquals(direccion1.getNumero(), "00");
		assertEquals(direccion1.getCp(), "00000");
		assertEquals(direccion1.getPoblacion(), "Población");
	}

	@Test
	public void testDireccionPostalDireccionPostal() {
		assertEquals(direccion2, new DireccionPostal(direccion2));
	}
	
	@Test
	public void testSetCalle() {
		try {
			direccion1.setCalle("Calle");
			assertEquals(direccion1.getCalle(), "Calle");
		} 
		catch (ModeloException e) {
			
		}	
	}
	
	@Test
	public void testSetNumero() {
		try {
			direccion1.setNumero("00");
			assertEquals(direccion1.getNumero(), "00");
		} 
		catch (ModeloException e) {
			fail("No debe llegar aquí...");
		}	
	}
	
	@Test
	public void testSetCP() {
		try {
			direccion1.setCp("99999");
			assertEquals(direccion1.getCp(), "99999");
		} 
		catch (ModeloException e) {
			fail("No debe llegar aquí...");
		}	
	}
	
	@Test
	public void testSetPoblacion() {
		try {
			direccion1.setPoblacion("Población");
			assertEquals(direccion1.getPoblacion(), "Población");
		} 
		catch (ModeloException e) { 
			fail("No debe llegar aquí...");
		}
	}
	
	@Test
	public void testToString() {
		assertEquals(direccion2.toString(), "Flan, 21, 88888, Murcia");
	}
	
	@Test
	public void testEqualsObject() {
		try {		
			assertTrue(direccion1.equals(new DireccionPostal()));
		} 
		catch (ModeloException e) {
			fail("No debe llegar aquí...");
		}
	}

	@Test
	public void testClone() {
		assertEquals(direccion2, direccion2.clone());
	}
	
	// Test con DATOS NO VALIDOS
	@Test
	public void testCorreoConvencionalCalleNull() {	

		try {
			new DireccionPostal(null, "21", "88888", "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
		}
	}

	@Test
	public void testCorreoConvencionalNumeroNull() {	

		try {
			new DireccionPostal("Flan", null, "88888", "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
		}
	}
	
	@Test
	public void testCorreoConvencionalCPNull() {	

		try {
			new DireccionPostal("Flan", "21", null, "Murcia");
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
		}
	}
	
	@Test
	public void testCorreoConvencionalPoblacionNull() {	

		try {
			new DireccionPostal("Flan", "21", "88888", null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
		}
	}
	
	@Test
	public void testSetCalleNull() {
		try {
			direccion1.setCalle(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
		}
	}

	@Test
	public void testSetNumeroNull() {
		try {
			direccion1.setNumero(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
		}
	}
	
	@Test
	public void testSetCPNull() {
		try {
			direccion1.setCp(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
		}
	}
	
	@Test
	public void testSetPoblacionNull() {
		try {
			direccion1.setPoblacion(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) {
		}
	}
	
} // class