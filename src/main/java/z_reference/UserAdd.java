package z_reference;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;

import ldap.SSL_Connection;
import scim.entity.ScimUser;
import util.ScimUtils;

public class UserAdd {

	//TODO: change return type to User
	public static void addUser(ScimUser user) {
		LDAPEntry entry = ScimUtils.getLdapUserEntry(user);
		try {
			LDAPConnection lc = SSL_Connection.getConnection();
			
			lc.add(entry);
			System.out.println( "\nAdded object: " + user.getId() + " successfully." );

			lc.disconnect();
		}	catch( LDAPException e ) {
			System.out.println( "Error: " + e.toString() );
		}
	}
}
