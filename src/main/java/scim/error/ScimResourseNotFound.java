/**
 * 
 */
package scim.error;

/**
 * @author AkshathaKadri
 *
 */
public class ScimResourseNotFound extends ScimBaseException {

	public static final String code = "404";
	public static String template = "Resource %s not found";
	
	public ScimResourseNotFound(String id) {
		super(code, String.format(template,id));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
