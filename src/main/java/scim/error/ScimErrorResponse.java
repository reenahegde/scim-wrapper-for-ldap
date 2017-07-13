/**
 * 
 */
package scim.error;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import scim.util.ScimConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author AkshathaKadri
 *
 */
@JsonPropertyOrder({"schemas","scimType","detail","status"})
public class ScimErrorResponse{

	@JsonProperty("schemas")
	protected Set<String> schemas;
	protected String detail;
	protected String status;
	protected String scimType;
	
	public ScimErrorResponse(String status, String detail, String scimType) {
		schemas = new HashSet<String>();
		schemas.add(ScimConstants.SCHEMA_ERROR);
		this.status = status;
		this.detail = detail;
		this.scimType = scimType;
	}
	
	public ScimErrorResponse(String status, String detail) {
		schemas = new HashSet<String>();
		schemas.add(ScimConstants.SCHEMA_ERROR);
		this.status = status;
		this.detail = detail;
	}
	
	public ScimErrorResponse() {
		schemas = new HashSet<String>();
		schemas.add(ScimConstants.SCHEMA_ERROR);
	}
	
	public Set<String> getSchemas() {
		return schemas;
	}
	public void setSchemas(String schema) {
		if(schemas == null)
			this.schemas = new HashSet<>();
		this.schemas.add(schema);
	}
	@JsonInclude(Include.NON_NULL)
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
	@JsonInclude(Include.NON_NULL)
	public String getScimType() {
		return scimType;
	}

	public void setScimType(String scimType) {
		this.scimType = scimType;
	}
}
