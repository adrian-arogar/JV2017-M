/** 
 * Proyecto: Juego de la vida.
 * Resuelve todos los aspectos del almacenamiento del DTO Usuario 
 * utilizando un Map - Hashtable.
 * Colabora en el patron Fachada.
 * @since: prototipo2.1
 * @source: UsuariosDAO.java 
 * @version: 2.1 - 23/05/2018
 * @author: Grupo 2 ->
 * 					- Adrián Sánchez Orcajada
 * 					- Adrián Aroca García
 */

package accesoDatos.db4o;

import java.util.Hashtable;
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

public class UsuariosDAO implements OperacionesDAO {

	// Requerido por el Singleton
	private static UsuariosDAO instancia = null;

	// Elementos de almacenamiento - Base de datos db4o
	private ObjectContainer db;

	/**
	 * Constructor por defecto de uso interno que solo se ejecutará una vez
	 */
	private UsuariosDAO() {

		db = Conexion.getDB();
		db.store(new Hashtable<String, String>());
		try {
			obtener("AAA0T");
			obtener("III1R");
		} catch (DatosException e) {
			cargarPredeterminados();
		}
	}

	/**
	 * Método estático de acceso a la instancia única. Si no existe la crea
	 * invocando al constructor interno. Utiliza inicializción diferida. Sólo se
	 * crea una vez; instancia única -patrón singleton-
	 * 
	 * @return instancia
	 */
	public static UsuariosDAO getInstancia() {
		if (instancia == null) {
			instancia = new UsuariosDAO();
		}
		return instancia;
	}

	/**
	 * Método para generar datos predeterminados
	 */
	private void cargarPredeterminados() {
		try {
			String nombreUsr = Configuracion.get().getProperty("usuario.admin");
			String password = "Miau#0";
			Configuracion.get().getProperty("usuario.passwordPredeterminada");
			Usuario usrPredeterminado = new Usuario(new Nif("00000000T"), nombreUsr, "Admin Admin",
					new DireccionPostal("Iglesia", "0", "30012", "Murcia"), new Correo("jv.admin" + "@gmail.com"),
					new Fecha(2000, 01, 01), new Fecha(), new ClaveAcceso(password), RolUsuario.ADMINISTRADOR);
			alta(usrPredeterminado);
		} catch (DatosException | ModeloException e) {
			e.printStackTrace();
		}
	}

	// Operaciones DAO
	/**
	 * Búsqueda de usuario dado su idUsr, el correo o su nif.
	 * 
	 * @param id - el id de Usuario a buscar.
	 * @return el Usuario encontrado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Usuario obtener(String id) throws DatosException {

		id = obtenerEquivalencia(id);

		Query consulta = db.query();

		consulta.constrain(Usuario.class);
		consulta.descend("idUsr").constrain(id).equal();

		// Resultado
		ObjectSet<Usuario> resultado = consulta.execute();

		if (resultado.size() > 0) {
			return resultado.get(0);
		} else {
			throw new DatosException("Obtener: " + id + "No existe");
		}
	}

	// Obtiene el id de usuario
	public String obtenerEquivalencia(String id) {
		return obtenerMapaEquivalencias().get(id);
	}

	/**
	 * Obtiene el mapa de equivalencias de id para idUsr.
	 * 
	 * @return el Hashtable almacenado
	 */
	private Map<String, String> obtenerMapaEquivalencias() {
		// Obtiene mapa de equivalencias de id de acceso
		Query consulta = db.query();
		consulta.constrain(Hashtable.class);
		ObjectSet<Hashtable<String, String>> result = consulta.execute();
		return result.get(0);
	}

	/**
	 * Búsqueda de Usuario dado un objeto, reenvía al método que utiliza idUsr.
	 * @param obj - el Usuario a buscar.
	 * @return - el Usuario encontrado.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Object obtener(Object obj) throws DatosException {
		return this.obtener(((Usuario) obj).getIdUsr());
	}

	/**
	 *  Alta de un nuevo usuario en orden y sin repeticiones según el campo idUsr. 
	 *  Localiza previamente la posición de inserción, en orden, que le corresponde.
	 *	@param obj - Objeto a almacenar.
	 *  @throws DatosException si ya existe o no puede generar variante de idUsr.
	 */
	@Override
	public void alta(Object obj) throws DatosException {
		assert obj!= null;
		Usuario usrNuevo = (Usuario) obj;							// Para conversión cast
		Usuario usrPrevio = null;
		try {
			usrPrevio = obtener(usrNuevo.getIdUsr());  //comprobar si existe
		}
		catch (DatosException e) {
			db.store(usrNuevo);		//store guarda objetos repetidos
			registrarEquivalenciaId(usrNuevo);
			return;
		}
		try {
			generarVarianteIdUsr(usrNuevo, usrPrevio);
			db.store(usrNuevo); //Si no falla almacena
			registrarEquivalenciaId(usrNuevo);
		}
		catch (DatosException e) {
			throw new DatosException("Alta: " + usrNuevo.getIdUsr() + " ya existe y es " + usrPrevio.getIdUsr());
		}
	}

