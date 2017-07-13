/**
 * 
 */
package scim.error;

import scim.util.ScimUtils.Mutability;

/**
 * @author AkshathaKadri
 *
 */
public class ScimBadRequest extends ScimBaseException {

	private static final long serialVersionUID = 1L;
	
	private static String template = "Attribute ’%s’ is %s";
	private static String detail = "Attribute 'id' is readOnly";
	private static final String code = "400";
	
	public ScimBadRequest() {
		super(code,detail);
	}
	
	public ScimBadRequest(String detail) {
		super(code,detail);
	}
	
	public ScimBadRequest(String detail,String scimType) {
		super(code,detail);
	}
	
	public ScimBadRequest(String attr, Mutability mutability) {
		super(code, String.format(template,attr, mutability.toString()));
	}
	
	public ScimBadRequest(String attr, Mutability mutability, String scimType) {
		super(code, String.format(template,attr, mutability.toString()),scimType);
	}
}
