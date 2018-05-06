/** Proyecto: Juego de la vida.
 *  Prueba Junit4 de la clase SesionUsuario según el modelo 2.
 *  @since: prototipo2.0
 *  @source: SesionUsuarioTest.java 
 *  @version: 2.0 - 2018/04/20
 *  @author: ajp
 */

package test.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.SesionUsuario;
import modelo.SesionUsuario.EstadoSesion;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class SesionUsuarioTest {
	private SesionUsuario sesion1;
	private static SesionUsuario sesion2;
	private static Usuario usr;
	private static Fecha fecha;

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
			sesion2 = new SesionUsuario(usr, fecha, EstadoSesion.ACTIVA);
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void iniciarlizarDatosVariables() {	
		try {
			sesion1 = new SesionUsuario();
			
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	@After
	public void borrarDatosPrueba() {	
		sesion1 = null;
	}

	// Test con DATOS VALIDOS
	@Test
	public void testSesionUsuarioConvencional() {	
		assertEquals(sesion2.getUsr(), usr);
		assertEquals(sesion2.getFecha(), fecha);
		assertEquals(sesion2.getEstado(), EstadoSesion.ACTIVA);
	}

	@Test
	public void testSesionUsuarioDefecto() {
		assertNotNull(sesion1.getUsr());
		assertNotNull(sesion1.getFecha());
		assertEquals(sesion1.getEstado(), EstadoSesion.EN_PREPARACION);
	}

	@Test
	public void testSesionUsuarioCopia() {
		assertNotSame(sesion2, new SesionUsuario(sesion2));
	}

	@Test
	public void testGetUsr() {
		assertEquals(sesion2.getUsr(), usr);
	}

	@Test
	public void testGetFecha() {
		assertEquals(sesion2.getFecha(), fecha);
	}

	@Test
	public void testGetEstado() {
		assertEquals(sesion2.getEstado(), EstadoSesion.ACTIVA);
	}

	@Test
	public void testGetIdSesionUsuario() {
		assertEquals(sesion2.getIdSesion(), "LPR1R-20180420103502");
	}

	@Test
	public void testSetUsr() {
		sesion1.setUsr(usr);
		assertEquals(sesion1.getUsr(), usr);
	}

	@Test
	public void testSetFecha() {
		sesion1.setFecha(fecha);
		assertEquals(sesion1.getFecha(), fecha);
	}

	@Test
	public void testSetEstado() {
		sesion1.setEstado(EstadoSesion.ACTIVA);
		assertEquals(sesion1.getEstado(), EstadoSesion.ACTIVA);
	}

	@Test
	public void testToString() {
		assertNotNull(sesion1.toString());
	}

	@Test
	public void testEquals() {
		assertEquals(sesion2, new SesionUsuario(usr, fecha, EstadoSesion.ACTIVA));
	}

	@Test
	public void testClone() {
		assertNotSame(sesion2, sesion2.clone());
	}

} // class
