/** 
 * Proyecto: Juego de la vida.
 *  Clase-utilidades de validación de formatos utilizando regex.
 *  @since: prototipo2.0
 *  @source: Formato.java 
 *  @version: 2.0 - 2018/04/20
 *  @author: ajp
 */

package util;

import java.util.regex.Pattern;

public class Formato {

	public static final String PATRON_URL = "^http://[\\w]+(\\.[\\w-]+)*/([\\w-])*/([\\w-]+)*(\\.[\\w]{1,})";
	public static final String PATRON_URL2 = "^(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\w\\.-/]+)?/?$";
	public static final String PATRON_URL3 = "^(ht|f)tp(s?)://[0-9a-zA-Z]([-\\.\\w]*[0-9a-zA-Z])*(?:(0-9)*)*(?)([a-zA-Z0-9-\\.?,/+&%\\$#_]*)?$";
	public static final String PATRON_ID_YOOTUBE = "^http://(?:youtu\\.be/|(?:[a-z]{2,3}\\.)?youtube\\.com/watch(?:\\?|#\\!)v=)([\\w-]{11}).*/gi";

	public static final String PATRON_CORREO = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$";

	public static final String PATRON_USUARIO = "^[a-z\\d_]{4,15}$";
	public static final String PATRON_CONTRASEÑA1 = "[A-ZÑa-zñ0-9%&#_-]{6,18}";
	public static final String PATRON_CONTRASEÑA2 = "(?=^.{6,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
	public static final String PATRON_CONTRASEÑA3 = "^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%]){6,16}";
	/**
	 * Verifica que una contraseña sea robusta:
	 * al menos una minúscula.
	 * Al menos una mayúscula.
	 * Al menos un número.
	 * Al menos un caracter especial.
	 * Mínimo 6 caracteres.
	 */
	public static final String PATRON_CONTRASEÑA4 = "((?=.{0,}[A-ZÑ])(?=.{0,}[a-zñ])(?=.{1,}\\d)(?=.{1,}[$*-+&!?%#])).{6,16}";
	public static final String PATRON_TELEFONO = "^\\+?\\d{1,3}?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$";
	public static final String PATRON_NUMERO_TELEFONO = "^\\+[0-9]{1,3}[0-9]{2,3}[0-9]{6,7}";
	public static final String PATRON_TARJETA = "^((67\\d{2})|(4\\d{3})|(5[1-5]\\d{2})|(6011))(-?\\s?\\d{4}){3}|(3[4,7])\\d{2}-?\\s?\\d{6}-?\\s?\\d{5}$";

	public static final String PATRON_IP = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	public static final String PATRON_IP2 = "^(([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]).){3}([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
	public static final String PATRON_IP3 = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))";

	public static final String PATRON_NIF = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]";
	public static final String PATRON_NIE = "^[X-Z][0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]";
	public static final String PATRON_CIF = "^[ABCDEFGHJPQRSUVNW][0-9]{7}[A-J0-9]";

			
	public static final String PATRON_FECHA = "^[0-9]{4}[/.-][0-9]{1,2}[/.-][0-9]{1,2}";
	public static final String PATRON_CP = "^([1-9]{2}|[0-9][1-9]|[1-9][0-9])[0-9]{3}$";
	public static final String PATRON_NUMERO_POSTAL = "[\\d]+[\\w]?";

	public static final String PATRON_NOMBRE_PERSONA = "^[A-ZÑ][áéíóúña-z \\w]+";
	public static final String PATRON_APELLIDOS = "^[A-ZÑ][áéíóúña-z]+[ A-ZÑáéíóúñ\\w]*";
	public static final String PATRON_TOPONIMO = "^[A-ZÑ][áéíóúña-z \\w]+";
	public static final String PATRON_NOMBRE_VIA = "^[A-ZÑ][/áéíóúña-z \\w]+";
	
	public static final String PATRON_NOMBRE_MUNDO_JV = "^[A-ZÑ][A-ZÑáéíóúña-z \\d]+";
	public static final String PATRON_NOMBRE_PATRON_JV = "^[A-ZÑ][A-ZÑáéíóúña-z \\d]+";
	
	/**
	 * Verifica que un texto tiene un formato válido.
	 * @param texto - a validar.
	 * @param patron - a utilizar.
	 * @return - true si es correcto.
	 */
	public static boolean validar(String texto,  String patron) {
		return Pattern.compile(patron).matcher(texto).matches();
	}

} // class