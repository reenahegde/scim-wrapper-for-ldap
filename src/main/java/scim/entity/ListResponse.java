package scim.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import scim.util.ScimConstants;

/**
 * @author AkshathaKadri
 *
 */
public class ListResponse {
	@JsonProperty("schemas")
	protected Set<String> schemas;
	private int totalResults;
	private int itemsPerPage;
	private int startIndex;
	private Set<Object> Resources;
	
	public ListResponse() {
		schemas = new HashSet<>();
		schemas.add(ScimConstants.SCHEMA_LIST_RESPONSE);
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
	 * @return the totalResults
	 */
	public int getTotalResults() {
		return totalResults;
	}
	/**
	 * @param totalResults the totalResults to set
	 */
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	/**
	 * @return the itemsPerPage
	 */
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	/**
	 * @param itemsPerPage the itemsPerPage to set
	 */
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}
	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	/**
	 * @return the resources
	 */
	public Set<Object> getResources() {
		return Resources;
	}
	/**
	 * @param resources the resources to set
	 */
	public void setResources(Set<Object> resources) {
		Resources = resources;
	}
	
}
