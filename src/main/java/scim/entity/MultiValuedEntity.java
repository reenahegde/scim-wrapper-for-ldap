package scim.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MultiValuedEntity {
	private String value;
	private String type;
	private String primary;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@JsonInclude(Include.NON_NULL)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@JsonInclude(Include.NON_NULL)
	public String getPrimary() {
		return primary;
	}
	/**
	 * Set this parameter as 'true' only if it is primary
	 * Ignore in all other cases
	 * @param primary
	 */
	public void setPrimary(String primary) {
		this.primary = primary;
	}
}
