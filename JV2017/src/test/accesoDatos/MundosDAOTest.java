/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit 4 para pruebas del DAO de mundos y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: MundosDAOTest.java 
 *  @version: 2.1 - 2018/05/17 
 *  @author: ajp
 */

package test.accesoDatos;

import static org.junit.Assert.*;

import java.util.Hashtable;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Patron;
import modelo.Posicion;

public class MundosDAOTest {

	private static Datos fachada;
	private Mundo mundoPrueba;

	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void crearFachadaDatos() {
		try {
			fachada = new Datos();
		} 
		catch (DatosException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se ejecuta antes de cada @test.
	 * @throws ModeloException 
	 * @throws DatosException 
	 */
	@Before
	public void crearDatosPrueba() {
		try {
			mundoPrueba = fachada.obtenerMundo("Demo0");
		} 
		catch (DatosException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se ejecuta al terminar de cada @test.
	 */
	@After
	public void borraDatosPrueba() {
		fachada.borrarTodosMundos();
		mundoPrueba = null;
	}

	@Test
	public void testObtenerMundoString() {
		fail("No implenetado");
	}

	@Test
	public void testObtenerMundoMundo() {
		fail("No implenetado");
	}

	@Test
	public void testAltaMundo() {
		fail("No implenetado");
	}

	@Test
	public void testBajaMundo() {
		fail("No implenetado");
	}

	@Test
	public void testActualizarMundo() {
		fail("No implenetado");
	}

	@Test
	public void testToStringDatosMundos() {
		fail("No implenetado");
	}

} //class
