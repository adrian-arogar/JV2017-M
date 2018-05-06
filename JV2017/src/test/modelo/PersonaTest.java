/** 
 * Proyecto: Juego de la vida.
 * Clase JUnit de prueba automatizada de las características de la clase Persona según el modelo 2.
 * @since: prototipo2
 * @source: TestPersona.java 
 * @version: 2.0 - 2018.04.21
 * @author: ajp
 */

package test.modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.ModeloException;
import modelo.Nif;
import modelo.Persona;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class PersonaTest {
	private Persona persona1; 
	private static Persona persona2; 

	/**
	 * Método que se ejecuta una sola vez al principio del conjunto pruebas.
	 * @throws DatosException 
	 */
	@BeforeClass
	public static void iniciarlizarDatosFijos() {
		// Objetos no modicados en las pruebas.
		try {
			persona2 = new Persona(new Nif("00000001R"), "Luis", "Pérez Ruiz",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), new Fecha(2000, 03, 21));
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 * @throws DatosException 
	 */
	@Before
	public void iniciarlizarDatosVariables() {
		// Objetos para la prueba.
		try {
			persona1 = new Persona(); 
		} 
		catch (ModeloException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After	
	public void borrarDatosPrueba() {
		persona1 = null;
	}

	// Test CON DATOS VALIDOS

	@Test
	public void testSetNifCorrecto() {
		try {
			persona1.setNif(new Nif("00000001R"));
			assertEquals(persona1.getNif(), new Nif("00000001R"));
		} 
		catch (ModeloException e) { 
			fail("No debe llegar aquí");
		} 
	}

	@Test
	public void testSetNombreCorrecto() {
		try {
			persona1.setNombre("Luis");
			assertEquals(persona1.getNombre(), "Luis");
		} 
		catch (ModeloException e) { 
			fail("No debe llegar aquí");
		} 
	}

	@Test
	public void testSetApellidosCorrecto() {
		try {
			persona1.setApellidos("Pérez Ruiz");
			assertEquals(persona1.getApellidos(), "Pérez Ruiz");
		} 
		catch (ModeloException e) { 
			fail("No debe llegar aquí");
		} 
	}

	@Test
	public void testSetDomicilioCorrecto() {
		try {
			persona1.setDomicilio(new DireccionPostal("Roncal", "10", "30130", "Murcia"));
			assertEquals(persona1.getDomicilio(), new DireccionPostal("Roncal", "10", "30130", "Murcia"));
		} 
		catch (ModeloException e) { 
			fail("No debe llegar aquí");
		} 
	}

	@Test
	public void testSetCorreoCorrecto() {
		try {
			persona1.setCorreo(new Correo("luis@gmail.com"));
			assertEquals(persona1.getCorreo(), new Correo("luis@gmail.com"));
		} 
		catch (ModeloException e) { 
			fail("No debe llegar aquí");
		} 
	}

	@Test
	public void testSetFechaNacimientoCorrecta() {

		try {
			persona1.setFechaNacimiento(new Fecha(2002, 04, 20));
			assertEquals( persona1.getFechaNacimiento(), new Fecha(2002, 04, 20));
		} 
		catch (ModeloException e) { 
			fail("No debe llegar aquí...");
		}
	}

	@Test
	public void testToString() {
		assertEquals(persona2.toString(), "nif:             00000001R\n" + 
				"nombre:          Luis\n" + 
				"apellidos:       Pérez Ruiz\n" + 
				"domicilio:       Roncal, 10, 30130, Murcia\n" + 
				"correo:          luis@gmail.com\n" + 
				"fechaNacimiento: 2000.03.21 - 00:00:00\n");
	}

	@Test
	public void testEqualsObject() {
		try {
			persona1 = new Persona(new Nif("00000001R"), "Luis", "Pérez Ruiz",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), new Fecha(2000, 03, 21));
			assertEquals(persona1, persona2);
		} 
		catch (Exception e) { 
			fail("No debe llegar aquí...");
		}
	}

	@Test
	public void testClone() {
		try {
			persona1 = persona2.clone();
			assertEquals(persona1, persona2);
		} 
		catch (Exception e) { 
			fail("No debe llegar aquí...");
		}
	}

	@Test
	public void testHashCode() {
		assertEquals(persona2.hashCode(), 1456814220);
	}


	// Test CON DATOS NO VALIDOS
	@Test
	public void testSetNifNull() {
		try {
			persona1.setNif(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
		}
	}
	
	@Test
	public void testSetNombreNull() {
		try {
			persona1.setNombre(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
		}
	}
	
	@Test
	public void testSetApellidosNull() {
		try {
			persona1.setApellidos(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
		}
	}
	
	@Test
	public void testSetDomicilioNull() {
		try {
			persona1.setDomicilio(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
		}
	}
	
	@Test
	public void testSetCorreoNull() {
		try {
			persona1.setCorreo(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
		}
	}

	@Test
	public void testSetFechaNacimientoNull() {
		try {
			persona1.setFechaNacimiento(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | ModeloException e) { 
		}
	}
	
	@Test
	public void testSetNombreFormatoCaracterIncorrecto() {
		try {
			persona1.setNombre("Pe?pe");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) { 
		}
	}

	@Test
	public void testSetNombreFormatoMayusculaInicial() {
		try {
			persona1.setNombre("pepe");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) { 
		}
	}
	
	@Test
	public void testSetApellidosFormatoIncorrecto() {
		try {
			persona1.setApellidos("pérez Ruiz");
			fail("No debe llegar aquí...");
		} 
		catch (ModeloException e) { 
		}
	}
	
	@Test
	public void testValidarFechaNacimientoIncorrecta() {	
		try {
			persona1.setFechaNacimiento(new Fecha(3025, 9, 10));
			fail("No debe llegar aquí");
		} 
		catch (ModeloException e) {
		}
	}

} // class
