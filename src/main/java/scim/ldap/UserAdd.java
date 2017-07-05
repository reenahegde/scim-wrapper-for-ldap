package scim.ldap;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;

import entity.User;
import ldap.SSL_Connection;
import util.ScimUtils;

public class UserAdd {

	//TODO: change return type to User
	public static void addUser(User user) {
		LDAPEntry entry = ScimUtils.getLdapUserEntry(user);
		try {
			LDAPConnection lc = SSL_Connection.getConnection();
			
			lc.add(entry);
			System.out.println( "\nAdded object: " + user.getDn() + " successfully." );

			lc.disconnect();
		}	catch( LDAPException e ) {
			System.out.println( "Error: " + e.toString() );
		}
	}
}
