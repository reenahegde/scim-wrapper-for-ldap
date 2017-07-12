package scim.ldap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPSearchResults;

import ldap.SSL_Connection;
import scim.entity.Meta;
import scim.entity.ScimUser;
import scim.error.ScimResourceInvalid;
import util.ScimConstants;
import util.ScimUtils;

public class UserService{
	static String ldapHost = "192.168.1.11";
	//static String ldapHost = "10.0.8.54";

	static int ldapPort 		= LDAPConnection.DEFAULT_PORT;
	static String scope 		= ScimConstants.USER_CONTAINER;
	static String loginDN       = "cn=admin,ou=services,o=system";
	static String password      = "abcd1234";
	static String containerName = "o=people";

	//private final static AtomicLong counter = new AtomicLong();
	public static void main(String[] args)	{
		String search1 = "(mail=JSmith2@kirkland.com)";
		//String search2 = "(givenName=James)";
		search(search1);
	}

	public static List<ScimUser> search(String search){
		LDAPConnection lc = SSL_Connection.getConnection();
		try {
			System.out.println("Ldap connection successful, Search: "+search);
			LDAPSearchResults searchResults = lc.search(scope, 
					LDAPConnection.SCOPE_SUB, search, null, false);
			List<ScimUser> userRet = new ArrayList<>();
			int i=0;
			System.out.print("Search results: ");
			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = searchResults.next();
				userRet.add(ScimUtils.getUser(nextEntry));
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

	public static ScimUser getUser(String id){
		return getUser("uid",id);
	}
	public static ScimUser getUser(String query, String value){
		ScimUser userRet = null;
		LDAPConnection lc = SSL_Connection.getConnection();
		try {
			System.out.println("Ldap connection successful, Searching "+query+"="+value);
			LDAPSearchResults searchResults = lc.search(scope, 
					LDAPConnection.SCOPE_SUB, "("+query+"="+value+")", null, false);

			if (searchResults.hasMore()) {
				LDAPEntry nextEntry = searchResults.next();
				userRet = ScimUtils.getUser(nextEntry);
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

	public static ScimUser addUser(ScimUser user) {
		user.setId(ScimUtils.generateIdForUser(user.getExternalId()));
		Meta meta = user.getMeta();
		String location = ScimConstants.USER_URI+user.getId();
		if(meta==null){
			meta = new Meta();
		}
		meta.setLocation(location);
		meta.setResourceType(ScimConstants.USER_RESOURCE_TYPE);
		meta.setVersion(ScimConstants.VERSION);
		user.setMeta(meta);

		LDAPEntry entry = ScimUtils.getLdapUserEntry(user);
		ScimUser newUser = null;
		try {
			LDAPConnection lc = SSL_Connection.getConnection();
			lc.add(entry);
			System.out.println( "\nAdded object: " + user.getId() + " successfully." );

			newUser = getUser(user.getId());
			lc.disconnect();
		}	catch( LDAPException e ) {
			System.out.println( "Error: " + e.toString() );
		}
		return newUser;
	}

	public static boolean deleteUser(String id){
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

	public static boolean replaceUser(String id, ScimUser user){
		System.out.println("In replaceUser "+user);
		LDAPConnection lc = SSL_Connection.getConnection();

		//Get DB user
		ScimUser dbuser= UserService.getUser(id);

		if(user.getId()!=null && !user.getId().isEmpty() && user.getId()!=dbuser.getId()){
			//TODO: throw immutability error
			throw new ScimResourceInvalid();
		}

		String dn = ScimUtils.getDNFromExternalId(user.getExternalId());

		LDAPModification[] mod = ScimUtils.pushLdapUser(user, dbuser);
		try {	
			lc.modify(dn, mod);
			System.out.print(dn+" Updated");
			// disconnect with the server
			lc.disconnect();
			return true;
		}
		catch (LDAPException e) {
			System.out.println("Error:  " + e.toString());
			return false;
		} 
	}
	
}
