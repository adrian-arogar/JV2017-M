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

		// Usuario usrPredeterminado = null;
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
	 * @param id
	 *            - el id de Usuario a buscar.
	 * @return el Usuario encontrado.
	 * @throws DatosException
	 *             si no existe.
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

	@Override
	public Object obtener(Object obj) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alta(Object obj) throws DatosException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object baja(String id) throws DatosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(Object obj) throws DatosException {
		// TODO Auto-generated method stub

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