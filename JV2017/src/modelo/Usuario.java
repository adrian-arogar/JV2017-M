/** 
 *  Proyecto: Juego de la vida.
 *  Implementa el concepto de Usuario según el modelo 2.
 *  @since: prototipo1.0
 *  @source: Usuario.java 
 *  @version: 2.0 - 2018/03/16 
 *  @author: ajp
 */

package modelo;

import util.Fecha;

public class Usuario  extends Persona {

	public enum RolUsuario {
		INVITADO, 
		NORMAL, 
		ADMINISTRADOR
	}
	private static final long serialVersionUID = 1L;
	private String idUsr;
	private Fecha fechaAlta;
	private ClaveAcceso claveAcceso;
	private RolUsuario rol;

	/**
	 * Constructor convencional. Utiliza métodos set...()
	 * @param fechaAlta
	 * @param claveAcceso
	 * @param rol
	 * @throws ModeloException 
	 */
	public Usuario(Nif nif, String nombre, String apellidos,
			DireccionPostal domicilio, Correo correo, Fecha fechaNacimiento,
			Fecha fechaAlta, ClaveAcceso claveAcceso, RolUsuario rol) throws ModeloException {
		super(nif, nombre, apellidos, domicilio, correo, fechaNacimiento);
		generarIdUsr();
		setFechaAlta(fechaAlta);
		setClaveAcceso(claveAcceso);
		setRol(rol);
	}

	/**
	 * Constructor por defecto. Reenvía al constructor convencional.
	 * @throws ModeloException 
	 */
	public Usuario() throws ModeloException {
		this(new Nif(), "Nombre", "Apellido Apellido", 
				new DireccionPostal(), 
				new Correo(), 
				new Fecha(2000, 01, 12), 
				new Fecha(), 
				new ClaveAcceso(), RolUsuario.NORMAL);
	}

	/**
	 * Constructor copia.
	 * @param usr
	 */
	public Usuario(Usuario usr) {
		super(usr);
		idUsr = new String(usr.idUsr);
		fechaAlta = new Fecha(usr.fechaAlta);
		claveAcceso = new ClaveAcceso(usr.claveAcceso);
		rol = usr.rol;
	}

	/**
	 * Genera el idUsr con:
	 * La letra inicial del nombre, 
	 * Las dos iniciales del primer y segundo apellido,
	 * Los dos último caracteres del nif. 
	 */
	private void generarIdUsr() {	
		assert nif != null;
		assert nombre != null;
		assert apellidos != null;
		idUsr = "" 
				+ nombre.charAt(0) 
				+ apellidos.charAt(0) + apellidos.charAt(apellidos.indexOf(" ")+1)
				+ nif.getTexto().substring(7);
	}

	/**
	 * Genera una variante cambiando la última letra del idUsr 
	 * por la siguiente en el alfabeto previsto para el nif.
	 */
	public void generarVarianteIdUsr() {
		assert idUsr != null;
		String alfabetoNif = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		String alfabetoNifDesplazado = "BCDEFGHJKLMNPQRSTUVWXYZA";
		idUsr = idUsr.substring(0, 4) + alfabetoNifDesplazado.charAt(alfabetoNif.indexOf(idUsr.charAt(4)));
	}

	public void setFechaAlta(Fecha fechaAlta) throws ModeloException {
		assert fechaAlta != null;
		this.fechaAlta = fechaAlta;
		if (fechaAltaValida(fechaAlta)) {
			this.fechaAlta = fechaAlta;
		}
		else {
			throw new ModeloException("Persona: fecha alta no válida.");
		}
	}
	
	/**
	 * Comprueba validez de una fecha de alta.
	 * @param fechaAlta.
	 * @return true si cumple.
	 */
	private boolean fechaAltaValida(Fecha fechaAlta) {
		// Debe ser hoy o fecha del pasado.
		if (fechaAlta.compareTo(new Fecha())  <= 0 ) {
			return true;
		}
		return false;
	}

	public void setClaveAcceso(ClaveAcceso claveAcceso) {
		assert claveAcceso != null;
		this.claveAcceso = claveAcceso;
	}
	
	public void setRol(RolUsuario rol) {
		assert rol != null;
		this.rol = rol;
	}
	
	public String getIdUsr() {
		return idUsr;
	}
	
	public Fecha getFechaAlta() {
		return fechaAlta;
	}
	
	public ClaveAcceso getClaveAcceso() {
		return claveAcceso;
	}

	public RolUsuario getRol() {
		return rol;
	}

	@Override
	public Usuario clone() {
		return new Usuario(this);	
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((claveAcceso == null) ? 0 : claveAcceso.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((idUsr == null) ? 0 : idUsr.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
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
			if (nif.equals(((Usuario)obj).nif) 
					&& nombre.equals(((Usuario)obj).nombre) 
					&& apellidos.equals(((Usuario)obj).apellidos) 
					&& idUsr.equals(((Usuario)obj).idUsr) 
					&& domicilio.equals(((Usuario)obj).domicilio) 
					&& correo.equals(((Usuario)obj).correo) 
					&& fechaNacimiento.equals(((Usuario)obj).fechaNacimiento) 
					&& fechaAlta.equals(((Usuario)obj).fechaAlta) 
					&& claveAcceso.equals(((Usuario)obj).claveAcceso) 
					&& rol.equals(((Usuario)obj).rol) 
				) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Redefine el método heredado de la clase Objecto.
	 * @return el texto formateado del estado -valores de atributos- de objeto de la clase Usuario.  
	 */
	@Override
	public String toString() {
		return String.format(
				"%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n", 
						"nif:", nif, "nombre:", nombre, "apellidos:", apellidos, "idUsr:", idUsr, 
						"domicilio:", domicilio, "correo:", correo, "fechaNacimiento:", fechaNacimiento, 
						"fechaAlta:", fechaAlta, "claveAcceso:", claveAcceso, "rol:", rol);		
	}

} // class

