/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit 4 para pruebas del DAO de sesiones y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: SesionesDAOTest.java 
 *  @version: 2.1 - 2018/05/17 
 *  @author: ajp
 */

package test.accesoDatos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import modelo.ModeloException;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import util.Fecha;

public class SesionesDAOTest {

	private static Datos fachada;
	private SesionUsuario sesionPrueba;

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
			sesionPrueba = new SesionUsuario(fachada.obtenerUsuario("III1R"), new Fecha(), EstadoSesion.EN_PREPARACION);
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
		fachada.borrarTodasSesiones();
		sesionPrueba = null;
	}

	@Test
	public void testObtenerSesionId() {
		fail("No implenetado");
	}

	@Test
	public void testObtenerSesion() {
		fail("No implenetado");
	}

	@Test
	public void testAltaSesion() {
		fail("No implenetado");
	}

	@Test
	public void testBajaSesionUsuario() {
		fail("No implenetado");
	}

	@Test
	public void testActualizarSesion() {
		fail("No implenetado");
	}

	@Test
	public void testToStringDatosSesiones() {
		fail("No implenetado");
	}

} //class
