/**
 * 
 */
package scim.error;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import util.ScimConstants;

/**
 * @author AkshathaKadri
 *
 */
@JsonPropertyOrder({"schemas","status","detail"})
public class ScimErrorResponse{

	@JsonProperty("schemas")
	protected Set<String> schemas;
	protected String detail;
	protected String status;
	
	public ScimErrorResponse() {
		schemas = new HashSet<String>();
		schemas.add(ScimConstants.SCHEMA_ERROR);
	}
	
	/*public ScimError(SCIM_CODES status) {
		super();
		schemas = new HashSet<String>();
		schemas.add(ScimConstants.SCHEMA_ERROR);
		this.detail = ScimHttpCodes.MsgPrefix+status;
		this.status = ScimHttpCodes.CodePrefix+status;
	}*/
	public Set<String> getSchemas() {
		return schemas;
	}
	public void setSchemas(String schema) {
		if(schemas == null)
			this.schemas = new HashSet<>();
		this.schemas.add(schema);
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
