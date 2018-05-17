/** 
 *  Proyecto: Juego de la vida.
  Clase JUnit 4 para pruebas del DAO de simulaciones y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: SimulacionesDAOTest.java 
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
import modelo.Mundo;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import util.Fecha;

public class SimulacionesDAOTest {

	private static Datos fachada;
	private Simulacion simulacionPrueba;

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
			simulacionPrueba = new Simulacion(fachada.obtenerUsuario("III1R"), new Fecha(), new Mundo(), EstadoSimulacion.PREPARADA);
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
		fachada.borrarTodasSimulaciones();
		simulacionPrueba = null;
	}

	@Test
	public void testObtenerSimulacion() {
		fail("No implenetado");
	}

	@Test
	public void testAltaSimulacion() {
		fail("No implenetado");
	}

	@Test
	public void testBajaSimulacion() {
		fail("No implenetado");
	}

	@Test
	public void testActualizarSimulacion() {	
		fail("No implenetado");
	}

	@Test
	public void testToStringDatosSimulaciones() {
		fail("No implenetado");
	}

} //class
