package scim.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ConfigAttributes {
	private boolean supported;
	private Long maxOperations;
	private Long maxPayloadSize;
	private Long maxResults;
	/**
	 * @return the supported
	 */
	public boolean getSupported() {
		return supported;
	}
	/**
	 * @param supported the supported to set
	 */
	public void setSupported(boolean supported) {
		this.supported = supported;
	}

	@JsonInclude(Include.NON_DEFAULT)
	public Long getMaxOperations() {
		return maxOperations;
	}
	/**
	 * @param maxOperations the maxOperations to set
	 */
	public void setMaxOperations(Long maxOperations) {
		this.maxOperations = maxOperations;
	}
	/**
	 * @return the maxPayloadSize
	 */
	@JsonInclude(Include.NON_DEFAULT)
	public Long getMaxPayloadSize() {
		return maxPayloadSize;
	}
	/**
	 * @param maxPayloadSize the maxPayloadSize to set
	 */
	public void setMaxPayloadSize(Long maxPayloadSize) {
		this.maxPayloadSize = maxPayloadSize;
	}
	/**
	 * @return the maxResults
	 */
	@JsonInclude(Include.NON_DEFAULT)
	public Long getMaxResults() {
		return maxResults;
	}
	/**
	 * @param maxResults the maxResults to set
	 */
	public void setMaxResults(Long maxResults) {
		this.maxResults = maxResults;
	}
}
