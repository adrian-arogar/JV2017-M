/** 
 * Proyecto: Juego de la vida.
 * Implementación del control de inicio de sesión y ejecución de la simulación por defecto. 
 * @since: prototipo1.1
 * @source: JVPrincipal.java 
 * @version: 2.0 - 2018/04/20
 * @author: ajp
 */

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoUsr.Presentacion;

public class JVPrincipal {

	/**
	 * Secuencia principal del programa.
	 */
	public static void main(String[] args) {		

		Datos fachada = null;
		try {
			fachada = new Datos();
		} 
		catch (DatosException e) {
			e.printStackTrace();
		}
		Presentacion presentacion = new Presentacion();
		
		//DatosPrueba.cargarUsuariosPrueba();	
		System.out.println(fachada.toStringDatosUsuarios());
		
		if (presentacion.iniciarSesionCorrecta()) {
			presentacion.arrancarSimulacion();
		}
		else {		
			System.out.println("\nDemasiados intentos fallidos...");
		}	
		fachada.cerrar();
		System.out.println("Fin del programa.");
	}
	
} //class
