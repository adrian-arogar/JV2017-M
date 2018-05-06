/** 
 * Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos del almacenamiento del DTO Simulacion utilizando un ArrayList.
 *  Colabora en el patron Fachada.
 *  @since: prototipo2.0
 *  @source: SimulacionesDAO.java 
 *  @version: 2.0 - 2018/04/20 
 *  @author: ajp
 */

package accesoDatos.memoria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ModeloException;
import modelo.Mundo;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import modelo.Usuario;
import util.Fecha;

public class SimulacionesDAO implements OperacionesDAO {

	// Requerido por el Singleton 
	private static SimulacionesDAO instancia;

	// Elemento de almacenamiento.
	private static ArrayList<Simulacion> datosSimulaciones;

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private SimulacionesDAO() {
		datosSimulaciones = new ArrayList<Simulacion>();
		cargarPredeterminados();
	}

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 * @throws DatosException 
	 */
	public static SimulacionesDAO getInstancia() throws DatosException {
		if (instancia == null) {
			instancia = new SimulacionesDAO();
		}
		return instancia;
	}

	/**
	 *  Método para generar de datos predeterminados. 
	 */
	private void cargarPredeterminados() {
		// Obtiene usuario (invitado) y mundo predeterminados.
		Usuario usrDemo = null;
		try {
			usrDemo = UsuariosDAO.getInstancia().obtener("III1R");
			Mundo mundoDemo = MundosDAO.getInstancia().obtener("MundoDemo");
			Simulacion simulacionDemo = new Simulacion(usrDemo, new Fecha(), mundoDemo, EstadoSimulacion.PREPARADA);
			datosSimulaciones.add(simulacionDemo);
		} catch (DatosException e) {
			e.printStackTrace();
		}
	}
	
	// OPERACIONES DAO
	/**
	 * Búsqueda de Simulacion dado idUsr y fecha.
	 * @param idSimulacion - el idUsr+fecha de la Simulacion a buscar. 
	 * @return - la Simulacion encontrada. 
	 * @throws DatosException - si no existe.
	 */	
	@Override
	public Simulacion obtener(String idSimulacion) throws DatosException {
		if (idSimulacion != null) {
			int posicion = obtenerPosicion(idSimulacion);			// En base 1
			if (posicion >= 0) {
				return datosSimulaciones.get(posicion - 1);     	// En base 0
			}
			else {
				throw new DatosException("Obtener: "+ idSimulacion + " no existe");
			}
		}
		return null;
	}

	/**
	 *  Obtiene por búsqueda binaria, la posición que ocupa, o ocuparía,  una simulación en 
	 *  la estructura.
	 *	@param idSimulacion - id de Simulacion a buscar.
	 *	@return - la posición, en base 1, que ocupa un objeto o la que ocuparía (negativo).
	 */
	private int obtenerPosicion(String idSimulacion) {
		int comparacion;
		int inicio = 0;
		int fin = datosSimulaciones.size() - 1;
		int medio = 0;
		while (inicio <= fin) {
			medio = (inicio + fin) / 2;			// Calcula posición central.
			// Obtiene > 0 si idSimulacion va después que medio.
			comparacion = idSimulacion.compareTo(datosSimulaciones.get(medio).getIdSimulacion());
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
	 * Búsqueda de simulacion dado un objeto, reenvía al método que utiliza idSimulacion.
	 * @param obj - la Simulacion a buscar.
	 * @return - la Simulacion encontrada.
	 * @throws DatosException - si no existe.
	 */
	public Simulacion obtener(Object obj) throws DatosException  {
		return this.obtener(((Simulacion) obj).getIdSimulacion());
	}

	/**
	 * Búsqueda de todas la simulaciones de un usuario.
	 * @param idUsr - el identificador de usuario a buscar.
	 * @return - Sublista con las simulaciones encontrada; null si no existe ninguna.
	 * @throws ModeloException 
	 * @throws DatosException 
	 */
	public List<Simulacion> obtenerTodasMismoUsr(String idUsr) throws ModeloException, DatosException {
		Simulacion aux = null;
		aux = new Simulacion();
		aux.setUsr(UsuariosDAO.getInstancia().obtener(idUsr));
		//Busca posición inserción ordenada por idUsr + fecha. La última para el mismo usuario.
		return separarSimulacionesUsr(obtenerPosicion(aux.getIdSimulacion()) - 1);
	}

	/**
	 * Separa en una lista independiente todas la simulaciones de un mismo usuario.
	 * @param ultima - el indice de la última simulación ya encontrada.
	 * @return - Sublista con las simulaciones encontrada; null si no existe ninguna.
	 */
	private List<Simulacion> separarSimulacionesUsr(int ultima) {
		// Localiza primera simulación del mismo usuario.
		String idUsr = datosSimulaciones.get(ultima).getUsr().getIdUsr();
		int primera = ultima;
		for (int i = ultima; i >= 0 && datosSimulaciones.get(i).getUsr().getIdUsr().equals(idUsr); i--) {
			primera = i;
		}
		// devuelve la sublista de simulaciones buscadas.
		return datosSimulaciones.subList(primera, ultima+1);
	}

	/**
	 *  Alta de una nueva Simulacion en orden y sin repeticiones según los idUsr más fecha. 
	 *  Busca previamente la posición que le corresponde por búsqueda binaria.
	 *  @param obj - Simulación a almacenar.
	 * @throws DatosException - si ya existe.
	 */	
	public void alta(Object obj) throws DatosException  {
		assert obj != null;
		Simulacion simulNueva = (Simulacion) obj;								// Para conversión cast
		int posicionInsercion = obtenerPosicion(simulNueva.getIdSimulacion()); 
		if (posicionInsercion < 0) {
			datosSimulaciones.add(-posicionInsercion - 1, simulNueva); 			// Inserta la simulación en orden.
		}
		else {
			throw new DatosException("Alta: "+ simulNueva.getIdSimulacion() + " ya existe");
		}
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param idSimulacion - identificador de la Simulacion a eliminar.
	 * @return - la Simulacion eliminada. 
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Simulacion baja(String idSimulacion) throws DatosException  {
		assert (idSimulacion != null);
		int posicion = obtenerPosicion(idSimulacion); 							// En base 1
		if (posicion > 0) {
			return datosSimulaciones.remove(posicion - 1); 						// En base 0
		}
		else {
			throw new DatosException("Baja: "+ idSimulacion + " no existe");
		}
	}

	/**
	 *  Actualiza datos de una Simulacion reemplazando el almacenado por el recibido.
	 *  No admitirá cambios en usr ni en la fecha.
	 *	@param obj - Patron con las modificaciones.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException  {
		assert obj != null;
		Simulacion simulActualizada = (Simulacion) obj;							// Para conversión cast
		int posicion = obtenerPosicion(simulActualizada.getIdSimulacion()); 	// En base 1
		if (posicion > 0) {
			// Reemplaza elemento
			datosSimulaciones.set(posicion - 1, simulActualizada);  			// En base 0		
		}
		else {
			throw new DatosException("Actualizar: "+ simulActualizada.getIdSimulacion() + "no existe");
		}
	}

	/**
	 * Obtiene el listado de todos las simulaciones almacenadas.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		for (Simulacion simulacion: datosSimulaciones) {
			if (simulacion != null) {
				listado.append("\n" + simulacion);
			}
		}
		return listado.toString();
	}

	/**
	 * Elimina todos las simulaciones almacenadas y regenera la demo predeterminada.
	 */
	@Override
	public void borrarTodo() {
		datosSimulaciones.clear();
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
