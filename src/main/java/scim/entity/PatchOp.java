/**
 * 
 */
package scim.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import scim.util.ScimConstants;

/**
 * @author AkshathaKadri
 *
 */
@JsonPropertyOrder({ "schemas", "Operations" })
public class PatchOp {
	@JsonProperty("schemas")
	private Set<String> schemas;
	
	@JsonProperty("Operations")
	private List<PatchOperation> Operations;

	
	/**
	 * 
	 */
	public PatchOp() {
		schemas = new HashSet<>();
		schemas.add(ScimConstants.SCHEMA_PATCH);
		Operations = new ArrayList<PatchOperation>();
	}

	/**
	 * @return the schemas
	 */
	public Set<String> getSchemas() {
		return schemas;
	}

	/**
	 * @param schemas the schemas to set
	 */
	public void setSchemas(Set<String> schemas) {
		this.schemas = schemas;
	}


	/**
	 * @return the operations
	 */
	public List<PatchOperation> getOperations() {
		return Operations;
	}

	/**
	 * @param operations the operations to set
	 */
	public void setOperations(List<PatchOperation> operations) {
		Operations = operations;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PatchOp [schemas=" + schemas + ", operations=" + Operations + "]";
	}
	
	
}
