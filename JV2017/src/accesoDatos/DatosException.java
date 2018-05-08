/** 
 * Proyecto: Juego de la vida.
 * Maneja los errores de las clases de la capa de acceso a datos.
 *  @since: prototipo2.0
 *  @source: DatosException.java 
 * @version: 2.0 - 2018.04.12
 *  @author: ajp
 */

package accesoDatos;

public class DatosException extends Exception {

	/**
	 * Excepción por defecto sin mensaje.
	 */
	public DatosException() {
		super();
	}
	
	/**
	 * Excepción con mensaje
	 * @param msg - el mensaje de error asociado.
	 */
	public DatosException(String mensaje) {
		super(mensaje);
	}

}
