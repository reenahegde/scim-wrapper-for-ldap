package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;

import ldap.SSL_Connection;
import scim.entity.Meta;
import scim.entity.MultiValuedEntity;
import scim.entity.Name;
import scim.entity.ScimUser;

public class ScimUtils {

	public static final String userObjClass = "inetOrgPerson";
	public static final String COMMA = ",";
	public static final String DN_SUFFIX= "cn=";
	public static final String CONTAINER_NAME  = ScimConstants.USER_CONTAINER;
	public static final String META_DELIM  = "|";

	/*public static void main(String[] args) {
		Calendar cal = getCurrentDate();
		System.out.println(cal);
	}
	public static Calendar parseDateString(String date){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(ScimConstants.DATE_FORMAT, Locale.ENGLISH);
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cal;
	}

	public static Calendar getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(ScimConstants.DATE_FORMAT, Locale.ENGLISH);
		try {
			cal.setTime(sdf.parse(cal.toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cal;
	}*/

	/**
	 * 
	 * @param nextEntry
	 * @return
	 */
	public static ScimUser getUser(LDAPEntry nextEntry){
		ScimUser user = new ScimUser();

		LDAPAttribute dp;
		dp = nextEntry.getAttribute("uid");
		if(dp!=null){
			String id = dp.getStringValue();
			user.setId(id);
			user.setExternalId(id.split(ScimConstants.ID_BUFFER)[1]);
		}

		dp = nextEntry.getAttribute("mail");
		if(dp!=null){
			List<MultiValuedEntity> emails = new ArrayList<MultiValuedEntity>();
			MultiValuedEntity email = new MultiValuedEntity();
			email.setValue(dp.getStringValue());
			emails.add(email);
			user.setEmails(emails);
		}

		dp = nextEntry.getAttribute("userpassword");
		if(dp!=null)
			user.setPassword(dp.getStringValue());

		dp = nextEntry.getAttribute("givenName");
		if(dp!=null && dp.getStringValueArray().length>0) {
			Name name = setLdapNames(dp.getStringValueArray());
			user.setName(name);
		}

		dp = nextEntry.getAttribute("telephonenumber");
		if(dp!=null){
			List<MultiValuedEntity> phones = new ArrayList<MultiValuedEntity>();
			MultiValuedEntity phone = new MultiValuedEntity();
			phone.setValue(dp.getStringValue());
			phone.setPrimary("true");
			phones.add(phone);
			user.setPhoneNumbers(phones);
		}

		//Meta data ------------------------------------------------
		//TODO: add meta in schema
		Meta meta = user.getMeta();
		dp = nextEntry.getAttribute("description");
		if(dp!=null){
			String metaData = dp.getStringValue();
			String metaArray[] = metaData.split("~");
			if(metaArray.length>2){
				meta.setVersion(metaArray[0]);
				meta.setCreated(metaArray[1]);
				meta.setLastModified(metaArray[2]);
				meta.setResourceType(metaArray[3]);
			} else {
				meta = null;
			}
		} else {
			meta.setVersion("");
			meta.setCreated("");
			meta.setLastModified("");
		}
		meta.setResourceType(ScimConstants.USER_RESOURCE_TYPE);
		meta.setLocation(ScimConstants.USER_URI+user.getId());
		user.setMeta(meta);
		return user;
	}

	public static String[] getLdapNames(Name name){
		String[] names = {name.getGivenName(),name.getMiddleName(),name.getFamilyName(),name.getHonorificPrefix()+" "+name.getGivenName(),name.getFamilyName()+" "+name.getHonorificSuffix()};
		//String[] names = {name.getFormatted()};
		return names;
	}

