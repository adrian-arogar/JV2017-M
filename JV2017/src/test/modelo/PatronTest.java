/** 
 * Proyecto: Juego de la vida.
 * Clase JUnit de prueba automatizada de las características de la clase Patron según el modelo 2.
 * @since: prototipo2
 * @source: PatronTest.java 
 * @version: 2.0 - 2018.04.25
 * @author: ajp
 */

package test.modelo;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.ModeloException;
import modelo.Patron;

public class PatronTest {
	static Patron patron1; 
	static Patron patron2;
	
	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		patron2 = new Patron("PatronPrueba2", 
				new byte[][] {{0, 0, 0, 0},
							  {1, 1, 1, 0},
							  {0, 1, 1, 0},
							  {0, 1, 0, 0}});
	}
	
	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosVariables() {
		try {
			// Objetos modificados en las pruebas.
			patron1 = new Patron();  
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After	
	public void borrarDatosPrueba() {
		patron1 = null;
	}

	// Test CON DATOS VALIDOS

	@Test
	public void testToString() {
		assertEquals(patron2.toString(), "0000111001100100");
	}

	// Test CON DATOS NO VALIDOS
}
