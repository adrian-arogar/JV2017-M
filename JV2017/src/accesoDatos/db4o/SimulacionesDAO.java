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


		@Override
		public Object obtener(String id) throws DatosException {
			// TODO Auto-generated method stub
			return null;
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
		
		/**
		 * Obtiene todos las simulaciones almacenadas.
		 * @return - la List con todas las simulaciones.
		 * @author GRUPO 1 DAM
		 */
		public List <Simulacion> obtenerTodasSimulacion() {
			return null;
		}

}		