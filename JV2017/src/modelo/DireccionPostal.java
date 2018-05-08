/** 
 *  Proyecto: Juego de la vida.
 *  Implementa el concepto de direccion postal según el modelo 2.
 *  Utiliza un varios string para representar los distintos campos.  
 *  @since: prototipo1.2
 *  @source: DireccionPostal.java 
 *  @version: 2.0 - 2018/02/14 
 *  @author: ajp
 */

package modelo;

import java.io.Serializable;

import util.Formato;

public class DireccionPostal implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private String calle;
	private String numero;
	private String cp;
	private String poblacion;

	public DireccionPostal(String calle, String numero, String cp, String poblacion) throws ModeloException {
		setCalle(calle);
		setNumero(numero);
		setCp(cp);
		setPoblacion(poblacion);
	}

	public DireccionPostal() throws ModeloException {
		this("Calle", "00", "00000", "Población");
	}

	public DireccionPostal(DireccionPostal dp) {
		calle = new String(dp.calle);
		numero = new String(dp.numero);
		cp = new String(dp.cp);
		poblacion = new String(dp.poblacion);
	}

	public void setCalle(String calle) throws ModeloException {
		if  (calleValida(calle)) {
			this.calle = calle;
		}
		else {
			throw new ModeloException("DireccionPostal: calle no válida.");
		}
	}

	/**
	 * Comprueba validez de una calle.
	 * @param calle.
	 * @return true si cumple.
	 */
	private boolean calleValida(String calle) {
		assert calle != null;
		return	calle.matches(Formato.PATRON_NOMBRE_VIA);
	}

	public void setNumero(String numero) throws ModeloException {
		if (numeroValido(numero)) {
			this.numero = numero;
		}
		else {
			throw new ModeloException("DireccionPostal: numero no válido.");
		}
	}

	/**
	 * Comprueba validez de un numero postal.
	 * @param numero.
	 * @return true si cumple.
	 */
	private boolean numeroValido(String numero) {
		assert numero != null;
		return	numero.matches(Formato.PATRON_NUMERO_POSTAL);
	}

	public void setCp(String cp) throws ModeloException {
		if (cpValido(cp)) {
			this.cp = cp;
		}
		else {
			throw new ModeloException("DireccionPostal: cp no válido.");
		}
	}

	/**
	 * Comprueba validez de un código postal.
	 * @param cp.
	 * @return true si cumple.
	 */
	private boolean cpValido(String cp) {
		assert cp != null;
		return	cp.matches("[\\d]{5}");
	}

	public void setPoblacion(String poblacion) throws ModeloException {
		if (poblacionValida(poblacion)) {
			this.poblacion = poblacion;
		}
		else {
			throw new ModeloException("DireccionPostal: poblacion no válido.");
		}
	}

	/**
	 * Comprueba validez de una población.
	 * @param poblacion.
	 * @return true si cumple.
	 */
	private boolean poblacionValida(String poblacion) {
		assert poblacion != null;
		return	poblacion.matches(Formato.PATRON_TOPONIMO);
	}

	public String getCalle() {
		return calle;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public String getCp() {
		return cp;
	}
	
	public String getPoblacion() {
		return poblacion;
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
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((cp == null) ? 0 : cp.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((poblacion == null) ? 0 : poblacion.hashCode());
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
			if (calle.equals(((DireccionPostal)obj).calle) 
					&& cp.equals(((DireccionPostal)obj).cp) 
					&& numero.equals(((DireccionPostal)obj).numero) 
					&& poblacion.equals(((DireccionPostal)obj).poblacion)
					) {
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
		return new DireccionPostal(this);
	}

	@Override
	public String toString() {
		return calle + ", " + numero + ", " + cp + ", " + poblacion;
	}

}