	public static Name setLdapNames(String[] names){
		Name name = new Name();
		List<String> namesArr = new ArrayList<>();
		for(String s:names){
			namesArr.add(s);
		}

		/*String[] famName = namesArr.get(1).toString().split(" ");
		name.setFamilyName(famName[0]);
		if(famName.length>1)
			name.setHonorificSuffix(famName[1]);*/

		if(namesArr.size()>4){
			String[] temp = namesArr.get(4).toString().split(" ");
			if(temp.length>1)
				name.setHonorificSuffix(temp[1]);
			namesArr.remove(4);
		}

		if(namesArr.size()>3){
			name.setHonorificPrefix(namesArr.get(3).toString().split(" ")[0]);
			namesArr.remove(3);
		}

		if(namesArr.size()>2){
			name.setMiddleName(namesArr.get(1).toString());
			namesArr.remove(1);
		}
		if(namesArr.size()>1){
			name.setFamilyName(namesArr.get(1).toString());
			namesArr.remove(1);
		}
		if(namesArr.size()>0){
			name.setGivenName(namesArr.get(0).toString());
			namesArr.remove(0);
		}
		name.getFormatted();
		return name;
	}

	public static LDAPEntry getLdapUserEntry(ScimUser user){
		LDAPAttributeSet attributeSet = new LDAPAttributeSet();                  
		attributeSet.add( new LDAPAttribute("objectclass", userObjClass));     

		if(null != user.getId())
			attributeSet.add( new LDAPAttribute("uid",user.getId()));
		if(null != user.getEmails() && null != user.getEmails().get(0))
			attributeSet.add( new LDAPAttribute("mail",user.getEmails().get(0).getValue()));
		if(null != user.getPassword())
			attributeSet.add( new LDAPAttribute("userpassword",user.getPassword()));        
		if(null != user.getName()){
			attributeSet.add( new LDAPAttribute("givenname",getLdapNames(user.getName()))); 
			if(null != user.getName().getFamilyName())
				attributeSet.add( new LDAPAttribute("sn", user.getName().getFamilyName()));
			if(null != user.getName().getGivenName())
				attributeSet.add( new LDAPAttribute("cn", user.getName().getGivenName() + " " +user.getName().getFamilyName()));
		}
		if(null != user.getPhoneNumbers() && null != user.getPhoneNumbers().get(0))
			attributeSet.add( new LDAPAttribute("telephonenumber",user.getPhoneNumbers().get(0).getValue()));                                                     

		if(null != user.getMeta()){
			Meta meta = user.getMeta();
			attributeSet.add( new LDAPAttribute("description",meta.getVersion()+"~"+meta.getCreated()+
					"~"+meta.getLastModified()+"~"+meta.getResourceType())); 
		}

		String  dn  = DN_SUFFIX+ user.getExternalId() + COMMA + CONTAINER_NAME;      
		LDAPEntry newEntry = new LDAPEntry( dn, attributeSet );

		return newEntry;
	}

	/**
	 * Checks only first entity as per current design 
	 * Make necessary modifications if handling array of changes here
	 * @param entity
	 * @param oldEntity
	 * @return true if this entity is to be modified
	 */
	public static boolean isModify(List<MultiValuedEntity> entity, List<MultiValuedEntity> oldEntity){
		boolean isModify = false;
		if(entity != null && !entity.get(0).getValue().isEmpty()) {
			if(oldEntity != null && !oldEntity.get(0).getValue().isEmpty()) {
				if(!entity.get(0).getValue().equals(oldEntity.get(0).getValue())){
					isModify = true;
				}
			} else {
				isModify = true;
			}
			return false;
		}
		return isModify;
	}

	/**
	 * Checks only first entity as per current design 
	 * Make necessary modifications if handling array of changes here
	 * @param entity
	 * @param oldEntity
	 * @return true if this entity is to be modified
	 */
	public static boolean isModify(String entity, String oldEntity){
		boolean isModify = false;
		if(entity != null && !entity.isEmpty()) {
			if(oldEntity != null && !oldEntity.isEmpty()) {
				if(!entity.equals(oldEntity)){
					isModify = true;
				}
			} else {
				isModify = true;
			}
			return false;
		}
		return isModify;
	}

