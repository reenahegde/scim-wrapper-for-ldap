package scim.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.validator.internal.util.StringHelper;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPModification;

import scim.entity.Meta;
import scim.entity.MultiValuedEntity;
import scim.entity.Name;
import scim.entity.ResourceType;
import scim.entity.Schema;
import scim.entity.ScimUser;

public class ScimUtils {

	private static final String userObjClass = "inetOrgPerson";
	private static final String COMMA = ",";
	private static final String DN_SUFFIX= "cn=";
	private static final String CONTAINER_NAME  = ScimConstants.USER_CONTAINER;
	private static final String META_DELIM  = "~";
	public enum Mutability {readOnly,readWrite};

	/**
	 * get User
	 * @param nextEntry
	 * @return
	 */
	public static ScimUser getScimUser(LDAPEntry nextEntry){
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

		/*dp = nextEntry.getAttribute("userpassword");
		if(dp!=null)
			user.setPassword(dp.getStringValue());*/

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
			//phone.setPrimary("true");
			phones.add(phone);
			user.setPhoneNumbers(phones);
		}

		//Meta data ------------------------------------------------
		Meta meta = user.getMeta();
		dp = nextEntry.getAttribute("description");
		if(dp!=null){
			String metaData = dp.getStringValue();
			String metaArray[] = metaData.split(META_DELIM);
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
		meta.setLocation(ScimConstants.URI+ScimConstants.USER_PATH+user.getId());
		user.setMeta(meta);
		return user;
	}

	private static String[] getLdapNames(Name name){
		String[] names = { name.getGivenName(), name.getMiddleName(), name.getFamilyName(),
				name.getHonorificPrefix() + " " + name.getGivenName(),
				name.getFamilyName() + " " + name.getHonorificSuffix() };
		return names;
	}

