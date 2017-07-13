/**
 * 
 */
package scim.error;

/**
 * @author AkshathaKadri
 *
 */
public class ScimBaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String detail;
	protected String status;
	protected String scimType;

	public ScimBaseException(String status) {
		super();
		this.status = status;
	}
	
	public ScimBaseException(String status, String detail) {
		super();
		this.detail = detail;
		this.status = status;
	}
	
	public ScimBaseException(String status, String detail, String scimType) {
		super();
		this.detail = detail;
		this.status = status;
		this.scimType = scimType;
	}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getScimType() {
		return scimType;
	}

	public void setScimType(String scimType) {
		this.scimType = scimType;
	}
}
