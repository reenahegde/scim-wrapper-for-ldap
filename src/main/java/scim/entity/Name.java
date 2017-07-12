package scim.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Name {
	private String formatted;
	private String familyName;
	private String givenName;
	private String middleName;
	private String honorificPrefix;
	private String honorificSuffix;
	
	/**
	 * Gets the formatted name, If not set, sets the formatted name 
	 * formatted => honorificPrefix givenName middleNameFirstLetter familyName, honorificSuffix;
	 * @return formatted
	 */
	public String getFormatted() {
		if(null==formatted||formatted.isEmpty()){
			//formatted = honorificPrefix+" "+givenName+" "+middleName+" "+familyName+", "+honorificSuffix;
			StringBuffer str = new StringBuffer();
			if(honorificPrefix!=null && !honorificPrefix.isEmpty()){
				str.append(honorificPrefix);
				str.append(" ");
			}
			if(givenName!=null && !givenName.isEmpty()){
				str.append(givenName);
				str.append(" ");
			}
			
			if(middleName!=null && !middleName.isEmpty()){
				str.append(middleName.charAt(0));
				str.append(" ");
			}
			if(familyName!=null && !familyName.isEmpty()){
				str.append(familyName);
			}
			if(honorificSuffix!=null && !honorificSuffix.isEmpty()){
				str.append(", ");
				str.append(honorificSuffix);
			}
			formatted = str.toString().trim();
		}
		return formatted;
	}
	public void setFormatted(String formatted) {
		this.formatted = formatted;
	}
	@JsonInclude(Include.NON_EMPTY)
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	@JsonInclude(Include.NON_EMPTY)
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@JsonInclude(Include.NON_EMPTY)
	public String getHonorificPrefix() {
		return honorificPrefix;
	}
	public void setHonorificPrefix(String honorificPrefix) {
		this.honorificPrefix = honorificPrefix;
	}
	@JsonInclude(Include.NON_EMPTY)
	public String getHonorificSuffix() {
		return honorificSuffix;
	}
	public void setHonorificSuffix(String honorificSuffix) {
		this.honorificSuffix = honorificSuffix;
	}
	@Override
	public String toString() {
		return "Name [formatted=" + formatted + ", familyName=" + familyName + ", givenName=" + givenName
				+ ", middleName=" + middleName + ", honorificPrefix=" + honorificPrefix + ", honorificSuffix="
				+ honorificSuffix + "]";
	}
	
	
}
