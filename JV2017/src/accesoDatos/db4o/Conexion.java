/** 
 * Proyecto: Juego de la vida.
 *  Establece acceso a base de datos OO db4o
 *  @since: Prototipo2.1
 *  @source: Conexion.java 
 *  @version: 2.1 - 2018.05.07
 *  @author: ajp
 */

package accesoDatos.db4o;

import java.util.Calendar;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import config.Configuracion;

public class Conexion {
	
	// Singleton
	private static ObjectContainer db;

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static ObjectContainer getDB() {
		if (db == null) { 
			new Conexion();
		}
		return db;
	}
	
	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private Conexion() {
		initConexion();
	}
 
	/**
	 * Configura la conexión.
	 */
	private void initConexion() {
		final String PATH = Configuracion.get().getProperty("db4o.nombreFicheroDB");
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(Calendar.class).callConstructor(true);
		db = Db4oEmbedded.openFile(config, PATH);	
		
		//ServerConfiguration serverConfig = Db4oClientServer.newServerConfiguration();
		//Db4oClientServer.openServer(serverConfig, filename, PORT)
		//ClientConfiguration clientConfig = Db4oClientServer.newClientConfiguration();
		//Db4oClientServer.openClient(clientConfig, HOST, PORT, USER, PASS);
	}

	/**
	 * Cierra la conexión.
	 */
	public static void cerrarConexiones() {
		db.close();
	}
}