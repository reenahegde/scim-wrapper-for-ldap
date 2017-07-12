package ldap;

import java.util.ArrayList;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;

import util.ScimConstants;

public class ModifyAttrs {

	public static void main(String[] args) {
		int returnCode = 0;
		String  dn  = "cn=Barbara," + ScimConstants.USER_CONTAINER;
		ArrayList<LDAPModification> modList = new ArrayList<LDAPModification>();
		/*
		 Date currentDate = new Date();
		 String desc ="This object was modified at " + new Date(currentDate.getTime());

		// Add a new value to the description attribute
		LDAPAttribute attribute = new LDAPAttribute("description", desc);
		modList.add(new LDAPModification(LDAPModification.ADD, attribute));*/

		// Replace all values the E-mail address with a new value
		String email = "Jbarbara@Acme.com";
		LDAPAttribute attribute = new LDAPAttribute("mail", email);
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
				System.exit(1);
			}
		}

		// Note: All the above modifications will be performed as an atomic unit, if all do not succeed, 
		// the operation fails and the directory is unchanged by this operation.
		try {
			lc.modify( dn, mods);
			System.out.println("Successfully modified the attributes of the entry.");
		} catch (LDAPException e2) {
			System.err.println("Error: " + e2.toString());
			returnCode = 1;
			// Didn't change the directory, delete initial phone number value
			try {
				lc.modify(dn, new LDAPModification(LDAPModification.DELETE,new LDAPAttribute("telephoneNumber", phone1)));
			} catch (Exception e) {
				// ignore
			}
		} finally {
			/*if (returnCode == 0) {
				try {
					// Cleanup - get rid of the updated description, mail, and telephone attribute values
					// We delete only those values that we added. If the value deleted is the only value of the respective
					// attribute, then the attribute is deleted.
					modList.clear();
					modList.add(new LDAPModification(LDAPModification.DELETE,new LDAPAttribute("description", desc)));
					modList.add(new LDAPModification(LDAPModification.DELETE,new LDAPAttribute("mail", email)));
					modList.add(new LDAPModification(LDAPModification.DELETE,new LDAPAttribute("telephoneNumber", phone2)));
					mods = new LDAPModification[modList.size()];
					mods = (LDAPModification[]) modList.toArray(mods);
					lc.modify(dn, mods);
				} catch (Exception e) {
					// ignore
				}
			}*/
			// Always disconnect
			try {
				lc.disconnect();
			} catch (Exception e) {
				// ignore
			}
		}
		System.exit(returnCode);
	}
	
}
