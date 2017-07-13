/**
 * 
 */
package scim.error;

import scim.util.ScimConstants;

/**
 * @author AkshathaKadri
 *
 */
public class ScimConflictException extends ScimBaseException {

	private static final long serialVersionUID = 1L;
	
	private static String template = "%s already exists";
	private static final String code = "409";
	
	public ScimConflictException() {
		super(code,String.format(template, ScimConstants.USER_RESOURCE_TYPE));
	}
	
	public ScimConflictException(String detail) {
		super(code,detail);
	}
	
	public ScimConflictException(String detail,String scimType) {
		super(code,detail);
	}
	
}
