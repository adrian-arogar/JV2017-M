/** 
 * Proyecto: Juego de la vida.
 *  Organiza y gestiona la configuración de la aplicación.
 *  Utiliza Properties para organizar y almacenar la configuración.
 *  @since: prototipo2.0
 *  @source: Configuracion.java 
 *  @version: 2.0 - 2018/04/20 
 *  @author: ajp
 */

package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracion {

	// Singleton
	private static Properties configuracion;

	/**
	 *  Método estático de acceso a la instancia única.
	 *  Si no existe la crea invocando al constructor interno.
	 *  Utiliza inicialización diferida.
	 *  Sólo se crea una vez; instancia única -patrón singleton-
	 *  @return instancia
	 */
	public static Properties get() {
		if (configuracion == null) {
			new Configuracion();
		}
		return configuracion;
	}
	
	/**
	 * Constructor por defecto de uso interno.
	 * Sólo se ejecutará una vez.
	 */
	private Configuracion() {
		configuracion = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream("jv.config");
			configuracion.load(is);
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}

} // class