/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Usuario 
 * utilizando acceso a base de datos db4o.
 * Colabora en el patron Fachada.
 * @since: prototipo2.0
 * @source: UsuariosDAO.java 
 * @version: 2.1 - 2018/05/11 
 * @author: ajp
 */

package accesoDatos.db4o;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import config.Configuracion;
import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class UsuariosDAO  implements OperacionesDAO {

	// Requerido por el Singleton. 
	private static UsuariosDAO instancia = null;

	// Elemento de almacenamiento.
	// Base datos db4o
	private ObjectContainer db;

	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private UsuariosDAO() {
		db = Conexion.getDB();
		db.store(new Hashtable <String,String>());
		try {
			obtener("AAA0T");
			obtener("III1R");	
		} 
		catch (DatosException e) {
			cargarPredeterminados();
		}
	}

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static UsuariosDAO getInstancia() {
		if (instancia == null) {
			instancia = new UsuariosDAO();
		}
		return instancia;
	}

	/**
	 *  Método para generar de datos predeterminados.
	 */
	private void cargarPredeterminados() {
		try {
			String nombreUsr = Configuracion.get().getProperty("usuario.admin");
			String password = Configuracion.get().getProperty("usuario.passwordPredeterminada");	
			Usuario usrPredeterminado = new Usuario(new Nif("00000000T"), nombreUsr, "Admin Admin", 
					new DireccionPostal("Iglesia", "00", "30012", "Murcia"), 
					new Correo("jv.admin" + "@gmail.com"), new Fecha(2000, 01, 01), 
					new Fecha(), new ClaveAcceso(password), RolUsuario.ADMINISTRADOR);
			alta(usrPredeterminado);

			nombreUsr = Configuracion.get().getProperty("usuario.invitado");
			usrPredeterminado = new Usuario(new Nif("00000001R"), nombreUsr, "Invitado Invitado", 
					new DireccionPostal("Iglesia", "00", "30012", "Murcia"), 
					new Correo("jv.invitado" + "@gmail.com"), new Fecha(2000, 01, 01), 
					new Fecha(), new ClaveAcceso(password), RolUsuario.INVITADO);
			alta(usrPredeterminado);
		} 
		catch (DatosException | ModeloException e) {
		}
	}

	/**
	 *  Cierra conexión.
	 */
	@Override
	public void cerrar() {
		// No hay que cerrar nada si la conexión es compartida por todos los DAO.
	}

	//OPERACIONES DAO
	/**
	 * Búsqueda de usuario dado su idUsr, el correo o su nif.
	 * @param id - el id de Usuario a buscar.
	 * @return - el Usuario encontrado. 
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Usuario obtener(String id) throws DatosException {
		id = obtenerEquivalencia(id);
		Query consulta = db.query();
		ObjectSet <Usuario> result;
		consulta.constrain(Usuario.class);
		consulta.descend("idUsr").constrain(id);
		result = consulta.execute();
		if (result.size() > 0) {
			return result.get(0);
		}	
		else {
			throw new DatosException("Obtener: "+ id + " no existe");
		}				
	}

	/**
	 * Obtiene todos los usuarios almacenados.
	 * @return - la List con todos los usuarios.
	 */
	public List <Usuario> obtenerTodos() {
		Query consulta = db.query();
		consulta.constrain(Usuario.class);
		return consulta.execute();
	}

	/**
	 * Búsqueda de Usuario dado un objeto, reenvía al método que utiliza idUsr.
	 * @param obj - el Usuario a buscar.
	 * @return - el Usuario encontrado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Usuario obtener(Object obj) throws DatosException  {
		return this.obtener(((Usuario) obj).getIdUsr());
	}	

	/**
	 *  Alta de un nuevo usuario en orden y sin repeticiones según el campo idUsr. 
	 *  Localiza previamente la posición de inserción, en orden, que le corresponde.
	 *	@param obj - Objeto a almacenar.
	 *  @throws DatosException si ya existe o no puede generar variante de idUsr.
	 */
	@Override
	public void alta(Object obj) throws DatosException  {
		Usuario usrNuevo = (Usuario) obj;
		Usuario usrPrevio = null;
		try {
			usrPrevio = obtener(usrNuevo.getIdUsr());
		}
		catch (DatosException e) {
			db.store(usrNuevo);
			registrarEquivalenciaId(usrNuevo);
			return;
		}
		generarVarianteIdUsr(usrNuevo, usrPrevio);
	}

	/**
	 * Genera variante de IdUsr cuando se produce coincidencia de identificador 
	 * con un usuario ya almacenado. 
	 * @param usrNuevo
	 * @param usrPrevio
	 * @throws DatosException si ya existe o no puede generar variante de idUsr.
	 */
	private void generarVarianteIdUsr(Usuario usrNuevo, Usuario usrPrevio) throws DatosException {
		// Comprueba que no haya coincidencia de Correo y Nif (ya existe)
		boolean condicion = !(usrNuevo.getCorreo().equals(usrPrevio.getCorreo())
				|| usrNuevo.getNif().equals(usrPrevio.getNif()));
		if (condicion) {
			int intentos = "ABCDEFGHJKLMNPQRSTUVWXYZ".length();				// 24 letras
			do {
				usrNuevo.generarVarianteIdUsr();
				usrPrevio = obtener(usrNuevo.getIdUsr());
				if (usrPrevio == null) {
					db.store(usrNuevo);
					registrarEquivalenciaId(usrNuevo);
					return;
				}
				intentos--;
			} while (intentos > 0);
			throw new DatosException("Variar idUsr: " + usrNuevo.getIdUsr() + " imposible generar variante.");
		}
		throw new DatosException("(ALTA) El Usuario: " + usrNuevo.getIdUsr() + " ya existe...");
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param id - el identificador del objeto a eliminar.
	 * @return - el Objeto eliminado. 
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Object baja(String id) throws DatosException {
		assert (id != null);
		try {
			Usuario usr = obtener(id);
			borrarEquivalenciaId(usr);
			db.delete(usr);
			return usr;
		}
		catch (DatosException e) {
			throw new DatosException("Baja: "+ id + " no existe");
		}
	} 

	/**
	 *  Actualiza datos de un Usuario reemplazando el almacenado por el recibido. 
	 *  No admitirá cambios en el idUsr.
	 *	@param obj - Usuario con los cambios.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException  {
		Usuario usrActualizado = (Usuario) obj;
		Usuario usrPrevio = null;
		try {
			usrPrevio = (Usuario) obtener(usrActualizado.getIdUsr());
			cambiarEquivalenciaId(usrPrevio, usrActualizado);
			usrPrevio.setNif(usrActualizado.getNif());
			usrPrevio.setNombre(usrActualizado.getNombre());
			usrPrevio.setApellidos(usrActualizado.getApellidos());
			usrPrevio.setDomicilio(usrActualizado.getDomicilio());
			usrPrevio.setCorreo(usrActualizado.getCorreo());
			usrPrevio.setFechaNacimiento(usrActualizado.getFechaNacimiento());
			usrPrevio.setFechaAlta(usrActualizado.getFechaAlta());
			usrPrevio.setRol(usrActualizado.getRol());
			db.store(usrPrevio);
		} 
		catch (DatosException e) {
			throw new DatosException("actualizar: "+ usrActualizado.getIdUsr() + " no existe");
		}
		catch (ModeloException e) {
			e.printStackTrace();
		}
	} 

	/**
	 * Obtiene el listado de todos los usuarios almacenados.
	 * @return el texto con el volcado de datos.
	 */
	@Override
	public String listarDatos() {
		StringBuilder listado = new StringBuilder();
		for (Usuario usr: obtenerTodos()) {
			listado.append("\n" + usr);
		}
		return listado.toString();
	}

	/**
	 * Elimina todos los usuarios almacenados y regenera los predeterminados.
	 */
	@Override
	public void borrarTodo() {
		// Elimina cada uno de los obtenidos
		for (Usuario usr: obtenerTodos()) {
			db.delete(usr);
		}
		// Quita todas las equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		mapaEquivalencias.clear();
		db.store(mapaEquivalencias);
		cargarPredeterminados();
	}

	//GESTION equivalencias id
	/**
	 * Obtiene el idUsr usado internamente a partir de otro equivalente.
	 * @param id - la clave alternativa. 
	 * @return - El idUsr equivalente.
	 */
	public String obtenerEquivalencia(String id) {
		return obtenerMapaEquivalencias().get(id);
	}

	/**
	 * Obtiene el mapa de equivalencias de id para idUsr.
	 * @return el Hashtable almacenado.
	 */
	private Map <String, String> obtenerMapaEquivalencias() {
		//Obtiene mapa de equivalencias de id de acceso
		Query consulta = db.query();
		consulta.constrain(Hashtable.class);
		ObjectSet <Hashtable <String,String>> result = consulta.execute();
		return result.get(0);	
	}

	/**
	 * Registra las equivalencias de nif y correo para un idUsr.
	 * @param usuario
	 */
	private void registrarEquivalenciaId(Usuario usuario) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Registra equivalencias 
		mapaEquivalencias.put(usuario.getIdUsr(), usuario.getIdUsr());
		mapaEquivalencias.put(usuario.getNif().getTexto(), usuario.getIdUsr());
		mapaEquivalencias.put(usuario.getCorreo().getTexto(), usuario.getIdUsr());
		//actualiza datos
		db.store(mapaEquivalencias);	
	}

	/**
	 * Elimina las equivalencias de nif y correo para un idUsr.
	 * @param usuario - el usuario para eliminar sus equivalencias de idUsr.
	 */
	private void borrarEquivalenciaId(Usuario usuario) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Borra equivalencias 
		mapaEquivalencias.remove(usuario.getIdUsr());
		mapaEquivalencias.remove(usuario.getNif().getTexto());
		mapaEquivalencias.remove(usuario.getCorreo().getTexto());
		//actualiza datos
		db.store(mapaEquivalencias);	
	}

	/**
	 * Actualiza las equivalencias de nif y correo para un idUsr
	 * @param usrAntiguo - usuario con id's antiguos
	 * @param usrNuevo - usuario con id's nuevos
	 */
	private void cambiarEquivalenciaId(Usuario usrAntiguo, Usuario usrNuevo) {
		//Obtiene mapa de equivalencias
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Cambia equivalencias 
		mapaEquivalencias.replace(usrAntiguo.getIdUsr(), usrNuevo.getIdUsr().toUpperCase());
		mapaEquivalencias.replace(usrAntiguo.getNif().getTexto(), usrNuevo.getIdUsr().toUpperCase());
		mapaEquivalencias.replace(usrAntiguo.getCorreo().getTexto(), usrNuevo.getIdUsr().toUpperCase());
		//actualiza datos
		db.store(mapaEquivalencias);
	}

} //class