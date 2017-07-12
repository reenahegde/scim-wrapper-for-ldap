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

	public static boolean updateUser(ScimUser user){
		LDAPConnection lc = SSL_Connection.getConnection();

		//Get DB user
		ScimUser dbuser= UserService.getUser(ScimUtils.generateIdForUser(user.getExternalId()));

		if(user.getId()!=null && !user.getId().isEmpty() && user.getId()!=dbuser.getId()){
			//TODO: throw immutability error
		}

		System.out.println("Ldap connection successful, ID: "+user);
		String dn = ScimUtils.getDNFromExternalId(user.getExternalId());

		//TODO: add modifications
		LDAPModification mod = new LDAPModification();
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
	
	public boolean modifyUser(ScimUser user){

		boolean isSuccess = true;
		
		String  dn  = "cn="+user.getExternalId()+"," + ScimConstants.USER_CONTAINER;

		ArrayList<LDAPModification> modList = new ArrayList<LDAPModification>();
		Date currentDate = new Date();
		String desc ="This object was modified at " + new Date(currentDate.getTime());

		// Add a new value to the description attribute
		LDAPAttribute attribute = new LDAPAttribute("description", desc);
		modList.add(new LDAPModification(LDAPModification.ADD, attribute));

		// Replace all values the E-mail address with a new value
		String email = "Jbarbara@Acme.com";
		attribute = new LDAPAttribute("mail", email);
		modList.add(new LDAPModification(LDAPModification.REPLACE, attribute));

		// Change the phone number
		// First we delete the old phone number
		String phone1 = "1 801 555 1212";
		attribute = new LDAPAttribute("telephoneNumber", phone1);
		modList.add(new LDAPModification(LDAPModification.DELETE, attribute));

		// Now we add the new phone number
		String phone2 = "1 423 555 1212";
		attribute = new LDAPAttribute("telephoneNumber", phone2);
		modList.add(new LDAPModification(LDAPModification.ADD, attribute));

		LDAPModification[] mods = new LDAPModification[modList.size()];
		mods = (LDAPModification[]) modList.toArray(mods);
		
		LDAPConnection lc = SSL_Connection.getConnection();
		try {
			// Add a known phone number value so we have something to change
			lc.modify(dn, new LDAPModification(LDAPModification.ADD,new LDAPAttribute("telephoneNumber", "1 801 555 1212")));
		} catch (LDAPException e1) {
			// If phonenumber value already exists, just go on, otherwise it's an error
			if (e1.getResultCode() != LDAPException.ATTRIBUTE_OR_VALUE_EXISTS) {
				System.out.println("Cannot create attribute: " + e1.toString());
				return false;
			}
		}

		try {
			lc.modify( dn, mods);
			System.out.println("Successfully modified the attributes of the entry.");
		} catch (LDAPException e2) {
			System.err.println("Error: " + e2.toString());
			isSuccess = false;
			try {
				lc.modify(dn, new LDAPModification(LDAPModification.DELETE,new LDAPAttribute("telephoneNumber", phone1)));
			} catch (Exception e) {
				// ignore
			}
		} finally {
			try {
				lc.disconnect();
			} catch (Exception e) {
				// ignore
			}
		}
		return isSuccess;
	}
}
