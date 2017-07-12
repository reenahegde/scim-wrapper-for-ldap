/**
 * 
 */
package scim.error;

/**
 * @author AkshathaKadri
 *
 */
public class ScimResourseNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String objectId;

	public ScimResourseNotFound(String objectId){
		this.objectId = objectId;
	}
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
}
