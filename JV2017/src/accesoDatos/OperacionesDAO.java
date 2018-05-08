/** 
 * Proyecto: Juego de la vida.
 * Interfaz con las operaciones básicas DAO, necesarias para la fachada.
 * @since: prototipo2.0
 * @source: OperacionesDAO.java  
 * @version: 2.0 - 2018.04.23 
 * @author: ajp
 */

package accesoDatos;

public interface OperacionesDAO {

	/**
	 * Obtiene el objeto dado el id utilizado para el almacenamiento.
	 * @param id - el idUsr de Usuario a obtener.
	 * @return - el Usuario encontrado; DatosException si no existe.
	 * @throws DatosException 
	 */	
	Object obtener(String id) throws DatosException;

	/**
	 * Obtiene el objeto, dado otro objeto igual.
	 * reenvía al método que utiliza id.
	 * @param obj - el Objeto a buscar.
	 * @return - el Object encontrado; DatosException si no existe.
	 * @throws DatosException 
	 */
	Object obtener(Object obj) throws DatosException;
	
	/**
	 *  Alta de un objeto en el almacén de datos, 
	 *  sin repeticiones, según el campo id previsto. 
	 *	@param obj - Objeto a almacenar.
	 *  @throws DatosException - si ya existe.
	 */
	void alta(Object obj) throws DatosException;
	
	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param id - el identificador del objeto a eliminar.
	 * @return - el Objeto eliminado.
	 * @throws DatosException - si no existe.
	 */
	Object baja(String id) throws DatosException;
	
	/**
	 *  Actualiza datos de un Objeto reemplazando el almacenado por el recibido.
	 *	@param obj - Objeto nuevo.
	 *  @throws DatosException - si no existe.
	 */
	void actualizar(Object obj) throws DatosException;
	
	/**
	 * Obtiene el listado de todos los datos almacenados.
	 * @return el texto con el volcado de datos.
	 */
	String listarDatos();

	/**
	 * Elimina todos los datos y restaura predeterminados.
	 */
	void borrarTodo();
	
	/**
	 *  Cierra datos.
	 */
	public void cerrar();
	
} // interface
