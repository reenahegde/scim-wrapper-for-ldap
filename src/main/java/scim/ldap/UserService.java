/**
 * 
 */
package scim.ldap;

import java.util.List;

import scim.entity.ScimUser;

/**
 * @author AkshathaKadri
 *
 */
public interface UserService {

	List<ScimUser> search(String search);

	ScimUser getUser(String query, String value);

	ScimUser getUserById(String id);

	ScimUser addUser(ScimUser user);

	boolean deleteUser(String id);

	ScimUser replaceUser(String id, ScimUser user);

	ScimUser updateUser(String id, ScimUser user);

}
