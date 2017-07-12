package ldap;

//Sample code file: var/ndk/webBuildengine/tmp/viewable_samples/f91a68eb-ad37-4526-92b1-b1938f37b871/AddEntry.java //Warning: This code has been marked up for HTML

/*******************************************************************************

* $Novell: AddEntry.java,v 1.15 2003/08/21 11:28:45 $

* Copyright (c) 2000-2003 Novell, Inc. All Rights Reserved.

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

* $name:         AddEntry.java 

* $description:  AddEntry adds an entry to the directory. First, it creates

*                each attribute of the entry, adds it to the attribute

*                set, and then uses the DN and the newly created attribute

*                set to create an LDAPEntry entry, newEntry. Finally it calls

*                the LDAPConnection add method to add the entry into the

*                directory.

******************************************************************************/



import com.novell.ldap.LDAPAttribute;

import com.novell.ldap.LDAPAttributeSet;

import com.novell.ldap.LDAPEntry;

import com.novell.ldap.LDAPConnection;

import com.novell.ldap.LDAPException;

import java.io.UnsupportedEncodingException;



public class AddDefault

{

  public static void main( String[] args )

  {

      if (args.length != 4) {

          System.err.println("Usage:   java AddEntry <host name> <login dn>"

                                              + " <password> <container>");

          System.err.println("Example: java AddEntry Acme.com"

                      + " \"cn=admin,o=Acme\" secret \"ou=Sales,o=Acme\"");

          System.exit(1);

      }

              

      int ldapPort = LDAPConnection.DEFAULT_PORT;

      int ldapVersion  = LDAPConnection.LDAP_V3;

      String ldapHost       = args[0];

      String loginDN        = args[1];

      String password       = args[2];

      String containerName  = args[3];

      LDAPConnection lc = new LDAPConnection();

     // LDAPAttribute  attribute = null;

      LDAPAttributeSet attributeSet = new LDAPAttributeSet();



   

      /* To Add an entry to the directory,

       *  - Create the attributes of the entry and add them to an attribute set

       *  - Specify the DN of the entry to be created

       *  - Create an LDAPEntry object with the DN and the attribute set

       *  - Call the LDAPConnection add method to add it to the directory

       */                   

      attributeSet.add( new LDAPAttribute( 

                           "objectclass", new String("inetOrgPerson")));                

      attributeSet.add( new LDAPAttribute("cn", 

              new String[]{"James Smith", "Jim Smith", "Jimmy Smith"}));               

      attributeSet.add( new LDAPAttribute("givenname",

                               new String[]{"James", "Jim", "Jimmy" }));        

      attributeSet.add( new LDAPAttribute("sn", new String("Smith")));        

      attributeSet.add( new LDAPAttribute("telephonenumber",

                                          new String("1 801 555 1212")));                                                     

      attributeSet.add( new LDAPAttribute("mail", 

                                         new String("JSmith@Acme.com")));

      attributeSet.add( new LDAPAttribute("userpassword", 

                                             new String("newpassword")));                                           

                                             

      String  dn  = "cn=JSmith," + containerName;      

      LDAPEntry newEntry = new LDAPEntry( dn, attributeSet );



      try {

         // connect to the server


          lc.connect( ldapHost, ldapPort );

         // authenticate to the server


          lc.bind( ldapVersion, loginDN, password.getBytes("UTF8") );



          lc.add( newEntry );

          System.out.println( "\nAdded object: " + dn + " successfully." );



         // disconnect with the server


          lc.disconnect();

      }

      catch( LDAPException e ) {

          System.out.println( "Error:  " + e.toString());

      }

      catch( UnsupportedEncodingException e ) {

          System.out.println( "Error: " + e.toString() );

      }                                   

      System.exit(0);

  }

}
