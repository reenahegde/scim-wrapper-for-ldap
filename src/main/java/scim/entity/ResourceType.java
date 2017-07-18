/**
 * 
 */
package scim.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import scim.util.ScimConstants;

/**
 * @author AkshathaKadri
 *
 */
@JsonPropertyOrder({ "schemas", "id", "name", "endpoint", "description", "schema", "schemaExtensions","meta" })
public class ResourceType {
	@JsonProperty("schemas")
	protected Set<String> schemas;
	private String id;
	private String name;
	private String endpoint;
	private String description;
	private String schema;
	private ArrayList<Schema> schemaExtensions;
	private Meta meta;

	/**
	 * 
	 */
	public ResourceType() {
		schemas = new HashSet<>();
		schemas.add(ScimConstants.SCHEMA_RESOURCE_TYPE);
		meta = new Meta(true);
		schemaExtensions = new ArrayList<Schema>();
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @return the schemaExtensions
	 */
	public ArrayList<Schema> getSchemaExtensions() {
		return schemaExtensions;
	}

	/**
	 * @param schemaExtensions the schemaExtensions to set
	 */
	public void setSchemaExtensions(Schema schema) {
		this.schemaExtensions.add(schema);
	}

	/**
	 * @return the meta
	 */
	public Meta getMeta() {
		return meta;
	}

	/**
	 * @param meta the meta to set
	 */
	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResourceType [schemas=" + schemas + ", id=" + id + ", name=" + name + ", endpoint=" + endpoint
				+ ", description=" + description + ", schema=" + schema + ", schemaExtensions=" + schemaExtensions
				+ ", meta=" + meta + "]";
	}

}
