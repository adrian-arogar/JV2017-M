/** 
 * Proyecto: Juego de la vida.
 *  Prueba Junit4 de la clase Mundo según el modelo 2.
 *  @since: prototipo2.0
 *  @source: MundoTest.java 
 *  @version: 2.0 - 2018/04/14
 *  @author: ajp
 */

package test.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.Mundo;
import modelo.Patron;
import modelo.Posicion;

public class MundoTest {
	private Mundo mundo1;
	private static Mundo mundo2;

	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		mundo2 = new Mundo("MundoPrueba", new ArrayList<Integer>(),
				new HashMap<Patron, Posicion>());
	}

	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosVariables() {
		mundo1 = new Mundo("Demo", 
				new ArrayList<Integer>(), 
				new HashMap<Patron, Posicion>()
				);
	}

	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After	
	public void borrarDatosPrueba() {
		mundo1 = null;
	}
	
	// Test con DATOS VALIDOS
	@Test
	public void testMundoConvencional() {
		assertEquals(mundo2.getNombre(), "MundoPrueba");
		assertEquals(mundo2.getConstantes(), new ArrayList<Integer>());
		assertEquals(mundo2.getDistribucion(), new HashMap<Patron, Posicion>());
	}

	@Test
	public void testMundo() {
		assertEquals(mundo1.getNombre(), "Demo");
		assertEquals(mundo1.getConstantes(), new ArrayList<Integer>());
		assertEquals(mundo1.getDistribucion(), new HashMap<Patron, Posicion>());
	}

	@Test
	public void testMundoMundo() {
		assertEquals(mundo2, new Mundo(mundo2));
	}

	@Test
	public void testSetNombre() {
		mundo2.setNombre("MundoPrueba");
		assertEquals(mundo2.getNombre(), "MundoPrueba");	
	}

	@Test
	public void testToString() {
		assertNotNull(mundo2.toString());
	}

	@Test
	public void testEqualsObject() {
		assertEquals(mundo2, new Mundo("MundoPrueba", 
				new ArrayList<Integer>(),
				new HashMap<Patron, Posicion>()));
	}

	@Test
	public void testClone() {
		assertEquals(mundo2, mundo2.clone());
	}

	// Test con DATOS NO VALIDOS
	@Test
	public void testMundoConvencionalNombreNull() {	

		try {
			mundo2 = new Mundo(null, 
					new ArrayList<Integer>(),
					new HashMap<Patron, Posicion>()
					);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) {
		}
	}

	@Test
	public void testSetNombreNull() {
		try {
			mundo1.setNombre(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) {
		}
	}

} //class
