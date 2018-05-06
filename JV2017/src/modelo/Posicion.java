/** 
 * Proyecto: Juego de la vida.
 *  Implementa el concepto de posición según el modelo2.
 *  Representa la coordenada del espacio de una celda del mundo simulado.  
 *  @since: prototipo2
 *  @source: Posicion.java 
 *  @version: 2.0 - 2018/03/22
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;

public class Posicion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	public Posicion(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public Posicion() {
		this(0, 0);
	}

	public Posicion(Posicion posicion) {
		x = posicion.x;
		y = posicion.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}

	/**
	 * hashCode() complementa al método equals y sirve para comparar objetos de forma 
	 * rápida en estructuras Hash. 
	 * Cuando Java compara dos objetos en estructuras de tipo hash (HashMap, HashSet etc)
	 * primero invoca al método hashcode y luego el equals.
	 * @return un número entero de 32 bit.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Dos objetos son iguales si: 
	 * Son de la misma clase.
	 * Tienen los mismos valores en los atributos; o son el mismo objeto.
	 * @return falso si no cumple las condiciones.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			if (this == obj) {
				return true;
			}
			if (x == ((Posicion) obj).x 
					&& y == ((Posicion) obj).y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto clonado.
	 */
	@Override
	public Object clone() {
		// Utiliza el constructor copia.
		return new Posicion(this);
	}


	
} //class
