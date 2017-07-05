package reference;
//Sample code file: var/ndk/webBuildengine/tmp/viewable_samples/f91a68eb-ad37-4526-92b1-b1938f37b871/security/SSLConnection.java //Warning: This code has been marked up for HTML

import java.io.UnsupportedEncodingException;
import java.security.Security;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPJSSESecureSocketFactory;
import com.novell.ldap.LDAPSocketFactory;

public class SSL_Connection_Working {

	static final String KEYSTORE = "C:\\Program Files\\Java\\jdk1.8.0_131\\jre\\lib\\security\\cacerts1";

	  public static void main(String args[])
	
	  {
	      int ldapPort = LDAPConnection.DEFAULT_SSL_PORT;
	
	      int ldapVersion = LDAPConnection.LDAP_V3;
	
	      /*String ldapHost = args[0];
	
	      String loginDN = args[1];
	
	      String password = args[2];
	
	      String path = args[3];*/
	
	      LDAPSocketFactory ssf;
	
	      String ldapHost       = "192.168.1.11";
	
	      String loginDN        = "cn=admin,ou=services,o=system";
	
	      String password       = "admin123";
	
	      String containerName  = "ou=users,o=people";
	
	      LDAPAttribute  attribute = null;
	
	      LDAPAttributeSet attributeSet = new LDAPAttributeSet();
	  
	      
	      try {
	
	         // Dynamically set JSSE as a security provider
	
	
	          Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	
	
	
	         // Dynamically set the property that JSSE uses to identify
	
	
	         // the keystore that holds trusted root certificates
	
	
	          System.setProperty("javax.net.ssl.trustStore", KEYSTORE);
	
	
	
	          ssf = new LDAPJSSESecureSocketFactory();
	
	
	
	         // Set the socket factory as the default for all future connections
	
	
	          LDAPConnection.setSocketFactory(ssf);
	
	
	
	         // Note: the socket factory can also be passed in as a parameter
	
	
	         // to the constructor to set it for this connection only.
	
	
	          LDAPConnection lc = new LDAPConnection();
	
	
	
	         // connect to the server
	
	
	          lc.connect( ldapHost, ldapPort );
	
	
	
	         // authenticate to the server
	
	
	          lc.bind( ldapVersion, loginDN, password.getBytes("UTF8") );
	
	
	
	         // at this point you are connected with a secure connection
	
	
	          System.out.println( "Successful SSL bind with server.");
	
	          
	          lc.disconnect();
	
	      }
	
	      catch( LDAPException e ) {
	
	          System.out.println( "Error: " + e.toString() );
	
	      }
	
	      catch( UnsupportedEncodingException e ) {
	
	          System.out.println( "Error: " + e.toString() );
	
	      }
	
	  }
	public static LDAPConnection getConnection(boolean isTest) {
		int ldapPort = LDAPConnection.DEFAULT_SSL_PORT;
	
		int ldapVersion = LDAPConnection.LDAP_V3;
		String ldapHost       = "192.168.1.11";
		String loginDN        = "cn=admin,ou=services,o=system";
		String password       = "admin123";
	
		/*
		String ldapHost       = "10.0.8.54";
		String loginDN        = "cn=admin,ou=services,o=system";
		String password       = "abcd1234";
		String containerName  = "o=people";
		*/
		
		// to the constructor to set it for this connection only.
		LDAPConnection lc = new LDAPConnection();
	
		try {
	
			// Dynamically set JSSE as a security provider
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	
			// Dynamically set the property that JSSE uses to identify
			// the keystore that holds trusted root certificates
			System.setProperty("javax.net.ssl.trustStore", KEYSTORE);
	
			LDAPSocketFactory ssf = new LDAPJSSESecureSocketFactory();
	
			// Set the socket factory as the default for all future connections
			LDAPConnection.setSocketFactory(ssf);
	
			// Note: the socket factory can also be passed in as a parameter
			// connect to the server
			lc.connect( ldapHost, ldapPort );
	
			// authenticate to the server
			lc.bind( ldapVersion, loginDN, password.getBytes("UTF8") );
	
			// at this point you are connected with a secure connection
			System.out.println( "Successful SSL bind with server.");
	
		} catch( LDAPException e ) {
			System.out.println( "Error: " + e.toString() );
		} catch( UnsupportedEncodingException e ) {
			System.out.println( "Error: " + e.toString() );
		}
		return lc;
	}
}
