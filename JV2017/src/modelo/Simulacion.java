/** 
 * Proyecto: Juego de la vida.
 * Organiza aspectos de gestión de la simulación según el modelo 2.
 * @since: prototipo2.0
 * @source: Simulacion.java 
 * @version: 2.0 - 2018.04.20
 * @author: ajp
 */

package modelo;

import java.io.Serializable;

import util.Fecha;
import util.UtilException;

public class Simulacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Usuario usr;
	private Fecha fecha;
	private Mundo mundo;
	public enum EstadoSimulacion  {PREPARADA, INICIADA, COMPLETADA}
	private EstadoSimulacion estado;

	/**
	 * Constructor convencional.
	 * Establece el valor inicial de cada uno de los atributos.
	 * Recibe parámetros que se corresponden con los atributos.
	 * Utiliza métodos set... para la posible verificación.
	 * @param usr
	 * @param fecha
	 * @param mundo
	 * @param estado
	 */
	public Simulacion(Usuario usr, Fecha fecha, Mundo mundo, EstadoSimulacion estado) {
		setUsr(usr);
		setFecha(fecha);
		setMundo(mundo);
		setEstado(estado);
	}

	/**
	 * Constructor por defecto.
	 * Establece el valor inicial, por defecto, de cada uno de los atributos.
	 * Llama al constructor convencional de la propia clase.
	 * @throws ModeloException 
	 * @throws UtilException  
	 */
	public Simulacion() throws ModeloException {
		this(new Usuario(), new Fecha(), new Mundo(), EstadoSimulacion.PREPARADA);
	}

	/**
	 * Constructor copia.
	 * Establece el valor inicial de cada uno de los atributos a partir de
	 * los valores obtenidos de un objeto de su misma clase.
	 * El objeto Usuario es compartido (agregación).
	 * Llama al constructor convencional.
	 * @param s - la Simulacion a clonar
	 */
	public Simulacion(Simulacion s) {
		this(s.usr, new Fecha(s.fecha), new Mundo(s.mundo), s.estado);
	}

	public Usuario getUsr() {
		return usr;
	}

	public Mundo getMundo() {
		return mundo;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public EstadoSimulacion getEstado() {
		return estado;
	}

	/**
	 * Obtiene idSesion concatenando idUsr + un número como texto:
	 * @return idSesion único generado.
	 */
	public String getIdSimulacion() {
		return	String.format("%s:%s",
				usr.getIdUsr(), fecha.toStringMarcaTiempo());
	}

	public void setUsr(Usuario usr) {
		this.usr = usr;
	}

	public void setMundo(Mundo mundo) {
		this.mundo = mundo;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public void setEstado(EstadoSimulacion estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return String.format(
				"Simulacion [usr=%s, fecha=%s, mundo=%s, estado=%s]", usr, fecha, mundo, estado);
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
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((mundo == null) ? 0 : mundo.hashCode());
		result = prime * result + ((usr == null) ? 0 : usr.hashCode());
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
			if (usr.equals(((Simulacion)obj).usr) &&
					fecha.equals(((Simulacion)obj).fecha) &&
					mundo.equals(((Simulacion)obj).mundo) &&
					estado.equals(((Simulacion)obj).estado) 
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
		return new Simulacion(this);
	}

} //class
