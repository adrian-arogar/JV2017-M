/** Proyecto: Juego de la vida.
 *  Gestiona la interacción con el usuario y la simulación del JV.  
 *  @since: prototipo1.2
 *  @source: Presentacion.java 
 *  @version: 1.2 - 2018/03/08 
 *  @author: ajp
 */

package accesoUsr;

import java.util.Scanner;
import accesoDatos.Datos;
import accesoDatos.DatosException;
import modelo.*;

public class Presentacion {
	
	private static Datos datos;
	
	private static final int MAX_INTENTOS_FALLIDOS = 3;
	private static Usuario usrEnSesion;
	
	private static byte[][] espacioMundo;
	private static final int TAMAÑO_MUNDO = 12;
	private static final int CICLOS_SIMULACION = 35;
	private enum FormaEspacio { PLANO1, PLANO2, ESFERICO }
	private static final FormaEspacio TIPO_MUNDO = FormaEspacio.ESFERICO;
	
	/**
	 * Constructor
	 * @param datos
	 */
	public Presentacion() {
		try {
			datos = new Datos();
		} 
		catch (DatosException e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsrEnSesion() {
		return usrEnSesion;
	}
	
	/**
	 * Controla el acceso de usuario.
	 * @return true si la sesión se inicia correctamente.
	 */
	public boolean iniciarSesionCorrecta() {
		Scanner teclado = new Scanner(System.in);	// Entrada por consola.
		int intentosPermitidos = MAX_INTENTOS_FALLIDOS;

		do {
			// Pide usuario y contraseña.
			System.out.print("Introduce el nif de usuario: ");
			String nif = teclado.nextLine();
			System.out.print("Introduce clava de acceso: ");
			String clave = teclado.nextLine();
			
			try {
				// Busca usuario coincidente con las credenciales.
				usrEnSesion = datos.obtenerUsuario(nif);
				
				// Encripta clave tecleada utilizando un objeto temporal.
				// que ejecutaráDatos datos automáticamente el método privado.
				if (usrEnSesion != null 
						&& usrEnSesion.getClaveAcceso().equals(new ClaveAcceso(clave))) {
					return true;
				} else {
					intentosPermitidos--;
					System.out.print("Credenciales incorrectas: ");
					System.out.println("Quedan " + intentosPermitidos + " intentos... ");
				}
			} catch (ModeloException | DatosException e) {
				intentosPermitidos--;
				System.out.print("Credenciales incorrectas-: ");
				System.out.println("Quedan " + intentosPermitidos + " intentos... ");
			} 
		} while (intentosPermitidos > 0);

		return false;
	}
	
	/**
	 * Actualiza el estado del Juego de la Vida.
	 * Actualiza según la configuración establecida para la forma del espacio.
	 */
	private void actualizarMundo() {
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
	 * Despliega en la consola el estado almacenado, corresponde
	 * a una generación del Juego de la vida.
	 */
	public void mostrarMundo() {

		for (int i = 0; i < TAMAÑO_MUNDO; i++) {
			for (int j = 0; j < TAMAÑO_MUNDO; j++) {		
				System.out.print((espacioMundo[i][j] == 1) ? "|o" : "| ");
			}
			System.out.println("|");
		}
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son adyacentes con las del lado contrario.
	 * El mundo representado sería esférico cerrado sin límites para células de dos dimensiones.
	 */
	public void actualizarMundoEsferico()  {     					
		byte[][] nuevoEstado = new byte[TAMAÑO_MUNDO][TAMAÑO_MUNDO];

		for (int i = 0; i < TAMAÑO_MUNDO; i++) {
			for (int j = 0; j < TAMAÑO_MUNDO; j++) {

				int filaSuperior = i-1;
				int filaInferior = i+1;
				// Reajusta filas adyacentes.
				if (i == 0) {
					filaSuperior = TAMAÑO_MUNDO-1;
				}
				if (i == TAMAÑO_MUNDO-1) {
					filaInferior = 0;
				}

				int colAnterior = j-1;
				int colPosterior = j+1;
				// Reajusta columnas adyacentes.
				if (j == 0) {
					colAnterior = TAMAÑO_MUNDO-1;
				}
				if (j == TAMAÑO_MUNDO-1) {
					colPosterior = 0;
				}

				int vecinas = 0;							
				vecinas += espacioMundo[filaSuperior][colAnterior];		// Celda NO
				vecinas += espacioMundo[filaSuperior][j];					// Celda N		NO | N | NE
				vecinas += espacioMundo[filaSuperior][colPosterior];		// Celda NE   	-----------
				vecinas += espacioMundo[i][colPosterior];					// Celda E		 O | * | E
				vecinas += espacioMundo[filaInferior][colPosterior];		// Celda SE	  	----------- 
				vecinas += espacioMundo[filaInferior][j]; 					// Celda S		SO | S | SE
				vecinas += espacioMundo[filaInferior][colAnterior]; 		// Celda SO 
				vecinas += espacioMundo[i][colAnterior];					// Celda O           			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacioMundo = nuevoEstado;
	}

	/**
	 * Apartado 8+:
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son los límites absolutos.
	 * El mundo representado sería plano cerrado con límites para células de dos dimensiones.
	 */
	private void actualizarMundoPlano2()  {     					
		byte[][] nuevoEstado = new byte[TAMAÑO_MUNDO][TAMAÑO_MUNDO];

		for (int i = 0; i < TAMAÑO_MUNDO; i++) {
			for (int j = 0; j < TAMAÑO_MUNDO; j++) {
				int vecinas = 0;							
				vecinas += celdaNoroeste(i, j);		
				vecinas += celdaNorte(i, j);		// 		NO | N | NE
				vecinas += celdaNoreste(i, j);		//    	-----------
				vecinas += celdaEste(i, j);			// 		 O | * | E
				vecinas += celdaSureste(i, j);		// 	  	----------- 
				vecinas += celdaSur(i, j); 			// 		SO | S | SE
				vecinas += celdaSuroeste(i, j); 	  
				vecinas += celdaOeste(i, j);		          			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacioMundo = nuevoEstado;
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
		if (vecinas == 2 && espacioMundo[fila][col] == 1) {
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
			return espacioMundo[fila][col-1]; 	// Celda O.
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
		if (fila+1 < TAMAÑO_MUNDO && col-1 >= 0) {
			return espacioMundo[fila+1][col-1]; 	// Celda SO.
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
		if (fila+1 < TAMAÑO_MUNDO) {
			return espacioMundo[fila+1][col]; 	// Celda S.
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
		if (fila+1 < TAMAÑO_MUNDO && col+1 < TAMAÑO_MUNDO) {
			return espacioMundo[fila+1][col+1]; 	// Celda SE.
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
		if (col+1 < TAMAÑO_MUNDO) {
			return espacioMundo[fila][col+1]; 		// Celda E.
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
		if (fila-1 >= 0 && col+1 < TAMAÑO_MUNDO) {
			return espacioMundo[fila-1][col+1]; 		// Celda NE.
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
			return espacioMundo[fila-1][col]; 		// Celda N.
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
			return espacioMundo[fila-1][col-1]; 		// Celda NO.
		}
		return 0;
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son los límites absolutos.
	 * El mundo representado sería plano cerrado con límites para células de dos dimensiones.
	 */
	private void actualizarMundoPlano1()  {     					
		byte[][] nuevoEstado = new byte[TAMAÑO_MUNDO][TAMAÑO_MUNDO];
		byte[][] matrizConteo;

		for (int i = 0; i < TAMAÑO_MUNDO; i++) {
			for (int j = 0; j < TAMAÑO_MUNDO; j++) {

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
		espacioMundo = nuevoEstado;
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
		if (fila-1 >= 0 && fila+1 < TAMAÑO_MUNDO && col-1 >= 0 && col+1 < TAMAÑO_MUNDO) {
			System.arraycopy(espacioMundo[fila-1], col-1, matrizCopia[0], 0, 3);
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 3);
			System.arraycopy(espacioMundo[fila+1], col-1, matrizCopia[2], 0, 3);
			return matrizCopia;
		}

		// Banda periférica superior.
		if (fila == 0 && col-1 >= 0 && col+1 < TAMAÑO_MUNDO)	{
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 3);
			System.arraycopy(espacioMundo[fila+1], col-1, matrizCopia[2], 0, 3);
			return matrizCopia;
		}
		
		// Banda periférica inferior.
		if (fila == TAMAÑO_MUNDO-1 && col-1 >= 0 && col+1 < TAMAÑO_MUNDO) {
			System.arraycopy(espacioMundo[fila-1], col-1, matrizCopia[0], 0, 3);
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 3);
			return matrizCopia;
		}
		
		// Banda periférica izquierda.
		if (col == 0 && fila-1 >= 0 && fila+1 < TAMAÑO_MUNDO) {
			System.arraycopy(espacioMundo[fila-1], col, matrizCopia[0], 1, 2);
			System.arraycopy(espacioMundo[fila], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacioMundo[fila+1], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}
		
		// Banda periférica derecha.
		if (col == TAMAÑO_MUNDO-1 && fila-1 >= 0 && fila+1 < TAMAÑO_MUNDO) {
			System.arraycopy(espacioMundo[fila-1], col-1, matrizCopia[0], 0, 2);
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacioMundo[fila+1], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas superior izquierda.
		if (fila == 0 && col == 0)	{
			System.arraycopy(espacioMundo[fila], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacioMundo[fila+1], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}

		// Esquinas superior derecha.
		if (fila == 0 && col == TAMAÑO_MUNDO-1)	{
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacioMundo[fila+1], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas inferior izquierda.
		if (fila == TAMAÑO_MUNDO-1 && col == TAMAÑO_MUNDO-1)	{
			System.arraycopy(espacioMundo[fila-1], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas inferior derecha.
		if (fila == TAMAÑO_MUNDO-1 && col == 0)	{
			System.arraycopy(espacioMundo[fila-1], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacioMundo[fila], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}
		return matrizCopia;
	}
	
	/**
	 * Ejecuta una simulación del juego de la vida en la consola.
	 */
	public void arrancarSimulacion() {
		cargarMundoDemo();
		int generacion = 0; 
		do {
			System.out.println("\nGeneración: " + generacion);
			mostrarMundo();
			actualizarMundo();
			generacion++;
		}
		while (generacion < CICLOS_SIMULACION);
	}
	
	/**
	 * Carga datos demo en la matriz que representa el mundo. 
	 */
	public void cargarMundoDemo() {
		// En este array los 0 indican celdas con células muertas y los 1 vivas.
		espacioMundo = new byte[][] { 
			{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0 }, //
			{ 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // 
			{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Planeador
			{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Flip-Flop
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } // 1x Still Life
		};
	}

} // class
