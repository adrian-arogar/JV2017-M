/** 
 * Proyecto: Juego de la vida.
 * Prueba Junit4 de la clase SimulacionesDAO.
 * @since: prototipo 2.1
 * @source: SimulacionesDAOTest.java 
 * @version: 2.1 - 2018.05.29
 * @author: DAM GRUPO 1 Francisco Jurado Abad
 * 						Alejandro Motellón Martínez
 */
package test.accesoDatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import accesoDatos.Datos;
import accesoDatos.DatosException;
import modelo.Mundo;
import modelo.Simulacion;
import modelo.Simulacion.EstadoSimulacion;
import util.Fecha;

public class SimulacionesDAOTest {

	private static Datos fachada;
	private Simulacion simulacionPrueba;
	
	/**
	 * Creacion de la fachada
	 */
	@BeforeClass
	public static void crearFachadaDatos() {
		try {
			fachada = new Datos();
		}
		catch (DatosException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Cargar datos de prueba
	 * @author DAM GRUPO 1 Francisco Jurado Abad
	 */
	@Before
	public void crearDatosPrueba() {
		try {
			simulacionPrueba= new Simulacion(fachada.obtenerUsuario("III1R"), new  Fecha(), 
					new Mundo(), EstadoSimulacion.PREPARADA);
		}
		catch(DatosException e){
			e.printStackTrace();
		}		
	}

	/**
	 * Borrar datos de prueba antes de cada ejecucion
	 * @author DAM GRUPO 1 Francisco Jurado Abad
	 */
	@After
	public void borraDatosPrueba() {
		fachada.borrarTodasSimulaciones();
		simulacionPrueba = null;
	}
	
	/**
	 * Test obtener Simulacion
	 * @author DAM GRUPO 1 Francisco Jurado Abad
	 */
	@Test
	public void testObtenerSimulacion() {
		try {
			fachada.obtenerSimulacion(simulacionPrueba);
			// busca Simulacion almacenada
			assertSame(simulacionPrueba, fachada.obtenerSimulacion(simulacionPrueba));
		}
		catch (DatosException e) {
			
		}

	}
	/**
	 * Test dar de alta una simulacion
	 * @author DAM GRUPO 1 Francisco Jurado Abad
	 */
	@Test
	public void altaSimulacion() {
		try {
			fachada.altaSimulacion(simulacionPrueba);
			assertSame(simulacionPrueba, fachada.obtenerSimulacion(simulacionPrueba));
		}
		catch (DatosException e) {
			
		}
	}
	
	/**
	 * Test dar de baja una simulacion 
	 * @author DAM GRUPO 1 Alejandro Motellón Martínez
	 */
	@Test
    public void bajaSimulacion() {
        try {
            fachada.bajaSimulacion(simulacionPrueba.getIdSimulacion());
            assertSame(simulacionPrueba.getIdSimulacion(), fachada.bajaSimulacion(simulacionPrueba.getIdSimulacion()));
        }
        catch (DatosException e) {
            
        }
    }

	/**
	 * Test actualizar cambios de una simulación 
	 * @author DAM GRUPO 1 - Alejandro Motellón Martínez
	 */
	@Test
    public void testActualizarSimulacion() {
        Simulacion simNueva = null;
        try {
            simNueva = new Simulacion(simulacionPrueba);
            fachada.altaSimulacion(simulacionPrueba);
            simNueva.setEstado(EstadoSimulacion.COMPLETADA);
            fachada.actualizarSimulacion(simNueva);
            assertEquals(fachada.obtenerSimulacion(simulacionPrueba), simNueva);
        } 
        catch (DatosException e) {
            
        }
    }

	/**
	 * Test básico ToString de simulación 
	 * @author DAM GRUPO 1 - Alejandro Motellón Martínez
	 */
    @Test
    public void testToStringDatos() {
        assertNotNull(fachada.toStringDatosSimulaciones());
    }

	
	
}// class
