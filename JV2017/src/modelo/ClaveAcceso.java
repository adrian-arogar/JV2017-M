/** 
 *  Proyecto: Juego de la vida.
 *  Implementa el concepto de ClaveAcceso según el modelo 2.
 *  Utiliza un string para representar el texto del ClaveAcceso.  
 *  @since: prototipo1.2
 *  @source: ClaveAcceso.java 
 *  @version: 2.0 - 2018/02/14 
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;

import util.Criptografia;
import util.Formato;

public class ClaveAcceso implements Serializable {
	
	private String texto;

	/**
	 * Constructor convencional. Utiliza método set...()
	 * @param ClaveAcceso
	 * @throws ModeloException 
	 */
	public ClaveAcceso(String texto) throws ModeloException {
		setTexto(texto);
	}

	/**
	 * Constructor defecto. Utiliza método set...()
	 * @throws ModeloException 
	 */
	public ClaveAcceso() throws ModeloException {
		this("Miau#0");
	}

	/**
	 * Constructor copia. 
	 * @param ClaveAcceso
	 */
	public ClaveAcceso(ClaveAcceso ClaveAcceso) {
		texto = new String(ClaveAcceso.texto);
	}

	public void setTexto(String texto) throws ModeloException {
		assert texto != null;
		if (textoValido(texto)) {
			this.texto = Criptografia.cesar(texto);
		}
		else {
			throw new ModeloException("ClaveAcceso: formato no válido.");
		}
	}

	/**
	 * Comprueba validez de una clave de acceso.
	 * @param claveAcceso.
	 * @return true si cumple.
	 */
	private boolean textoValido(String texto) {
			return	texto.matches(Formato.PATRON_CONTRASEÑA2);
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
			if (texto.equals(((ClaveAcceso) obj).texto)) {
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
		return new ClaveAcceso(this);
	}

	@Override
	public String toString() {
		return texto;
	}

}
