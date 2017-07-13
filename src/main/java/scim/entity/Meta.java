package scim.entity;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import util.ScimConstants;

@JsonPropertyOrder({ "resourceType", "created" , "lastModified", "location", "version"})
public class Meta {

	private String resourceType;

	private String created;

	private String lastModified;

	private String location;

	private String version;

	public Meta(){
		created = Calendar.getInstance().getTime().toString();
		lastModified =  created;
		version = ScimConstants.VERSION;
	}
	
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Meta [resourceType=" + resourceType + ", created=" + created + ", lastModified=" + lastModified
				+ ", location=" + location + ", version=" + version + "]";
	}
	
}
