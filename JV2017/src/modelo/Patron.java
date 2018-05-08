/** 
 *  Proyecto: Juego de la vida.
 *  Implementa el concepto de Patrón de células según el modelo2.
 *  Representa una formación de células conocido que tiene un
 *  comportamiento particular en una simulación.  
 *  @since: prototipo2
 *  @source: Posicion.java 
 *  @version: 2.0 - 2018/03/21
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;
import java.util.Arrays;

public class Patron implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private byte[][] esquema;

	public  Patron( ) {
		this("NombrePatron", new byte[1][1]);
	}

	public Patron(String nombre, byte[][] esquema) {
		setNombre(nombre);
		setEsquema(esquema);
	}

	public Patron(Patron patron) {
		nombre = new String(patron.nombre);
		esquema = patron.esquema.clone();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[][] getEsquema() {
		return esquema;
	}

	public void setEsquema(byte[][] esquema) {
		this.esquema = esquema;
	}

	@Override
	public String toString() {
		StringBuilder texto = new StringBuilder();
		for (int i=0; i < esquema.length; i++) {
			for (int j=0; j < esquema[i].length; j++) {
				texto.append(esquema[i][j]); 
			}
		}
		return texto.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(esquema);
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
			if (!Arrays.deepEquals(esquema, ((Patron) obj).esquema)
					&& nombre.equals(((Patron) obj).nombre)) {
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
		return new Patron(this);
	}

} // class