	private static Name setLdapNames(String[] names){
		Name name = new Name();
		List<String> namesArr = new ArrayList<>();
		for(String s:names){
			namesArr.add(s);
		}

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

	private static String getMetaString(Meta meta){
		return meta.getVersion()+META_DELIM+meta.getCreated()+META_DELIM+meta.getLastModified()+META_DELIM+meta.getResourceType();
	}
	public static LDAPEntry getLdapEntry(ScimUser user){
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
			attributeSet.add( new LDAPAttribute("description",getMetaString(meta))); 
		}

		String  dn  = getDNFromExternalId(user.getExternalId());      
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
	private static boolean isModify(List<MultiValuedEntity> entity, List<MultiValuedEntity> oldEntity){
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
	private static boolean isModify(String entity, String oldEntity){
		boolean isModify = false;
		if(StringHelper.isNullOrEmptyString(entity)) {
			if(StringHelper.isNullOrEmptyString(oldEntity)) {
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

	private static List<LDAPModification> LDAPModHelper(int op, String key, String value, List<LDAPModification> modList){
		if(value != null && !value.isEmpty()){
			LDAPAttribute attribute = new LDAPAttribute(key, value);
			modList.add(new LDAPModification(op, attribute));
		}
		return modList;
	}
	/**
	 * pushLdapUser
	 * Deletes all attributes and builds new ones
	 * @param user
	 * @param oldUser
	 * @return
	 */
	public static LDAPModification[] pushLdapUser(ScimUser user, ScimUser oldUser){
		List<LDAPModification> modList = new ArrayList<LDAPModification>();

		//Email remove
		for(MultiValuedEntity entity:oldUser.getEmails()){
			modList = LDAPModHelper(LDAPModification.DELETE, "mail", entity.getValue(), modList);
		}
		//Email add
		for(MultiValuedEntity entity:user.getEmails()){
			modList = LDAPModHelper(LDAPModification.ADD, "mail", entity.getValue(), modList);
		}

		modList = LDAPModHelper(LDAPModification.DELETE, "userpassword", oldUser.getPassword(), modList);
		modList = LDAPModHelper(LDAPModification.ADD, "userpassword", user.getPassword(), modList);

		/*String[] oldNames = getLdapNames(oldUser.getName());
		String[] names = getLdapNames(user.getName());
		for(String name: oldNames){
			modList = LDAPModHelper(LDAPModification.DELETE, "givenname", name, modList);
		}
		for(String name: names){
			modList = LDAPModHelper(LDAPModification.ADD, "givenname", name, modList);
		}*/

		if(oldUser.getName()!=null){
			String[] oldNames = getLdapNames(oldUser.getName());
			if(oldNames != null && oldNames.length>0){
				LDAPAttribute attribute = new LDAPAttribute("givenname", oldNames);
				modList.add(new LDAPModification(LDAPModification.DELETE, attribute));
			}
			if(null != oldUser.getName().getFamilyName())
				modList = LDAPModHelper(LDAPModification.DELETE, "sn", oldUser.getName().getFamilyName(), modList);
			if(null != oldUser.getName().getGivenName())
				modList = LDAPModHelper(LDAPModification.DELETE, "cn", oldUser.getName().getGivenName()+ " " +oldUser.getName().getFamilyName(), modList);
		}

		if(user.getName()!=null){
			String[] names = getLdapNames(user.getName());
			if(names != null && names.length>0){
				LDAPAttribute attribute = new LDAPAttribute("givenname", names);
				modList.add(new LDAPModification(LDAPModification.ADD, attribute));
			}
			if(null != user.getName().getFamilyName())
				modList = LDAPModHelper(LDAPModification.ADD, "sn", user.getName().getFamilyName(), modList);
			if(null != user.getName().getGivenName())
				modList = LDAPModHelper(LDAPModification.ADD, "cn", user.getName().getGivenName()+ " " +user.getName().getFamilyName(), modList);

		}

		//phone number remove
		for(MultiValuedEntity entity:oldUser.getPhoneNumbers()){
			modList = LDAPModHelper(LDAPModification.DELETE, "telephoneNumber", entity.getValue(), modList);
		}
		//phone number add
		for(MultiValuedEntity entity:user.getPhoneNumbers()){
			modList = LDAPModHelper(LDAPModification.ADD, "telephoneNumber", entity.getValue(), modList);
		}
		
		//If the old user does not have description set
		if(null == oldUser.getMeta()){
			user.setMeta(new Meta());
		}

		Meta meta = oldUser.getMeta();
		//TODO: test URI
		meta.setLocation(ScimConstants.URI+ScimConstants.USER_PATH+oldUser.getId());
		meta.setResourceType(ScimConstants.USER_RESOURCE_TYPE);
		Integer version = Integer.parseInt(meta.getVersion())+1;
		meta.setVersion(version.toString());
		String lastModified =  Calendar.getInstance().getTime().toString();
		meta.setLastModified(lastModified);
		user.setMeta(meta);

		modList = LDAPModHelper(LDAPModification.REPLACE, "description", getMetaString(meta), modList);

		LDAPModification[] mods = new LDAPModification[modList.size()];
		mods = (LDAPModification[]) modList.toArray(mods);

		return mods;

	}

	//TODO: Remove if not implementing PATCH
	public static LDAPModification[] getLdapUserMod(ScimUser user, ScimUser oldUser){
		ArrayList<LDAPModification> modList = new ArrayList<LDAPModification>();
		LDAPAttribute attribute;

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

		//If the old user does not have description set
		if(null == oldUser.getMeta()){
			user.setMeta(new Meta());
		}

		Meta meta = user.getMeta();
		meta.setLocation(ScimConstants.URI+ScimConstants.USER_PATH+user.getId());
		meta.setResourceType(ScimConstants.USER_RESOURCE_TYPE);
		Integer version = Integer.parseInt(meta.getVersion())+1;
		meta.setVersion(version.toString());
		String lastModified =  Calendar.getInstance().getTime().toString();
		meta.setLastModified(lastModified);
		user.setMeta(meta);

		attribute = new LDAPAttribute("description", getMetaString(meta));

		modList.add(new LDAPModification(LDAPModification.REPLACE, attribute));

		LDAPModification[] mods = new LDAPModification[modList.size()];
		mods = (LDAPModification[]) modList.toArray(mods);

		return mods;

	}

	public static String generateIdForUser(String id) {
		return ScimConstants.ORG + ScimConstants.ID_BUFFER + id;
	}

	public static String getDNFromExternalId(String extId){
		return DN_SUFFIX + extId + COMMA + CONTAINER_NAME; 
	}

	public static String getDNFromId(String id){
		return getDNFromExternalId(id.split(ScimConstants.ID_BUFFER)[1]); 
	}
	
	public static ResourceType getUserResource(){
		ResourceType user = new ResourceType();
		user.setId(ScimConstants.USER_RESOURCE_TYPE);
		user.setName(ScimConstants.USER_RESOURCE_TYPE);
		user.setEndpoint(ScimConstants.URI_DELIM+ScimConstants.USER_RESOURCE_TYPE);
		user.setDescription(ScimConstants.USER_DESC);
		user.setSchema(ScimConstants.SCHEMA_USER);
		Schema schema = new Schema();
		schema.setRequired(false);
		schema.setSchema(ScimConstants.SCHEMA_ENT_USER);
		user.setSchemaExtensions(schema);
		Meta meta = user.getMeta();
		meta.setResourceType(ScimConstants.USER_RESOURCE_TYPE);
		meta.setLocation(ScimConstants.URI+ScimConstants.USER_RESOURCE_TYPE);
		return user;
	}
}
