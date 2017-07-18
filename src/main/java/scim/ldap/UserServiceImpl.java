package scim.ldap;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;

import scim.entity.Meta;
import scim.entity.ScimUser;
import scim.error.ScimBadRequest;
import scim.error.ScimConflictException;
import scim.error.ScimResourseNotFound;
import scim.util.SSL_Connection;
import scim.util.ScimConstants;
import scim.util.ScimUtils;
import scim.util.ScimUtils.Mutability;
@Repository
public class UserServiceImpl implements UserService {

	//private final static AtomicLong counter = new AtomicLong();
	public static void main(String[] args)	{
		String search1 = "(mail=JSmith2@kirkland.com)";
		//String search2 = "(givenName=James)";
		new UserServiceImpl().search(search1);
	}

	@Override
	public  List<ScimUser> search(String search){
		LDAPConnection lc = SSL_Connection.getConnection();
		try {
			System.out.println("Ldap connection successful, Search: "+search);
			LDAPSearchResults searchResults = lc.search(ScimConstants.USER_CONTAINER, 
					LDAPConnection.SCOPE_SUB, search, null, false);
			List<ScimUser> userRet = new ArrayList<>();
			int i=0;
			System.out.print("Search results: ");
			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = searchResults.next();
				userRet.add(ScimUtils.getScimUser(nextEntry));
				System.out.print(++i+" ");
			}
			System.out.print("\nNo of Users------> Search service: "+userRet.size()+" | ");
			// disconnect with the server
			lc.disconnect();
			return userRet;
		}
		catch (LDAPException e) {
			System.out.println("Error:  " + e.toString());
		}
		System.out.println("Return Null");
		return null;
		//System.exit(0);
	}

	@Override
	public  ScimUser getUserById(String id){
		ScimUser scimUser =  getUser("uid",id);
		if(scimUser == null){
			throw new ScimResourseNotFound(id);
		}
		return scimUser;
	}
	
	@Override
	public  ScimUser getUser(String query, String value){
		ScimUser userRet = null;
		LDAPConnection lc = SSL_Connection.getConnection();
		try {
			System.out.println("Ldap connection successful, Searching "+query+"="+value);
			LDAPSearchResults searchResults = lc.search(ScimConstants.USER_CONTAINER, 
					LDAPConnection.SCOPE_SUB, "("+query+"="+value+")", null, false);

			if (searchResults.hasMore()) {
				LDAPEntry nextEntry = searchResults.next();
				userRet = ScimUtils.getScimUser(nextEntry);
			}
			// disconnect with the server
			lc.disconnect();
		}
		catch (LDAPException e) {
			System.out.println("Error:  " + e.toString());
		} finally {

		}
		return userRet;
	}

	@Override
	public  ScimUser addUser(ScimUser user) {
		user.setId(ScimUtils.generateIdForUser(user.getExternalId()));
		Meta meta = user.getMeta();
		String location = ScimConstants.URI+ScimConstants.USER_PATH+user.getId();
		meta.setLocation(location);
		meta.setResourceType(ScimConstants.USER_RESOURCE_TYPE);
		meta.setVersion(ScimConstants.VERSION);
		user.setMeta(meta);

		LDAPEntry entry = ScimUtils.getLdapEntry(user);
		ScimUser newUser = null;
		try {
			LDAPConnection lc = SSL_Connection.getConnection();
			lc.add(entry);
			System.out.println( "\nAdded object: " + user.getId() + " successfully." );

			newUser = getUserById(user.getId());
			lc.disconnect();
		}	catch( LDAPException e ) {
			//System.out.println( "Error: " + e.toString() );
			throw new ScimConflictException();
		}
		return newUser;
	}

	@Override
	public  boolean deleteUser(String id){
		LDAPConnection lc = SSL_Connection.getConnection();
		try {
			System.out.println("Ldap connection successful, ID: "+id);
			String dn = ScimUtils.getDNFromId(id);
			lc.delete(dn);
			System.out.print(dn+" Deleted");
			// disconnect with the server
			lc.disconnect();
			return true;
		}
		catch (LDAPException e) {
			System.out.println("Error:  " + e.toString());
			return false;
		} 
	}

	@Override
	public  ScimUser replaceUser(String id, ScimUser user){
		System.out.println("In replaceUser "+user);
		LDAPConnection lc = SSL_Connection.getConnection();

		//Get DB user
		ScimUser dbuser= getUserById(id);
		
		if(user.getId()!=null && !user.getId().isEmpty() && user.getId()!=dbuser.getId()){
			throw new ScimBadRequest("id", Mutability.readOnly,"mutability");
		}

		String dn = ScimUtils.getDNFromExternalId(user.getExternalId());

		LDAPModification[] mod = ScimUtils.pushLdapUser(user, dbuser);
		try {	
			lc.modify(dn, mod);
			System.out.print(dn+" Updated");
			//Get DB user
			dbuser= getUserById(id);
			// disconnect with the server
			lc.disconnect();
			return dbuser;
		}
		catch (LDAPException e) {
			System.out.println("Error:  " + e.toString());
			return null;
		} 
	}

	@Override
	public ScimUser updateUser(String id, ScimUser user) {
		System.out.println("In updateUser "+user);
		LDAPConnection lc = SSL_Connection.getConnection();

		//Get DB user
		ScimUser dbuser= getUserById(id);
		
		if(user.getId()!=null && !user.getId().isEmpty() && user.getId()!=dbuser.getId()){
			throw new ScimBadRequest("id", Mutability.readOnly,"mutability");
		}

		String dn = ScimUtils.getDNFromExternalId(user.getExternalId());

		LDAPModification[] mod = ScimUtils.getLdapUserMod(user, dbuser);
		try {	
			lc.modify(dn, mod);
			System.out.print(dn+" Updated");
			//Get DB user
			dbuser= getUserById(id);
			// disconnect with the server
			lc.disconnect();
			return dbuser;
		}
		catch (LDAPException e) {
			System.out.println("Error:  " + e.toString());
			return null;
		} 
	}
	
}
