/** Proyecto: Juego de la vida.
 *  Resuelve todos los aspectos relacionados con el control 
 *  de una simulación. Colabora en el patron MVC
 *  @since: prototipo2.1
 *  @source: ControlSimulación.java 
 *  @version: 2.1 - 2018.05.08
 *  @author: ajp
 */

package accesoUsr.consola.control;

import accesoDatos.Datos;
import accesoUsr.consola.VistaSimulacion;
import config.Configuracion;
import modelo.Mundo;
import modelo.Simulacion;

public class ControlSimulacion {
	Datos fachada;
	final int CICLOS = new Integer(Configuracion.get().getProperty("simulacion.ciclos"));
	VistaSimulacion vistaSimulacion;
	Simulacion simulacion;
	Mundo mundo;
	
	public ControlSimulacion(Simulacion simulacion) {
		initControlSimulacion();
	}
	
	private void initControlSimulacion() {	
		
	}

	
} // class
