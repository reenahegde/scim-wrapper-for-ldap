/**
 * 
 */
package scim.ldap;

import java.util.List;

import scim.entity.PatchOp;
import scim.entity.ScimUser;

/**
 * @author AkshathaKadri
 *
 */
public interface UserService {

	/**
	 * search
	 * @param search
	 * @return
	 */
	List<ScimUser> search(String search);

	/**
	 * getUser
	 * @param query
	 * @param value
	 * @return
	 */
	ScimUser getUser(String query, String value);

	/**
	 * getUserById
	 * @param id
	 * @return
	 */
	ScimUser getUserById(String id);

	/**
	 * addUser
	 * @param user
	 * @return
	 */
	ScimUser addUser(ScimUser user);

	/**
	 * deleteUser
	 * @param id
	 * @return
	 */
	boolean deleteUser(String id);

	/**
	 * replaceUser
	 * @param id
	 * @param user
	 * @return
	 */
	ScimUser replaceUser(String id, ScimUser user);

	/**
	 * patchUser
	 * @param id
	 * @param patchOp
	 * @return updated User
	 */
	ScimUser patchUser(String id, PatchOp patchOp);

}