	/**
	 *  Si hay coincidencia de identificador hace 23 intentos de variar la última letra
	 *  procedente del NIF. Llama al generarVarianteIdUsr() de la clase Usuario.
	 * @param usrNuevo
	 * @param usrPrevio
	 * @throws DatosException
	 */
	private void generarVarianteIdUsr(Usuario usrNuevo, Usuario usrPrevio) throws DatosException {
		// Comprueba que no haya coincidencia de Correo y Nif (ya existe)
		boolean condicion = !(usrNuevo.getCorreo().equals(usrPrevio.getCorreo())
				|| usrNuevo.getNif().equals(usrPrevio.getNif()));
		if (condicion) {
			int intentos = "TRWAGMYFPDXBNJZSQVHLCKE".length();				// 24 letras
			do {
				try {
					usrNuevo.generarVarianteIdUsr();
					usrPrevio = obtener(usrNuevo.getIdUsr());
				}
				catch (DatosException e){
					return;
				}
				intentos--;
			} while (intentos > 0);
			throw new DatosException("Variar idUsr: " + usrNuevo.getIdUsr() + " imposible generar variante.");
		}
		throw new DatosException("Alta: " + usrNuevo.getIdUsr() + " usuario ya existe.");
	}


	/**
	 *  Añade nif y correo como equivalencias de idUsr para el inicio de sesión. 
	 *	@param usr - Usuario a registrar equivalencias. 
	 */
	private void registrarEquivalenciaId(Usuario usr) {
		//obtiene mapa
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Registrar

		mapaEquivalencias.put(usr.getIdUsr(), usr.getIdUsr());
		mapaEquivalencias.put(usr.getNif().getTexto(), usr.getIdUsr());
		mapaEquivalencias.put(usr.getCorreo().getTexto(), usr.getIdUsr());

		db.store(mapaEquivalencias);
	}

	/**
	 * Elimina el objeto, dado el id utilizado para el almacenamiento.
	 * @param idUsr - el identificador del objeto a eliminar.
	 * @return - el Objeto eliminado. 
	 * @throws DatosException - si no existe.
	 */
	@Override
	public Usuario baja(String id) throws DatosException {
		assert (id != null);
		assert (id !="");
		assert (id != " ");
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
	 *  Elimina nif y correo como equivalencias de idUsr. 
	 *	@param usr - Usuario a eliminar equivalencias. 
	 */
	private void borrarEquivalenciaId(Usuario usr) {
		//obtiene mapa
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//Registrar
		mapaEquivalencias.remove(usr.getIdUsr());
		mapaEquivalencias.remove(usr.getNif().getTexto());
		mapaEquivalencias.remove(usr.getCorreo().getTexto());
		db.store(mapaEquivalencias);
	}

	/**
	 *  Actualiza datos de un Usuario reemplazando el almacenado por el recibido. 
	 *  No admitirá cambios en el idUsr.
	 *	@param obj - Usuario con los cambios.
	 * @throws DatosException - si no existe.
	 */
	@Override
	public void actualizar(Object obj) throws DatosException {
		assert obj != null;
		Usuario usrActualizado = (Usuario) obj;							// Para conversión cast
		Usuario usrPrevio = null;
		try {
			usrPrevio = (Usuario) obtener (usrActualizado.getIdUsr());
			cambiarEquivalenciaId(usrPrevio,usrActualizado);
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
		catch(DatosException e) {
			throw new DatosException("actualizar: "+ usrActualizado.getIdUsr() + " no existe");
		}
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Modifica o reemplaza nif y correo como equivalencias de idUsr. 
	 *	@param usrAntiguo - Usuario a modificar equivalencias. 
	 *	@param usrNuevo   - Usuario del que se extraen el valor de las modificaciones
	 */
	private void cambiarEquivalenciaId(Usuario usrAntiguo, Usuario usrNuevo) {
		//obtiene mapa
		Map<String,String> mapaEquivalencias = obtenerMapaEquivalencias();
		//cambiar

		mapaEquivalencias.replace(usrAntiguo.getIdUsr(), usrNuevo.getIdUsr().toUpperCase());
		mapaEquivalencias.replace(usrAntiguo.getNif().getTexto(), usrNuevo.getIdUsr().toUpperCase());
		mapaEquivalencias.replace(usrAntiguo.getCorreo().getTexto(), usrNuevo.getIdUsr().toUpperCase());
		//actualiza
		db.store(mapaEquivalencias);
	}

	@Override
	public String listarDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarTodo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cerrar() {
		// TODO Auto-generated method stub

	}

} // class
