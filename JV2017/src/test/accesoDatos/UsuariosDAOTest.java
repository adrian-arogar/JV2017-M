/** 
 *  Proyecto: Juego de la vida.
 *  Clase JUnit 4 para pruebas del DAO de usuarios y la parte de la fachada de Datos correspondiente.
 *  @since: prototipo2.1
 *  @source: UsuariosDAO.java 
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
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class UsuariosDAOTest {

	private static Datos fachada;
	private Usuario usrPrueba;


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
			// Usuario con idUsr "PMA8P"
			usrPrueba =  new Usuario(new Nif("00000008P"), "Pepe",
					"Márquez Alón", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("pepe@gmail.com"), new Fecha(1990, 11, 12), 
					new Fecha(2014, 12, 3), new ClaveAcceso("Miau#32"), RolUsuario.NORMAL);
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se ejecuta al terminar de cada @test.
	 */
	@After
	public void borraDatosPrueba() {
		fachada.borrarTodosUsuarios();
		usrPrueba = null;
	}

	@Test
	public void testObtenerUsuarioId() {
		fail("No implenetado");
	}

	@Test
	public void testObtenerUsuario() {
		fail("No implenetado");
	}

	@Test
	public void testAltaUsuario() {
		fail("No implenetado");
	}

	@Test
	public void testBajaUsuario() {
		fail("No implenetado");
	}

	@Test
	public void testActualizarUsuario() {
		fail("No implenetado");
	}

	@Test
	public void testToStringDatosUsuarios() {
		fail("No implenetado");
	}

	@Test
	public void testGetEquivalenciaId() {
		fail("No implenetado");
	}

} //class
