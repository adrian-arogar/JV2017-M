/** 
 *  Proyecto: Juego de la vida.
 *  Implementa el concepto de Mundo de una simulación según modelo2. 
 *  @since: prototipo2
 *  @source: Mundo.java 
 *  @version: 2.1 - 2018/05/21
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import config.Configuracion;

public class Mundo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private List <Integer> constantes;
	private Map <Patron, Posicion> distribucion;
	private byte[][] espacio;
	private enum FormaEspacio {PLANO1, PLANO2, ESFERICO}
	private static final FormaEspacio TIPO_MUNDO 
	= FormaEspacio.valueOf(Configuracion.get().getProperty("mundo.formaEspacio"));

	public Mundo(String nombre, List <Integer> constantes,
			Map <Patron, Posicion> distribucion) {
		setNombre(nombre);
		setConstantes(constantes);
		setDistribucion(distribucion);
		desplegarDistribucionEspacio();
	}

	public Mundo() {
		this("Demo", new ArrayList<Integer>(), 
				new HashMap <Patron, Posicion>());
	}

	public Mundo(Mundo mundo) {
		nombre = new String(mundo.nombre);
		constantes = new ArrayList <Integer>(mundo.constantes);
		distribucion = new HashMap <Patron, Posicion>(mundo.distribucion);
		espacio = mundo.espacio.clone();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List <Integer> getConstantes() {
		return constantes;
	}

	public void setConstantes(List <Integer> constantes) {
		this.constantes = constantes;
	}

	public Map<Patron, Posicion> getDistribucion() {
		return distribucion;
	}

	public void setDistribucion(Map<Patron, Posicion> distribucion) {
		this.distribucion = distribucion;
	}

	private void desplegarDistribucionEspacio() {
		Iterator <Entry <Patron, Posicion>> it = distribucion.entrySet().iterator();
		espacio = new byte[distribucion.entrySet().size()][distribucion.entrySet().size()];
		while (it.hasNext()) {
			Map.Entry <Patron, Posicion> pareja = (Map.Entry <Patron, Posicion>)it.next();
			procesarPatron((Patron) pareja.getKey(), (Posicion) pareja.getValue());
		}
	}

	private void procesarPatron(Patron patronActual, Posicion posicionActual) {
		// TODO Auto-generated method stub

	}

	public byte[][] getEspacio() {
		return espacio;
	}

	/**
	 * Actualiza el estado del Juego de la Vida.
	 * Actualiza según la configuración establecida para la forma del espacio.
	 */
	public void actualizarMundo() {
		if (TIPO_MUNDO == FormaEspacio.PLANO1) {
			actualizarMundoPlano1();
		}
		if (TIPO_MUNDO == FormaEspacio.PLANO2) {
			actualizarMundoPlano2();
		}
		if (TIPO_MUNDO == FormaEspacio.ESFERICO) {
			actualizarMundoEsferico();
		}
	}
	
	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son adyacentes con las del lado contrario.
	 * El mundo representado sería esférico cerrado sin límites para células de dos dimensiones.
	 */
	private void actualizarMundoEsferico()  {     					
		byte[][] nuevoEstado = new byte[espacio.length][espacio.length];

		for (int i = 0; i < espacio.length; i++) {
			for (int j = 0; j < espacio.length; j++) {

				int filaSuperior = i-1;
				int filaInferior = i+1;
				// Reajusta filas adyacentes.
				if (i == 0) {
					filaSuperior = espacio.length-1;
				}
				if (i == espacio.length-1) {
					filaInferior = 0;
				}

				int colAnterior = j-1;
				int colPosterior = j+1;
				// Reajusta columnas adyacentes.
				if (j == 0) {
					colAnterior = espacio.length-1;
				}
				if (j == espacio.length-1) {
					colPosterior = 0;
				}

				int vecinas = 0;							
				vecinas += espacio[filaSuperior][colAnterior];		// Celda NO
				vecinas += espacio[filaSuperior][j];					// Celda N		NO | N | NE
				vecinas += espacio[filaSuperior][colPosterior];		// Celda NE   	-----------
				vecinas += espacio[i][colPosterior];					// Celda E		 O | * | E
				vecinas += espacio[filaInferior][colPosterior];		// Celda SE	  	----------- 
				vecinas += espacio[filaInferior][j]; 					// Celda S		SO | S | SE
				vecinas += espacio[filaInferior][colAnterior]; 		// Celda SO 
				vecinas += espacio[i][colAnterior];					// Celda O           			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacio = nuevoEstado;
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son los límites absolutos.
	 * El mundo representado sería plano cerrado con límites para células de dos dimensiones.
	 */
	private void actualizarMundoPlano2()  {     					
		byte[][] nuevoEstado = new byte[espacio.length][espacio.length];

		for (int i = 0; i < espacio.length; i++) {
			for (int j = 0; j < espacio.length; j++) {
				int vecinas = 0;							
				vecinas += celdaNoroeste(i, j);		
				vecinas += celdaNorte(i, j);		    // 		NO | N | NE
				vecinas += celdaNoreste(i, j);		//    	-----------
				vecinas += celdaEste(i, j);			// 		 O | * | E
				vecinas += celdaSureste(i, j);		// 	  	----------- 
				vecinas += celdaSur(i, j); 			// 		SO | S | SE
				vecinas += celdaSuroeste(i, j); 	  
				vecinas += celdaOeste(i, j);		          			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacio = nuevoEstado;
	}

	/**
	 * Aplica las leyes del mundo a la celda indicada dada la cantidad de células adyacentes vivas.
	 * @param nuevoEstado
	 * @param fila
	 * @param col
	 * @param vecinas
	 */
	private void actualizarCelda(byte[][] nuevoEstado, int fila, int col, int vecinas) {
		if (vecinas < 2) {
			nuevoEstado[fila][col] = 0; 				// Subpoblación, muere...
		}
		if (vecinas > 3) {
			nuevoEstado[fila][col] = 0; 				// Sobrepoblación, muere...
		}
		if (vecinas == 3) {
			nuevoEstado[fila][col] = 1; 				// Pasa a estar viva o se mantiene.
		}
		if (vecinas == 2 && espacio[fila][col] == 1) {
			nuevoEstado[fila][col] = 1; 				// Se mantiene viva...
		}	
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Oeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Oeste.
	 */
	private byte celdaOeste(int fila, int col) {
		if (col-1 >= 0) {
			return espacio[fila][col-1]; 	// Celda O.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Suroeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Suroeste.
	 */
	private byte celdaSuroeste(int fila, int col) {
		if (fila+1 < espacio.length && col-1 >= 0) {
			return espacio[fila+1][col-1]; 	// Celda SO.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Sur de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Sur.
	 */
	private byte celdaSur(int fila, int col) {
		if (fila+1 < espacio.length) {
			return espacio[fila+1][col]; 	// Celda S.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Sureste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Sureste.
	 */
	private byte celdaSureste(int fila, int col) {
		if (fila+1 < espacio.length && col+1 < espacio.length) {
			return espacio[fila+1][col+1]; 	// Celda SE.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Este de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Este.
	 */
	private byte celdaEste(int fila, int col) {
		if (col+1 < espacio.length) {
			return espacio[fila][col+1]; 		// Celda E.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Noreste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Noreste.
	 */
	private byte celdaNoreste(int fila, int col) {
		if (fila-1 >= 0 && col+1 < espacio.length) {
			return espacio[fila-1][col+1]; 		// Celda NE.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al NO de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda NO.
	 */
	private byte celdaNorte(int fila, int col) {
		if (fila-1 >= 0) {
			return espacio[fila-1][col]; 		// Celda N.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Noroeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Noroeste.
	 */
	private byte celdaNoroeste(int fila, int col) {
		if (fila-1 >= 0 && col-1 >= 0) {
			return espacio[fila-1][col-1]; 		// Celda NO.
		}
		return 0;
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son los límites absolutos.
	 * El mundo representado sería plano cerrado con límites para células de dos dimensiones.
	 */
	private void actualizarMundoPlano1()  {     					
		byte[][] nuevoEstado = new byte[espacio.length][espacio.length];
		byte[][] matrizConteo;

		for (int i = 0; i < espacio.length; i++) {
			for (int j = 0; j < espacio.length; j++) {

				matrizConteo = replicarMatrizConteo(i, j);

				int vecinas = 0;							
				vecinas += matrizConteo[0][0];		// Celda NO
				vecinas += matrizConteo[0][1];		// Celda N		NO | N | NE
				vecinas += matrizConteo[0][2];		// Celda NE   	-----------
				vecinas += matrizConteo[1][2];  	// Celda E		 O | * | E
				vecinas += matrizConteo[2][2];		// Celda SE	  	----------- 
				vecinas += matrizConteo[2][1]; 		// Celda S		SO | S | SE
				vecinas += matrizConteo[2][0]; 		// Celda SO 
				vecinas += matrizConteo[1][0];		// Celda O           			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacio = nuevoEstado;
	}

	/**
	 * Obtiene una submatriz con las celdas adyacentes a cualquier celda del mundo.
	 * @param fila de la celda central de la submatriz
	 * @param col de la celda central de la submatriz
	 * @return matrizCopia
	 */
	private byte[][] replicarMatrizConteo(int fila, int col) {
		byte[][] matrizCopia = { 
				{ 0, 0, 0 }, 
				{ 0, 0, 0 }, 
				{ 0, 0, 0 }
		};

		// Zona central.
		if (fila-1 >= 0 && fila+1 < espacio.length && col-1 >= 0 && col+1 < espacio.length) {
			System.arraycopy(espacio[fila-1], col-1, matrizCopia[0], 0, 3);
			System.arraycopy(espacio[fila], col-1, matrizCopia[1], 0, 3);
			System.arraycopy(espacio[fila+1], col-1, matrizCopia[2], 0, 3);
			return matrizCopia;
		}

		// Banda periférica superior.
		if (fila == 0 && col-1 >= 0 && col+1 < espacio.length)	{
			System.arraycopy(espacio[fila], col-1, matrizCopia[1], 0, 3);
			System.arraycopy(espacio[fila+1], col-1, matrizCopia[2], 0, 3);
			return matrizCopia;
		}
		
		// Banda periférica inferior.
		if (fila == espacio.length-1 && col-1 >= 0 && col+1 < espacio.length) {
			System.arraycopy(espacio[fila-1], col-1, matrizCopia[0], 0, 3);
			System.arraycopy(espacio[fila], col-1, matrizCopia[1], 0, 3);
			return matrizCopia;
		}
		
		// Banda periférica izquierda.
		if (col == 0 && fila-1 >= 0 && fila+1 < espacio.length) {
			System.arraycopy(espacio[fila-1], col, matrizCopia[0], 1, 2);
			System.arraycopy(espacio[fila], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacio[fila+1], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}
		
		// Banda periférica derecha.
		if (col == espacio.length-1 && fila-1 >= 0 && fila+1 < espacio.length) {
			System.arraycopy(espacio[fila-1], col-1, matrizCopia[0], 0, 2);
			System.arraycopy(espacio[fila], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacio[fila+1], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas superior izquierda.
		if (fila == 0 && col == 0)	{
			System.arraycopy(espacio[fila], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacio[fila+1], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}

		// Esquinas superior derecha.
		if (fila == 0 && col == espacio.length-1)	{
			System.arraycopy(espacio[fila], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacio[fila+1], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas inferior izquierda.
		if (fila == espacio.length-1 && col == espacio.length-1)	{
			System.arraycopy(espacio[fila-1], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacio[fila], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas inferior derecha.
		if (fila == espacio.length-1 && col == 0)	{
			System.arraycopy(espacio[fila-1], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacio[fila], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}
		return matrizCopia;
	}
	
	@Override
	public String toString() {
		return "Mundo [nombre=" + nombre + ", constantes=" + constantes + ", distribucion=" + distribucion
				+ ", espacio=" + Arrays.toString(espacio) + ", getNombre()=" + getNombre() + ", getConstantes()="
				+ getConstantes() + ", getDistribucion()=" + getDistribucion() + ", getEspacio()="
				+ Arrays.toString(getEspacio()) + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constantes == null) ? 0 : constantes.hashCode());
		result = prime * result + ((distribucion == null) ? 0 : distribucion.hashCode());
		result = prime * result + Arrays.deepHashCode(espacio);
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mundo other = (Mundo) obj;
		if (constantes == null) {
			if (other.constantes != null)
				return false;
		} else if (!constantes.equals(other.constantes))
			return false;
		if (distribucion == null) {
			if (other.distribucion != null)
				return false;
		} else if (!distribucion.equals(other.distribucion))
			return false;
		if (!Arrays.deepEquals(espacio, other.espacio))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto clonado.
	 */
	@Override
	public Mundo clone() {
		return new Mundo(this);
	}

} // class
