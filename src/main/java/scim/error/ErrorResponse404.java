/**
 * 
 */
package scim.error;

/**
 * @author AkshathaKadri
 *
 */
public class ErrorResponse404 extends ScimErrorResponse {
	public static final String code = "404";
	public static String template = "Resource %s not found";
	
	/**
	 * 
	 * @param id
	 */
	public ErrorResponse404(String id) {
		super();
		this.detail = String.format(template,id);
		this.status = code;
	}

}
