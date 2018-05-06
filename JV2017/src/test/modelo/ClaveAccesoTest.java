/** 
 * Proyecto: Juego de la vida.
 * Prueba Junit4 de la clase ClaveAcceso según el modelo 2.
 * @since: prototipo 2.0
 * @source: ClaveAccesoTest.java 
 * @version: 2.0 - 2018.04.12
 * @author: ajp
 */

package test.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.ClaveAcceso;
import modelo.ModeloException;

public class ClaveAccesoTest {
	private ClaveAcceso clave1;
	private static ClaveAcceso clave2;
	
	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		// Objetos no modicados en las pruebas.
		try {
			clave2 = new ClaveAcceso("Miau#2");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void iniciarlizarDatosVariables() {	
		try {
			clave1 = new ClaveAcceso();
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void borrarDatosVariables() {	
		clave1 = null;
	}
	
	// Test con DATOS VALIDOS
	@Test
	public void testClaveAccesoConvencional() {	
		try {
			assertEquals(clave2.getTexto(), new ClaveAcceso("Miau#2").getTexto());
		} 
		catch (ModeloException e) {
			fail("No debe llegar aquí");
		}
	}

	@Test
	public void testClaveAcceso() {
		try {
			assertEquals(clave1.getTexto(), new ClaveAcceso("Miau#0").getTexto());
		} 
		catch (ModeloException e) {
			fail("No debe llegar aquí");
		}
	}

	@Test
	public void testClaveAccesoClaveAcceso() {
		try {
			assertEquals(clave2, new ClaveAcceso(clave2));
		} 
		catch (Exception e) {	
			fail("No debe llegar aquí");
		}
	}

	@Test
	public void testSetTexto() {
		try {
			clave1.setTexto("Miau#1");
			assertEquals(clave1.getTexto(), new ClaveAcceso("Miau#1").getTexto());
		} 
		catch (Exception e) {
			fail("No debe llegar aquí");
		}
	}

	@Test
	public void testToString() {
		try {
			assertEquals(clave2.toString(), new ClaveAcceso("Miau#2").getTexto());
		} 
		catch (ModeloException e) {
			fail("No debe llegar aquí");
		}
	}

	@Test
	public void testEquals() {
		try {
			assertTrue(clave2.equals(new ClaveAcceso("Miau#2")));
		} 
		catch (Exception e) { 
			fail("No debe llegar aquí");
		}
	}

	@Test
	public void testClone() {
		assertEquals(clave2, clave2.clone());
	}

	// Test con DATOS NO VALIDOS
	@Test
	public void testClaveAccesoConvencionalTextoNull() {	
		try {
			String texto = null;
			new ClaveAcceso(texto);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 	
		}
	}
	
	@Test
	public void testClaveAccesoConvencionalTextoMalFormato() {	
		try {
			new ClaveAcceso("hola");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) { 
		}
	}
	
	@Test
	public void testSetTextoNull() {
		try {
			clave1.setTexto(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 	
		}
	}
	
	@Test
	public void testSetTextoMalFormato() {
		try {
			clave1.setTexto("hola");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) { 
		}
	}
	
} //class
