package entity;

import java.net.URI;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "resourceType", "created" , "lastModified", "location", "version"})
public class Meta {

	private String resourceType;

	private Calendar created;

	private Calendar lastModified;

	private URI location;

	private String version;

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getLastModified() {
		return lastModified;
	}

	public void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}

	public URI getLocation() {
		return location;
	}

	public void setLocation(URI location) {
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
