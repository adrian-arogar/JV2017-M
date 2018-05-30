package accesoDatos.db4o;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.Mundo;
import modelo.Simulacion;
import modelo.Usuario;
import modelo.Simulacion.EstadoSimulacion;
import util.Fecha;

import java.util.List;

import com.db4o.ObjectContainer;
//import com.db4o.query.Query;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class SimulacionesDAO implements OperacionesDAO{
			// Requerido por el Singleton
		private static SimulacionesDAO instancia = null;
		// Metodo de almacenamiento
		private static ObjectContainer db;
		
		/** 
		 * Constructor por defecto de uso único 
		 * @author GRUPO 1 DAM - Francisco Jurado Abad
		 */
		public SimulacionesDAO() {
			db = Conexion.getDB();
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
		 *  @author GRUPO 1 DAM - Francisco Jurado Abad
		 */
		public static SimulacionesDAO getInstancia() throws DatosException {
			if (instancia == null) {
				instancia = new SimulacionesDAO();
			}
			return instancia;
		}
		
		
		/**
		 *  Método para generar de datos predeterminados.
		 *  @author GRUPO 1 DAM - Francisco Jurado Abad
		 */
		private void cargarPredeterminados() {
			try {
				Datos fachada = new Datos();
				Usuario usrDemo = null;
				usrDemo = fachada.obtenerUsuario("III1R");
				Mundo mundoDemo = fachada.obtenerMundo("Demo0");
				Simulacion simulacionDemo = new Simulacion(usrDemo, new Fecha(), 
						mundoDemo, EstadoSimulacion.PREPARADA);
				alta(simulacionDemo);
			
			} catch (DatosException e) {
					e.printStackTrace();
			}
		}

		/**
		 * Búsqueda de simulacion dada su id
		 * @param id - el id de Simulacion a buscar.
		 * @return - el Simulacion encontrado. 
		 * @throws DatosException - si no existe.
		 * @author GRUPO 1 DAM - Alejandro Motellón Martínez
		 */
		@Override
		public Simulacion obtener(String idSim) throws DatosException {
			assert idSim!=null;
			Query consulta = db.query();
			ObjectSet<Simulacion> result = consulta.execute();
			consulta.constrain(Simulacion.class);
			// equal??
			consulta.descend("idSimulacion").constrain(idSim).equal();
			
			if (result.size() > 0) {
				return result.get(0);
			} else {
					return null;
			}		
		}



		@Override
		public Object obtener(Object obj) throws DatosException {
			// TODO Auto-generated method stub
			return null;
		}

		/**
         *  Recibe un argumento que representa la nueva simulación
         *  Busca previamente la posición que le corresponde por búsqueda binaria.
         *  @param obj - Simulación a almacenar.
         *  @throws DatosException - si ya existe.
         *  @author GRUPO 1 DAM - Manuel Castillo Jiménez
         */    
        @Override
        public void alta(Object obj) throws DatosException {
            Simulacion simNueva = (Simulacion) obj;
            Simulacion simPrevia = null;
            
            try {
                simPrevia = obtener(simNueva.getIdSimulacion());
            } catch (DatosException e) {
                db.store(simNueva);
                return;
            }
            
            throw new DatosException("Simulacion: " + simPrevia + "ya existente");
        }


		@Override
		public Object baja(String id) throws DatosException {
			// TODO Auto-generated method stub
			return null;
		}


		/**
		 *  Actualiza datos de una Simulacion reemplazando el almacenado por el recibido.
		 *	@param obj - Simulación actualizada recibida.
		 *  @throws DatosException - si la simulación recibida no existe o no concuerda su id.
		 *  @author GRUPO 1 DAM - Juan Antonio Espinosa Gálvez.
		 */
		@Override
		public void actualizar(Object obj) throws DatosException {
			Simulacion simActualizada = (Simulacion) obj;
			Simulacion simPrevia = null;
			try {
				simPrevia = (Simulacion) obtener(simActualizada.getIdSimulacion());
				simPrevia.setEstado(simActualizada.getEstado());
				simPrevia.setFecha(simActualizada.getFecha());
				simPrevia.setMundo(simActualizada.getMundo());
				simPrevia.setUsr(simActualizada.getUsr());
				db.store(simPrevia);
			} catch (DatosException e) {
				throw new DatosException("Actualizar: " + simActualizada.getIdSimulacion() + "no existe");
			}
		}

		/**
         * Obtiene el listado de todas las simulaciones almacenadas.
         * @return el texto con el volcado de datos.
         * @author GRUPO 1 - José Antonio Aldeguer Madrid
         */

		@Override
		public String listarDatos() {
			StringBuilder listado = new StringBuilder();
			for (Simulacion sim: obtenerTodasSimulacion()) {
				listado.append("\n"+ sim);
			}
			return listado.toString();
		}


		@Override
		public void borrarTodo() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void cerrar() {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * Obtiene todos las simulaciones almacenadas.
		 * @return - la List con todas las simulaciones.
		 * @author GRUPO 1 DAM
		 */
		public List <Simulacion> obtenerTodasSimulacion() {
			return null;
		}

}		