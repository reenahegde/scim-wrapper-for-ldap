/**
 * 
 */
package scim.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import scim.util.ScimConstants;

/**
 * @author AkshathaKadri
 *
 */
@JsonPropertyOrder({ "schemas", "documentationUri", "patch", "bulk", "filter", "changePassword", "sort", "etag", "meta" })
public class ServiceProviderConfig {
	@JsonProperty("schemas")
	protected Set<String> schemas;
	private String documentationUri;
	
	private ConfigAttributes patch;
	private ConfigAttributes bulk;
	private ConfigAttributes filter;
	private ConfigAttributes changePassword;
	private ConfigAttributes sort;
	private ConfigAttributes etag;
	
	private Object authenticationSchemes;
	
	private Meta meta;

	/**
	 * 
	 */
	public ServiceProviderConfig() {
		schemas = new HashSet<>();
		schemas.add(ScimConstants.SCHEMA_SERVICE);
		meta = new Meta();
		meta.setLocation(ScimConstants.URI+ScimConstants.SERVICE_RESOURCE_TYPE+ScimConstants.URI_DELIM);
		meta.setResourceType(ScimConstants.SERVICE_RESOURCE_TYPE);
		meta.setVersion(ScimConstants.VERSION);
		
		patch = new ConfigAttributes();
		bulk = new ConfigAttributes();
		filter = new ConfigAttributes();
		changePassword = new ConfigAttributes();
		sort = new ConfigAttributes();
		etag = new ConfigAttributes();
		
		patch.setSupported(true);
		filter.setSupported(true);
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
	 * @return the documentationUri
	 */
	public String getDocumentationUri() {
		return documentationUri;
	}

	/**
	 * @param documentationUri the documentationUri to set
	 */
	public void setDocumentationUri(String documentationUri) {
		this.documentationUri = documentationUri;
	}

	/**
	 * @return the patch
	 */
	public ConfigAttributes getPatch() {
		return patch;
	}

	/**
	 * @param patch the patch to set
	 */
	public void setPatch(ConfigAttributes patch) {
		this.patch = patch;
	}

	/**
	 * @return the bulk
	 */
	public ConfigAttributes getBulk() {
		return bulk;
	}

	/**
	 * @param bulk the bulk to set
	 */
	public void setBulk(ConfigAttributes bulk) {
		this.bulk = bulk;
	}

	/**
	 * @return the filter
	 */
	public ConfigAttributes getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(ConfigAttributes filter) {
		this.filter = filter;
	}

	/**
	 * @return the changePassword
	 */
	public ConfigAttributes getChangePassword() {
		return changePassword;
	}

	/**
	 * @param changePassword the changePassword to set
	 */
	public void setChangePassword(ConfigAttributes changePassword) {
		this.changePassword = changePassword;
	}

	/**
	 * @return the sort
	 */
	public ConfigAttributes getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(ConfigAttributes sort) {
		this.sort = sort;
	}

	/**
	 * @return the etag
	 */
	public ConfigAttributes getEtag() {
		return etag;
	}

	/**
	 * @param etag the etag to set
	 */
	public void setEtag(ConfigAttributes etag) {
		this.etag = etag;
	}

	/**
	 * @return the authenticationSchemes
	 */
	public Object getAuthenticationSchemes() {
		return authenticationSchemes;
	}

	/**
	 * @param authenticationSchemes the authenticationSchemes to set
	 */
	public void setAuthenticationSchemes(Object authenticationSchemes) {
		this.authenticationSchemes = authenticationSchemes;
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
	
}
