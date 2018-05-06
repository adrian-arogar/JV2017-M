/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit de prueba automatizada de las características de la clase Criptografia según el modelo 2.
 *  @since: prototipo2
 *  @source: CriptografiaTest.java 
 *  @version: 2.0 - 2018/04/23
 *  @author: ajp
 */

package test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import util.Criptografia;
import util.UtilException;

public class CriptografiaTest {
	private static String textoClaro; 
	
	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		// Objetos fijos en las pruebas.
		textoClaro = "Miau#0";
	}
	
	@Test
	public void testCesar() {
		assertEquals(Criptografia.cesar(textoClaro), "Pmezd8");
	}
	
	@Test
	public void testCesarAleatorio() {
		try {
			assertEquals(Criptografia.cesarAleatorio(textoClaro, "02629"), "026298ÑKb1e");
		} 
		catch (UtilException e) {
		fail("No debe llegar aquí.");
		}
	}
	
} //class
