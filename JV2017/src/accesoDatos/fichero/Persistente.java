/** 
 * Proyecto: Juego de la vida.
 * Interfaz con las operaciones básicas de persistencia en fichero.
 * @since: prototipo2.0
 * @source: Persistente.java  
 * @version: 2.0 - 2018.04.29 
 * @author: ajp
 */

package accesoDatos.fichero;

import accesoDatos.DatosException;

public interface Persistente {

	/**
	 *  Permite guarda el Arraylist de objetos en ficheros.
	 */
	void guardarDatos();
	
	/**
	 *  Permite recupera el Arraylist de objetos almacenados en fichero. 
	 * @throws DatosException 
	 */
	void recuperarDatos() throws DatosException;
	
} // interface
