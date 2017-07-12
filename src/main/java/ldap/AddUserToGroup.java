package ldap;
//Sample code file: var/ndk/webBuildengine/tmp/viewable_samples/f91a68eb-ad37-4526-92b1-b1938f37b871/AddUserToGroup.java //Warning: This code has been marked up for HTML

/*******************************************************************************

 * $Novell: AddUserToGroup.java,v 1.8 2003/08/21 11:29:24 $

 * Copyright (C) 1999, 2000, 2001 Novell, Inc. All Rights Reserved.

 *

 * THIS WORK IS SUBJECT TO U.S. AND INTERNATIONAL COPYRIGHT LAWS AND

 * TREATIES. USE AND REDISTRIBUTION OF THIS WORK IS SUBJECT TO THE LICENSE

 * AGREEMENT ACCOMPANYING THE SOFTWARE DEVELOPMENT KIT (SDK) THAT CONTAINS

 * THIS WORK. PURSUANT TO THE SDK LICENSE AGREEMENT, NOVELL HEREBY GRANTS TO

 * DEVELOPER A ROYALTY-FREE, NON-EXCLUSIVE LICENSE TO INCLUDE NOVELL'S SAMPLE

 * CODE IN ITS PRODUCT. NOVELL GRANTS DEVELOPER WORLDWIDE DISTRIBUTION RIGHTS

 * TO MARKET, DISTRIBUTE, OR SELL NOVELL'S SAMPLE CODE AS A COMPONENT OF

 * DEVELOPER'S PRODUCTS. NOVELL SHALL HAVE NO OBLIGATIONS TO DEVELOPER OR

 * DEVELOPER'S CUSTOMERS WITH RESPECT TO THIS CODE.

 *

 * $name:        AddUserToGroup.java

 * $Description: The AddUserToGroup sample shows how to add a user to a group

 *               on Novell eDirectory. This includes four attribute

 *               modification steps:

 *                   1. add group's dn to user's groupMemberShip attribute.

 *                   2. add group's dn to user's securityEquals attribute.

 *                   3. add user's dn to group's uniqueMember attribute.

 *                   4. add user's dn to group's equivalentToMe attribute.

 *               After the modifications, the security privileges that are

 *               granted to the group are now inherited by the user. 

 ******************************************************************************/
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;

import util.ScimConstants;

public class AddUserToGroup {
	public static void main(String[] args)	{

		boolean status = false;

		LDAPConnection lc = SSL_Connection.getConnection();

		String userDN  = "cn=JSmith,"+ScimConstants.USER_CONTAINER;

		String groupDN = "cn=cumin,ou=groups,o=people";

		try {

			// call _AddUseToGroup() to add the user to the group
			status = _AddUserToGroup(lc, userDN, groupDN);

			if (status)
				System.out.println("User: " + userDN + " was enrolled in group: " + groupDN);
			else
				System.out.println("User: " + userDN + " could not be enrolled in group: " + groupDN);

			// disconnect with the server
			lc.disconnect();
		}  catch (LDAPException e) {
			System.out.println("Error: " + e.toString());
		}
		System.exit(0);

	}

	public static boolean _AddUserToGroup(LDAPConnection lc, String userdn, String groupdn) {

		// modifications for group and user
		LDAPModification[] modGroup = new LDAPModification[2];
		LDAPModification[] modUser = new LDAPModification[2];

		// Add modifications to modUser
		LDAPAttribute membership = new LDAPAttribute("groupMembership", groupdn);
		modUser[0] = new LDAPModification(LDAPModification.ADD, membership);
		LDAPAttribute security = new LDAPAttribute("securityEquals", groupdn);
		modUser[1] = new LDAPModification(LDAPModification.ADD, security);

		// Add modifications to modGroup
		LDAPAttribute member = new LDAPAttribute("uniqueMember", userdn);
		modGroup[0] = new LDAPModification(LDAPModification.ADD, member);
		LDAPAttribute equivalent = new LDAPAttribute("equivalentToMe", userdn);
		modGroup[1] = new LDAPModification(LDAPModification.ADD, equivalent);

		try {
			// Modify the user's attributes
			lc.modify(userdn, modUser);

			System.out.println("Modified the user's attribute.");
		}  catch (LDAPException e) {
			System.out.println("Failed to modify user's attributes: " + e.toString());
			return false;
		}

		try {
			// Modify the group's attributes
			lc.modify(groupdn, modGroup);
			System.out.println("Modified the group's attribute.");
		}catch (LDAPException e) {
			System.out.println("Failed to modify group's attributes: " + e.toString());
			doCleanup(lc, userdn, groupdn);
			return false;
		}
		return true;
}

	public static void doCleanup(LDAPConnection lc, String userdn, String groupdn) {

		// since we have modified the user's attributes and failed to
		// modify the group's attribute, we need to delete the modified
		// user's attribute values.
		
		// modifications for user

		LDAPModification[] modUser = new LDAPModification[2];

		// Delete the groupdn from the user's attributes
		LDAPAttribute membership = new LDAPAttribute("groupMembership", groupdn);
		modUser[0] = new LDAPModification(LDAPModification.DELETE, membership);
		LDAPAttribute security = new LDAPAttribute("securityEquals", groupdn);
		modUser[1] = new LDAPModification(LDAPModification.DELETE, security);

		try {
			// Modify the user's attributes
			lc.modify(userdn, modUser);
			System.out.println("Deleted the modified user's attribute values.");
		}catch (LDAPException e) {
			System.out.println("Could not delete modified user's attributes: " + e.toString());
		}
		return;
	}

}
