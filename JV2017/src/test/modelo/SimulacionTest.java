/** Proyecto: Juego de la vida.
 *  Prueba Junit4 de la clase Simulacion según el modelo 2.
 *  @since: prototipo2.0
 *  @source: CorreoTest.java 
 *  @version: 2.0 - 2018/04/20
 *  @author: ajp
 */

package test.modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Nif;
import modelo.SesionUsuario;
import modelo.Simulacion;
import modelo.Usuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class SimulacionTest {
	private Simulacion simulacion1;
	private static Simulacion simulacion2;
	private static Usuario usr;
	private static Fecha fecha;
	private static Mundo mundo;

	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		// Objetos no modicados en las pruebas.
		try {
			usr = new Usuario(new Nif("00000001R"), "Luis", "Pérez Ruiz",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), new Fecha(2000, 03, 21),
					new Fecha(2015,05,12), new ClaveAcceso(), RolUsuario.NORMAL);
			fecha = new Fecha(2018, 4, 20, 10, 35, 2);
			mundo = new Mundo();
			simulacion2 = new Simulacion(usr, fecha, mundo, EstadoSimulacion.PREPARADA);
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void iniciarlizarDatosVariables() {	
		try {
			simulacion1 = new Simulacion();
			
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	@After
	public void borrarDatosPrueba() {	
		simulacion1 = null;
	}

	// Test con DATOS VALIDOS
	@Test
	public void testSimulacionConvencional() {	
		assertEquals(simulacion2.getUsr(), usr);
		assertEquals(simulacion2.getFecha(), fecha);
		assertEquals(simulacion2.getMundo(), mundo);
		assertEquals(simulacion2.getEstado(), EstadoSimulacion.PREPARADA);
	}

	@Test
	public void testSimulacionDefecto() {
		assertNotNull(simulacion1.getUsr());
		assertNotNull(simulacion1.getFecha());
		assertEquals(simulacion1.getMundo(), new Mundo());
		assertEquals(simulacion1.getEstado(), EstadoSimulacion.PREPARADA);
	}

	@Test
	public void testSimulacionCopia() {
		assertNotSame(simulacion2, new Simulacion(simulacion2));
	}

	@Test
	public void testGetUsr() {
		assertEquals(simulacion2.getUsr(), usr);
	}

	@Test
	public void testGetMundo() {
		assertEquals(simulacion2.getMundo(), mundo);
	}

	@Test
	public void testGetFecha() {
		assertEquals(simulacion2.getFecha(), fecha);
	}

	@Test
	public void testGetEstado() {
		assertEquals(simulacion2.getEstado(), EstadoSimulacion.PREPARADA);
	}

	@Test
	public void testGetIdSimulacion() {
		assertEquals(simulacion2.getIdSimulacion(), "LPR1R:20180420103502");
	}

	@Test
	public void testSetUsr() {
		simulacion1.setUsr(usr);
		assertEquals(simulacion1.getUsr(), usr);
	}

	@Test
	public void testSetMundo() {
		simulacion1.setMundo(mundo);
		assertEquals(simulacion1.getMundo(), mundo);
	}

	@Test
	public void testSetFecha() {
		simulacion1.setFecha(fecha);
		assertEquals(simulacion1.getFecha(), fecha);
	}

	@Test
	public void testSetEstado() {
		simulacion1.setEstado(EstadoSimulacion.PREPARADA);
		assertEquals(simulacion1.getEstado(), EstadoSimulacion.PREPARADA);
	}

	@Test
	public void testToString() {
		assertNotNull(simulacion1.toString());
	}

	@Test
	public void testEquals() {
		assertEquals(simulacion2, new Simulacion(usr, fecha, mundo, EstadoSimulacion.PREPARADA));
	}

	@Test
	public void testClone() {
		assertNotSame(simulacion2, simulacion2.clone());
	}

} // class