	public static ArrayList<LDAPModification> getLdapUserMod(ScimUser user, ScimUser oldUser){
		boolean isSuccess = true;
		String  dn  = DN_SUFFIX+ user.getExternalId() + COMMA + CONTAINER_NAME; 
		ArrayList<LDAPModification> modList = new ArrayList<LDAPModification>();
		LDAPAttribute attribute;

		//TODO: mutability error check if not done already

		// Replace all values the E-mail address with a new value
		if(isModify(user.getEmails(), oldUser.getEmails())){
			attribute = new LDAPAttribute("mail", user.getEmails().get(0).getValue());
			modList.add(new LDAPModification(LDAPModification.REPLACE, attribute));
		}

		// Replace values of the password with a new value
		if(isModify(user.getPassword(), oldUser.getPassword())){
			attribute = new LDAPAttribute("userpassword", user.getPassword());
			modList.add(new LDAPModification(LDAPModification.REPLACE, attribute));
		}

		//TODO: Name change
		/*if(null != user.getName()){
			attributeSet.add( new LDAPAttribute("givenname",getLdapNames(user.getName()))); 
			if(null != user.getName().getFamilyName())
				attributeSet.add( new LDAPAttribute("sn", user.getName().getFamilyName()));
			if(null != user.getName().getGivenName())
				attributeSet.add( new LDAPAttribute("cn", user.getName().getGivenName() + " " +user.getName().getFamilyName()));
		}*/

		// Change the phone number
		if(isModify(user.getPhoneNumbers(), oldUser.getPhoneNumbers())){
			attribute = new LDAPAttribute("telephoneNumber", oldUser.getPhoneNumbers().get(0).getValue());
			modList.add(new LDAPModification(LDAPModification.DELETE, attribute));

			// Now we add the new phone number
			attribute = new LDAPAttribute("telephoneNumber", user.getPhoneNumbers().get(0).getValue());
			modList.add(new LDAPModification(LDAPModification.ADD, attribute));
		}

		if(null != oldUser.getMeta()){
			user.setMeta(new Meta());
		}
		
		Meta meta = user.getMeta();
		meta.setLocation(ScimConstants.USER_URI+user.getId());
		meta.setResourceType(ScimConstants.USER_RESOURCE_TYPE);
		Integer version = Integer.parseInt(meta.getVersion())+1;
		meta.setVersion(version.toString());
		String lastModified =  Calendar.getInstance().getTime().toString();
		meta.setLastModified(lastModified);
		user.setMeta(meta);
		
		attribute = new LDAPAttribute("description", meta.getVersion()+"~"+meta.getCreated()+
				"~"+meta.getLastModified()+"~"+meta.getResourceType());
		
		modList.add(new LDAPModification(LDAPModification.REPLACE, attribute));

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
				return false;
			}
		}

		try {
			lc.modify( dn, mods);
			System.out.println("Successfully modified the attributes of the entry.");
		} catch (LDAPException e2) {
			System.err.println("Error: " + e2.toString());
			isSuccess = false;
			try {
				lc.modify(dn, new LDAPModification(LDAPModification.DELETE,new LDAPAttribute("telephoneNumber", phone1)));
			} catch (Exception e) {
				// ignore
			}
		} finally {
			try {
				lc.disconnect();
			} catch (Exception e) {
				// ignore
			}
		}
		return isSuccess;

	}

	public static String generateIdForUser(String id) {
		return ScimConstants.ORG + ScimConstants.ID_BUFFER + id;
	}

	public static String getDNFromExternalId(String extId){
		return DN_SUFFIX+ extId + COMMA + CONTAINER_NAME; 
	}

	public static String getDNFromId(String id){
		return getDNFromExternalId(id.split(ScimConstants.ID_BUFFER)[1]); 
	}
}
