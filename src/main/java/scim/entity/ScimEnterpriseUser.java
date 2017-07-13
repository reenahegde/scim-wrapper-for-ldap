package scim.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import scim.util.ScimConstants;

@JsonInclude(Include.NON_EMPTY)
public class ScimEnterpriseUser extends ScimUser{

	private String employeeNumber;
	
	public ScimEnterpriseUser() {
		schemas.add(ScimConstants.SCHEMA_ENT_USER);
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

}
