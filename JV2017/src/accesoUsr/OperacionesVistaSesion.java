/** Proyecto: Juego de la vida.
 *  Operaciones que deben estar disponibles para el inicio de sesión.
 *  @since: prototipo2.1
 *  @source: OperacionesVistaSesion.java 
 *  @version: 2.1 - 2018.05.16
 *  @author: ajp
 */

package accesoUsr;

public interface OperacionesVistaSesion extends OperacionesVista {

	/** 
    *  Para interactuar con el usuario y que introduzca su identificador. 
	 * @return Devuelve el texto del identificador de usuario.
	 */
	String pedirIdUsr();

	/**
    * Para interactuar con el usuario y que introduzca su contraseña. 
	 * @return Devuelve el texto de la contraseña.
	 */
	String pedirClaveAcceso();

} // Interface
