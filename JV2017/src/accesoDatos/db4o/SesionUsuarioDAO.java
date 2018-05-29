package accesoDatos.db4o;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import accesoDatos.DatosException;
import accesoDatos.OperacionesDAO;
import modelo.Simulacion;

public class SesionUsuarioDAO implements OperacionesDAO {
	
	private static SesionUsuarioDAO instancia = null;
	private static ObjectContainer db;
	
	@Override
	public Object obtener(String idSesion) throws DatosException {
		assert idSesion!=null;
		Query consulta = db.query();
		ObjectSet<Simulacion> result = consulta.execute();
		consulta.constrain(Simulacion.class);
		// equal??
		consulta.descend("idSesion").constrain(idSesion).equal();
		
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

}
