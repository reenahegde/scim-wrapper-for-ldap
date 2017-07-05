package entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "schemas", "id" })
public class BaseClass {
	@JsonProperty("schemas")
	protected Set<String> schemas = new HashSet<String>();
	
	protected String id;

	protected Set<String> getSchemas() {
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

	@Override
	public String toString() {
		return "BaseClass [schemas=" + schemas + ", id=" + id + "]";
	}
	
}
