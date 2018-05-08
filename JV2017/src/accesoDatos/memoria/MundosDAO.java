/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Mundo utilizando un ArrayList.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: MundosDAO.java 
 * @version: 2.0 - 2018/04/20
 * @author: ajp
 */

package accesoDatos.memoria;

import java.util.ArrayList;
import java.util.Hashtable;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.Mundo;
import modelo.Patron;
import modelo.Posicion;

public class MundosDAO implements OperacionesDAO {
	
	// Requerido por el patrón Singleton
	private static MundosDAO instancia;

	// Elementos de almacenamiento.
	private static ArrayList<Mundo> datosMundos;

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private MundosDAO() {
		datosMundos = new ArrayList<Mundo>();
		cargarPredeterminados();
	}

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static MundosDAO getInstancia() {
		if (instancia == null) {
			instancia = new MundosDAO();
		}
		return instancia;
	}

	/**
	 *  Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		// En este array los 0 indican celdas con célula muerta y los 1 vivas
		byte[][] espacioDemo =  new byte[][]{ 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0 }, //
			{ 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // Given:
			{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Planeador
			{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Flip-Flop
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }  // 1x Still Life
		};
		Mundo mundoDemo = new Mundo("MundoDemo", new ArrayList<Integer>(), new Hashtable<Patron,Posicion>());
		datosMundos.add(mundoDemo);
	}
	
	//OPERACIONES DAO
	/**
	 * Obtiene el objeto dado el id utilizado para el almacenamiento.
	 * @param nombre - id del mundo a obtener.
	 * @return - el Mundo encontrado.
	 * @throws DatosException - si no existe.
	 */	
	@Override
	public Mundo obtener(String nombre) throws DatosException {
		if (nombre != null) {
			int posicion = obtenerPosicion(nombre);				// En base 1
			if (posicion >= 0) {
				return datosMundos.get(posicion - 1);     		// En base 0
			}
			else {
				throw new DatosException("Obtener: "+ nombre + " no existe");
			}
		}
		return null;
	}
	
	/**
	 *  Obtiene por búsqueda binaria, la posición que ocupa, o ocuparía,  un Mundo en 
	 *  la estructura.
	 *	@param nombre - id de Mundo a buscar.
	 *	@return - la posición, en base 1, que ocupa un objeto o la que ocuparía (negativo).
	 */
	private int obtenerPosicion(String nombre) {
		int comparacion;
		int inicio = 0;
		int fin = datosMundos.size() - 1;
		int medio = 0;
		while (inicio <= fin) {
			medio = (inicio + fin) / 2;			// Calcula posición central.
			// Obtiene > 0 si nombre va después que medio.
			comparacion = nombre.compareTo(datosMundos.get(medio).getNombre());
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
	 * Búsqueda de Mundo dado un objeto, reenvía al método que utiliza nombre.
	 * @param obj - el Mundo a buscar.
	 * @return - el Mundo encontrado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Mundo obtener(Object obj) throws DatosException  {
		return this.obtener(((Mundo) obj).getNombre());
	}
	
	/**
	 *  Alta de un objeto en el almacén de datos, 
	 *  sin repeticiones, según el campo id previsto. 
	 *	@param obj - Objeto a almacenar.
	 * @throws DatosException - si ya existe.
	 */
	@Override
	public void alta(Object obj) throws DatosException  {
		assert obj != null;
		Mundo mundoNuevo = (Mundo) obj;										// Para conversión cast
		int posicionInsercion = obtenerPosicion(mundoNuevo.getNombre()); 
		if (posicionInsercion < 0) {
			datosMundos.add(-posicionInsercion - 1, mundoNuevo); 			// Inserta la sesión en orden.
		}
		else {
			throw new DatosException("Alta: "+ mundoNuevo.getNombre() + " ya existe");
		}
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param nombre - el nombre del Mundo a eliminar.
	 * @return - el Mundo eliminado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Mundo baja(String nombre) throws DatosException  {
		assert (nombre != null);
		int posicion = obtenerPosicion(nombre); 									// En base 1
		if (posicion > 0) {
			return datosMundos.remove(posicion - 1); 								// En base 0
		}
		else {
			throw new DatosException("Baja: "+ nombre + " no existe");
		}
	}

	/**
	 *  Actualiza datos de un Mundo reemplazando el almacenado por el recibido.
	 *	@param obj - Mundo con las modificaciones.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException  {
		assert obj != null;
		Mundo mundoActualizado = (Mundo) obj;										// Para conversión cast
		int posicion = obtenerPosicion(mundoActualizado.getNombre()); 				// En base 1
		if (posicion > 0) {
			// Reemplaza elemento
			datosMundos.set(posicion - 1, mundoActualizado);  						// En base 0		
		}
		else {
			throw new DatosException("Actualizar: "+ mundoActualizado.getNombre() + " no existe");
		}
	}

	/**
	 * Obtiene el listado de todos los objetos Mundo almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		for (Mundo mundo: datosMundos) {
			if (mundo != null) {
				listado.append("\n" + mundo);
			}
		}
		return listado.toString();
	}

	/**
	 * Elimina todos los mundos almacenados y regenera el demo predeterminado.
	 */
	@Override
	public void borrarTodo() {
		datosMundos.clear();
		cargarPredeterminados();	
	}

	/**
	 *  Cierra almacenes de datos.
	 */
	@Override
	public void cerrar() {
		// Nada que hacer si no hay persistencia.	
	}
	
} // class