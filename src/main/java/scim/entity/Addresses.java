package scim.entity;

public class Addresses {
	private String type;
	private String streetAddress;
	private String locality;
	private String region;
	private String postalCode;
	private String country;
	private String formatted;
	private String primary;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFormatted() {
		if(null==formatted||formatted.isEmpty()){
			//TODO: Use String buffer and checks for null
			formatted = streetAddress+"\n"+locality+", "+region+" "+postalCode+" "+country;
		}
		return formatted;
	}
	public void setFormatted(String formatted) {
		this.formatted = formatted;
	}
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	@Override
	public String toString() {
		return "Addresses [type=" + type + ", streetAddress=" + streetAddress + ", locality=" + locality + ", region="
				+ region + ", postalCode=" + postalCode + ", country=" + country + ", formatted=" + formatted
				+ ", primary=" + primary + "]";
	}
	
}
