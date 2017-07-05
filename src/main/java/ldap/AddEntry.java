package ldap;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;

public class AddEntry {

	public static void main( String[] args ) {
		String containerName  = "ou=users,o=people";
		LDAPAttributeSet attributeSet = new LDAPAttributeSet();                  
		attributeSet.add( new LDAPAttribute("objectclass", new String("inetOrgPerson")));                
		attributeSet.add( new LDAPAttribute("cn",new String[]{"James Smith1", "Jim Smith1", "Jimmy Smith1"}));               
		attributeSet.add( new LDAPAttribute("givenname",new String[]{"James", "Jim", "Jimmy" }));        
		attributeSet.add( new LDAPAttribute("sn", new String("Smith1")));        
		attributeSet.add( new LDAPAttribute("telephonenumber",new String("1 801 555 1212")));                                                     
		attributeSet.add( new LDAPAttribute("mail",new String("JSmith1@Acme.com")));
		attributeSet.add( new LDAPAttribute("userpassword",new String("newpassword")));                                           

		String  dn  = "cn=JSmith1," + containerName;      
		LDAPEntry newEntry = new LDAPEntry( dn, attributeSet );

		try {
			new SSL_Connection();
			LDAPConnection lc = SSL_Connection.getConnection();
			
			lc.add(newEntry);
			System.out.println( "\nAdded object: " + dn + " successfully." );

			lc.disconnect();
		}	catch( LDAPException e ) {
			System.out.println( "Error: " + e.toString() );
		}
	}
}
