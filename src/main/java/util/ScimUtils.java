package util;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPEntry;

import entity.User;

public class ScimUtils {
	
	public static final String userObjClass = "inetOrgPerson";
	public static final String COMMA = ",";
	public static final String DN_SUFFIX= "cn=";
	public static final String containerName  = "ou=users,o=people";
	
	public static User getUser(LDAPEntry nextEntry){
		User user = new User(nextEntry.getDN());

		LDAPAttribute dp;
		
		dp = nextEntry.getAttribute("uid");
		if(dp!=null)
			user.setExternalId(dp.getStringValue());
		
		dp = nextEntry.getAttribute("mail");
		if(dp!=null)
			user.setMail(dp.getStringValue());
		
		dp = nextEntry.getAttribute("userpassword");
		if(dp!=null)
			user.setUserpassword(dp.getStringValue());
		
		dp = nextEntry.getAttribute("givenName");
		if(dp!=null) 
			user.setGivenName(dp.getStringValueArray());
		
		dp = nextEntry.getAttribute("telephonenumber");
		if(dp!=null)
			user.setTelephonenumber(dp.getStringValue());
		
		dp = nextEntry.getAttribute("sn");
		if(dp!=null)
			user.setSn(dp.getStringValue());
		
		dp = nextEntry.getAttribute("cn");
		if(dp!=null) 
			user.setCn(dp.getStringValueArray());
		
		dp = nextEntry.getAttribute("objectClass");
		if(dp!=null)
			user.setObjectClass(dp.getStringValue());
		
		dp = nextEntry.getAttribute("content");
		if(dp!=null)
			user.setContent(dp.getStringValue());

		//System.out.println("User converted "+user.getDn()+"---------------------------------------------------");
		return user;
	}
	
	public static LDAPEntry getLdapUserEntry(User user){
		LDAPAttributeSet attributeSet = new LDAPAttributeSet();                  
		attributeSet.add( new LDAPAttribute("objectclass", userObjClass));     
		
		System.out.println(user.toString());
		if(null != user.getExternalId())
			attributeSet.add( new LDAPAttribute("uid",user.getExternalId()));          
		if(null != user.getCn())
			attributeSet.add( new LDAPAttribute("cn",user.getCn()));          
		if(null != user.getGivenName())
			attributeSet.add( new LDAPAttribute("givenname",user.getGivenName()));   
		if(null != user.getSn())
			attributeSet.add( new LDAPAttribute("sn", user.getSn()));        
		if(null != user.getTelephonenumber())
			attributeSet.add( new LDAPAttribute("telephonenumber",user.getTelephonenumber()));                                                     
		if(null != user.getMail())
			attributeSet.add( new LDAPAttribute("mail",user.getMail()));
		if(null != user.getUserpassword())
			attributeSet.add( new LDAPAttribute("userpassword",user.getUserpassword()));                                           

		String  dn  = DN_SUFFIX+ user.getDn() + COMMA + containerName;      
		LDAPEntry newEntry = new LDAPEntry( dn, attributeSet );
		
		return newEntry;
	}
}
