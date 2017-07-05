package scim.ldap;
import java.util.ArrayList;
import java.util.List;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

import entity.User;
import ldap.SSL_Connection;
import util.ScimUtils;

public class UserSearchService{
	static String ldapHost = "192.168.1.11";
	//static String ldapHost = "10.0.8.54";

	static int ldapPort 		= LDAPConnection.DEFAULT_PORT;
	static String scope 		= "ou=users,o=people";
	static String loginDN       = "cn=admin,ou=services,o=system";
	static String password      = "abcd1234";
	static String containerName = "o=people";
	
	//private final static AtomicLong counter = new AtomicLong();
	public static void main(String[] args)	{
		String search2 = "(givenName=James)";
		search(search2);
	}

	public static List<User> search(String search){
		LDAPConnection lc = SSL_Connection.getConnection();
		try {
			System.out.println("Ldap connection successful, Search: "+search);
			LDAPSearchResults searchResults = lc.search(scope, 
					LDAPConnection.SCOPE_SUB, search, null, false);
			List<User> userRet = new ArrayList<>();
			int i=0;
			System.out.print("Search results: ");
			while (searchResults.hasMore()) {
				LDAPEntry nextEntry = searchResults.next();
				userRet.add(ScimUtils.getUser(nextEntry));
				System.out.print(++i+" ");
			}
			System.out.print("\nUsers------> Search service: "+userRet.size()+" | ");
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

}
