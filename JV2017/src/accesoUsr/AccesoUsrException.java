/** 
 * Proyecto: Juego de la vida.
 * Maneja los errores de acceso del usuario.
 *  @since: prototipo2.1
 *  @source: AccesoUsrException.java 
 *  @version: 2.1 - 2016.05.20
 *  @author: ajp
 */

package accesoUsr;

public class AccesoUsrException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Excepción por defecto sin mensaje
	 */
	public AccesoUsrException() {
		super();
	}
	
	/**
	 * Excepción con mensaje
	 * @param msg - el mensaje de error asociado
	 */
	public AccesoUsrException(String msg) {
		super(msg);
	}
}
