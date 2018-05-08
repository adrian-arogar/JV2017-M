/** 
 *  Proyecto: Juego de la vida.
 *  Implementa el concepto de correo según el modelo 2.
 *  Utiliza un string para representar el texto del correo.  
 *  @since: prototipo1.2
 *  @source: Correo.java 
 *  @version: 2.0 - 2018/02/14 
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;

import util.Formato;

public class Correo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String texto;
	
	/**
	 * Constructor convencional. Utiliza método set...()
	 * @param nif
	 * @throws ModeloException 
	 * @throws Exception 
	 */
	public Correo(String texto) throws ModeloException {
		setTexto(texto);
	}

	/**
	 * Constructor defecto. Utiliza método set...()
	 * @throws ModeloException 
	 */
	public Correo() throws ModeloException {
		this("correo@correo.com");
	}

	/**
	 * Constructor copia. 
	 * @param correo
	 */
	public Correo(Correo correo) {
		texto = new String(correo.texto);
	}

	public void setTexto(String texto) throws ModeloException {
		assert texto != null;
		if (formatoValido(texto) && correoAutentico()) {
			this.texto = texto;
		}
		else {
			throw new ModeloException("Correo: formato no válido.");
		}
	}

	/**
	 * Comprueba que una dirección de correo existe.
	 * @return
	 */
	private boolean correoAutentico() {
		// -- Pendiente --
		return true;
	}

	/**
	 * Comprueba validez de un dirección de correo.
	 * @param correo.
	 * @return true si cumple.
	 */
	private boolean formatoValido(String correo) {	
		return	correo.matches(Formato.PATRON_CORREO);
	}
	
	public String getTexto() {
		return texto;
	}
	
	/**
	 * hashcode() complementa al método equals y sirve para comparar objetos de forma 
	 * rápida en estructuras Hash. 
	 * Cuando Java compara dos objetos en estructuras de tipo hash (HashMap, HashSet etc)
	 * primero invoca al método hashcode y luego el equals.
	 * @return un número entero de 32 bit.
	*/
	@Override
	public int hashCode() {
		final int primo = 31;
		int result = 1;
		result = primo * result + ((texto == null) ? 0 : texto.hashCode());
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
			if (texto.equals(((Correo) obj).texto)) {
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
		return new Correo(this);
	}
	
	@Override
	public String toString() {
		return texto;
	}

}
