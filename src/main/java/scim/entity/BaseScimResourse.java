package scim.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "schemas", "id", "externalId" })
public class BaseScimResourse {
	@JsonProperty("schemas")
	protected Set<String> schemas;
	
	protected String id;
	protected String externalId;
	protected Meta meta;
	
	public BaseScimResourse(){
		schemas = new HashSet<String>();
		meta = new Meta();
	}
	public Set<String> getSchemas() {
		return schemas;
	}

	public void setSchemas(Set<String> schemas) {
		this.schemas = schemas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public Meta getMeta() {
		if(meta == null)
			meta = new Meta();
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@Override
	public String toString() {
		return "BaseClass [schemas=" + schemas + ", id=" + id + ", meta=" + meta + "]";
	}
	
}
