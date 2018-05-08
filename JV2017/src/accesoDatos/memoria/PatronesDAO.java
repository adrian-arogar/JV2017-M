/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Patron utilizando un ArrayList.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: PatronesDAO.java 
 * @version: 2.0 - 2018/04/20
 * @author: ajp
 */

package accesoDatos.memoria;

import java.util.ArrayList;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.Patron;

public class PatronesDAO implements OperacionesDAO {

	// Requerido por el Singleton 
	private static PatronesDAO instancia = null;

	// Elemento de almacenamiento. 
	private static ArrayList<Patron> datosPatrones;

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private PatronesDAO() {
		datosPatrones = new ArrayList<Patron>();
		cargarPredeterminados();
	}

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static PatronesDAO getInstancia() {
		if (instancia == null) {
			instancia = new PatronesDAO();
		}
		return instancia;
	}

	/**
	 *  Método para generar datos predeterminados.
	 */
	private void cargarPredeterminados() {
		byte[][] esquemaDemo =  new byte[][]{ 
			{ 0, 0, 0, 0 }, 
			{ 1, 0, 1, 0 }, 
			{ 0, 0, 0, 1 }, 
			{ 0, 1, 1, 1 }, 
			{ 0, 0, 0, 0 }
		};
		Patron patronDemo = new Patron("PatronDemo", esquemaDemo);
		datosPatrones.add(patronDemo);
	}

	//OPERACIONES DAO
	/**
	 * Búsqueda binaria de un Patron dado su nombre.
	 * @param nombre - el nombre del Patron a buscar.
	 * @return - el Patron encontrado.
	 * @throws DatosException - si no existe.
	 */	
	@Override
	public Patron obtener(String nombre) throws DatosException {
		if (nombre != null) {
			int posicion = obtenerPosicion(nombre);				// En base 1
			if (posicion >= 0) {
				return datosPatrones.get(posicion - 1);     	// En base 0
			}
			else {
				throw new DatosException("Obtener: "+ nombre + " no existe");
			}
		}
		return null;
	}

	/**
	 *  Obtiene por búsqueda binaria, la posición que ocupa, o ocuparía,  un Patron en 
	 *  la estructura.
	 *	@param nombre - id de Patron a buscar.
	 *	@return - la posición, en base 1, que ocupa un objeto o la que ocuparía (negativo).
	 */
	private int obtenerPosicion(String nombre) {
		int comparacion;
		int inicio = 0;
		int fin = datosPatrones.size() - 1;
		int medio = 0;
		while (inicio <= fin) {
			medio = (inicio + fin) / 2;			// Calcula posición central.
			// Obtiene > 0 si nombre va después que medio.
			comparacion = nombre.compareTo(datosPatrones.get(medio).getNombre());
			if (comparacion == 0) {			
				return medio + 1;   			// Posción ocupada, base 1	  
			}		
			if (comparacion > 0) {
				inicio = medio + 1;
			}			
			else {
				fin = medio - 1;
			}
		}	
		return -(inicio + 1);					// Posición que ocuparía -negativo- base 1
	}

	/**
	 * Búsqueda de Patron dado un objeto, reenvía al método que utiliza nombre.
	 * @param obj - el Patron a buscar.
	 * @return - el Patron encontrado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Patron obtener(Object obj) throws DatosException  {
		return this.obtener(((Patron) obj).getNombre());
	}

	/**
	 *  Alta de un nuevo Patron en orden y sin repeticiones según el campo nombre. 
	 *  Busca previamente la posición que le corresponde por búsqueda binaria.
	 * @param obj - Patron a almacenar.
	 * @throws DatosException - si ya existe.
	 */
	@Override
	public void alta(Object obj) throws DatosException  {
		assert obj != null;
		Patron patronNuevo = (Patron) obj;										// Para conversión cast
		int posicionInsercion = obtenerPosicion(patronNuevo.getNombre()); 
		if (posicionInsercion < 0) {
			datosPatrones.add(-posicionInsercion - 1, patronNuevo); 			// Inserta la sesión en orden.
		}
		else {
			throw new DatosException("Alta: "+ patronNuevo.getNombre() + " ya existe");
		}
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param nombre - el nombre del Patron a eliminar.
	 * @return - el Patron eliminado. 
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Patron baja(String nombre) throws DatosException  {
		assert (nombre != null);
		int posicion = obtenerPosicion(nombre); 									// En base 1
		if (posicion > 0) {
			return datosPatrones.remove(posicion - 1); 								// En base 0
		}
		else {
			throw new DatosException("Baja: "+ nombre + " no existe");
		}
	}

	/**
	 *  Actualiza datos de un Mundo reemplazando el almacenado por el recibido.
	 *	@param obj - Patron con las modificaciones.
	 *  @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException  {
		assert obj != null;
		Patron patronActualizado = (Patron) obj;									// Para conversión cast
		int posicion = obtenerPosicion(patronActualizado.getNombre()); 				// En base 1
		if (posicion > 0) {
			// Reemplaza elemento
			datosPatrones.set(posicion - 1, patronActualizado);  					// En base 0	
		}
		else {
			throw new DatosException("Actualizar: "+ patronActualizado.getNombre() + " no existe");
		}
	}

	/**
	 * Obtiene el listado de todos los objetos Patron almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		for (Patron patron: datosPatrones) {
			if (patron != null) {
				listado.append("\n" + patron); 
			}
		}
		return listado.toString();
	}

	/**
	 * Elimina todos los patrones almacenados y regenera el demo predeterminado.
	 */
	@Override
	public void borrarTodo() {
		datosPatrones.clear();
		cargarPredeterminados();
	}

	/**
	 *  Cierra almacenes de datos.
	 */
	@Override
	public void cerrar() {
		// Nada que hacer si no hay persistencia.	
	}
	
} //class