package scim.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import util.ScimConstants;

@JsonInclude(Include.NON_EMPTY)
public class ScimUser extends BaseScimResourse{

	private String userName;
	private Name name;
	private String displayName;
	private String nickName;
	private String profileUrl;
	private List<MultiValuedEntity> emails;
	private List<MultiValuedEntity> phoneNumbers;
	private List<MultiValuedEntity> ims;
	private List<MultiValuedEntity> photos;
	
	private String userType;
	private String title;
	private String preferredLanguage;
	private String locale;
	private String timezone;
	private String active;
	private String password;
	private List<MultiValuedEntity> groups;
	private List<MultiValuedEntity> x509Certificates;
	
	public ScimUser() {
		schemas.add(ScimConstants.SCHEMA_USER);
		meta = new Meta();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public void setEmail(String value) {
		if(emails == null)
			emails = new ArrayList<>();
		MultiValuedEntity mail = new MultiValuedEntity();
		mail.setValue(value);
		emails.add(mail);
	}
	
	public List<MultiValuedEntity> getEmails() {
		return emails;
	}

	public void setEmails(List<MultiValuedEntity> emails) {
		this.emails = emails;
	}

	public List<MultiValuedEntity> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<MultiValuedEntity> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<MultiValuedEntity> getIms() {
		return ims;
	}

	public void setIms(List<MultiValuedEntity> ims) {
		this.ims = ims;
	}

	public List<MultiValuedEntity> getPhotos() {
		return photos;
	}

	public void setPhotos(List<MultiValuedEntity> photos) {
		this.photos = photos;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<MultiValuedEntity> getGroups() {
		return groups;
	}

	public void setGroups(List<MultiValuedEntity> groups) {
		this.groups = groups;
	}

	public List<MultiValuedEntity> getX509Certificates() {
		return x509Certificates;
	}

	public void setX509Certificates(List<MultiValuedEntity> x509Certificates) {
		this.x509Certificates = x509Certificates;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", name=" + name + ", displayName=" + displayName + ", emails=" + emails
				+ ", phoneNumbers=" + phoneNumbers + "]";
	}

}
