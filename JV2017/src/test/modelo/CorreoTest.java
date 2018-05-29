/** 
 *  Proyecto: Juego de la vida.
 *  Prueba Junit4 de la clase Correo según el modelo 2.
 *  @since: prototipo2.0
 *  @source: CorreoTest.java 
 *  @version: 2.0 - 2018/04/03
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

import modelo.Correo;
import modelo.ModeloException;

public class CorreoTest {
	private Correo correo1;
	private static Correo correo2;

	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		// Objetos no modicados en las pruebas.
		try {
			correo2 = new Correo("correo@correo.com");
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void iniciarlizarDatosVariables() {
		try {
			correo1 = new Correo();
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	@After
	public void borrarDatosVariables() {
		correo1 = null;
	}
	
	// Test con DATOS VALIDOS
	@Test
	public void testCorreoConvencional() {
		assertEquals(correo2.getTexto(), "correo@correo.com");
	}

	@Test
	public void testCorreo() {
		assertEquals(correo1.getTexto(), "correo@correo.com");
	}

	@Test
	public void testCorreoCorreo() {
		assertEquals(correo2, new Correo(correo2));
	}
	
	@Test
	public void testSetTexto() {
		try {
			correo2.setTexto("correo@correo.com");
			assertEquals(correo2.getTexto(), "correo@correo.com");
		} 
		catch (ModeloException e) {
			fail("No debe llegar aquí...");
		}
	}

	@Test
	public void testToString() {
		assertEquals("correo@correo.com", correo2.toString());
	}
	
	@Test
	public void testEqualsObject() {
		try {		
			assertTrue(correo2.equals(new Correo("correo@correo.com")));
		} 
		catch (ModeloException e) {
			fail("No debe llegar aquí...");
		}
	}

	@Test
	public void testClone() {
		assertEquals(correo2, correo2.clone());
	}

	@Test
	public void testHashCode() {
		assertEquals(correo1.hashCode(), -2034667982);
	}
	
	// Test con DATOS NO VALIDOS
		@Test
		public void testCorreoConvencionalTextoNull() {	

			try {
				String texto = null;
				new Correo(texto);
				fail("No debe llegar aquí...");
			} 
			catch (AssertionError | ModeloException e) {
			}
		}

		@Test
		public void testSetTextoNull() {
			try {
				correo1.setTexto(null);
				fail("No debe llegar aquí...");
			} 
			catch (AssertionError | ModeloException e) {
				assertTrue(true);
			}
		}
		
} //class
