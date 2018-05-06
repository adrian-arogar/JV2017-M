/** 
 * Proyecto: Juego de la vida.
 *  Prueba Junit4 del paquete modelo según el modelo 2.
 *  @since: prototipo2.0
 *  @source: AllTest.java 
 *  @version: 2.0 - 2018/04/14
 *  @author: ajp
 */

package test.modelo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ClaveAccesoTest.class, 
	CorreoTest.class, 
	DireccionPostalTest.class, 
	MundoTest.class,
	NifTest.class, 
	PatronTest.class,
	PersonaTest.class,
	PosicionTest.class,
	SesionUsuarioTest.class,
	SimulacionTest.class,
	UsuarioTest.class 
	})

public class AllTests {

}
