package ldap;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;

import util.ScimConstants;

public class AddEntry {

	public static void main( String[] args ) {
		LDAPAttributeSet attributeSet = new LDAPAttributeSet(); 

		/**
		 	cn=JSmith1,ou=users,o=people; 
			LDAPAttributeSet: 
			LDAPAttribute: {type='location', value='http://localhost:8080/Users/SATH0000JSmith1'} 
				LDAPAttribute: {type='cn', values='James Smith','Jim Smith','Jimmy Smith','JSmith'} 
				LDAPAttribute: {type='telephonenumber', value='1 801 555 1212'} 
				LDAPAttribute: {type='resType', value='User'} 
				LDAPAttribute: {type='uid', value='JSmith1'} 
				LDAPAttribute: {type='created', value='Wed Jul 05 18:18:05 CDT 2017'} 
				LDAPAttribute: {type='mail', value='JSmith1@kirkland.com'} 
				LDAPAttribute: {type='lastModified', value='Wed Jul 05 18:18:05 CDT 2017'} 
				LDAPAttribute: {type='version', value='1'} 
				LDAPAttribute: {type='id', value='SATH0000JSmith1'} 
				LDAPAttribute: {type='sn', value='Smith1'} 
				LDAPAttribute: {type='objectclass', value='inetOrgPerson'} 
				LDAPAttribute: {type='givenname', values='James','Jim','Jimmy'}
		 */
		String lNN = "1";
		attributeSet.add( new LDAPAttribute("objectclass", new String("inetOrgPerson")));                
		attributeSet.add( new LDAPAttribute("cn",new String[]{"James Smith"+lNN, "Jim Smith"+lNN, "Jimmy Smith"+lNN}));               
		attributeSet.add( new LDAPAttribute("givenname",new String[]{"James", "Jim", "Jimmy" }));        
		attributeSet.add( new LDAPAttribute("sn", new String("Smith"+lNN)));        
		attributeSet.add( new LDAPAttribute("telephonenumber",new String("1 801 555 1212")));                                                     
		attributeSet.add( new LDAPAttribute("mail",new String("JSmith"+lNN+"@Acme.com")));
		attributeSet.add( new LDAPAttribute("userpassword",new String("newpassword")));                                           
		
		//attributeSet.add( new LDAPAttribute("id",new String("SATH0000JSmith"+lNN)));   
		attributeSet.add( new LDAPAttribute("uid",new String("SATH0000JSmith"+lNN)));   
		
		attributeSet.add( new LDAPAttribute("street",new String("1|Wed Jul 05 18:18:05 CDT 2017|Wed Jul 05 18:18:05 CDT 2017|User")));  
		//attributeSet.add( new LDAPAttribute("metainfo",new String[]{"1","Wed Jul 05 18:18:05 CDT 2017","Wed Jul 05 18:18:05 CDT 2017","User"}));   
		/*attributeSet.add( new LDAPAttribute("lastModified",new String("Wed Jul 05 18:18:05 CDT 2017")));     
		attributeSet.add( new LDAPAttribute("created",new String("Wed Jul 05 18:18:05 CDT 2017")));   
		attributeSet.add( new LDAPAttribute("resType",new String("User")));   
		attributeSet.add( new LDAPAttribute("location",new String("http://localhost:8080/Users/SATH0000JSmith1")));  */ 
		String  dn  = "cn=JSmith"+lNN+"," + ScimConstants.USER_CONTAINER;      
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
