/** 
 * Proyecto: Juego de la vida.
 *  Prueba Junit4 del paquete accesoDatos según el modelo 2.
 *  @since: prototipo2.0
 *  @source: AllTest.java 
 *  @version: 2.0 - 2018/05/17
 *  @author: ajp
 */

package test.accesoDatos;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import modelo.Usuario;

@RunWith(Suite.class)
@SuiteClasses({ 
	UsuariosDAOTest.class, 
	SesionesDAOTest.class, 
	SimulacionesDAOTest.class, 
	MundosDAOTest.class, 
	PatronesDAOTest.class 
	})


public class AllTest {

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
	 * Método que se ejecuta una sola vez al final del conjunto pruebas.
	 */
	@AfterClass
	public static void cerrarFachadaDatos() {
		fachada.cerrar();
	}
}
