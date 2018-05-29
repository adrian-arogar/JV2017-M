/** 
 * Proyecto: Juego de la vida.
 * Clase JUnit de prueba automatizada de las características de la clase Posicion según el modelo 2.
 * @since: prototipo2
 * @source: PosicionTest.java 
 * @version: 2.0 - 2018.04.25
 * @author: ajp
 */

package test.modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Persona;
import modelo.Posicion;
import util.Fecha;

public class PosicionTest {
	private Posicion posicion1; 
	private static Posicion posicion2;
	
	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		posicion2 = new Posicion(5,3);
	}

	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 */
	@Before
	public void iniciarlizarDatosVariables() {
		try {
			// Objetos modificados en las pruebas.
			posicion1 = new Posicion(); 
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
		posicion1 = null;
	}

	// Test CON DATOS VALIDOS

	@Test
	public void test() {
		
	}

}
