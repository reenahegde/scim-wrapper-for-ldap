package entity;

import java.util.Arrays;

import util.ScimConstants;
public class User extends BaseClass{

	private String externalId;
	private String mail;
	private String userpassword;
	private String givenName[];
	private String telephonenumber;

	private String dn;
	private String sn;
	private String cn[];

	private String objectClass;
	private String content;

	public User(String dn) {
		this.dn = dn;
		schemas.add(ScimConstants.SCHEMA_USER);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String[] getGivenName() {
		return givenName;
	}

	public void setGivenName(String[] givenName) {
		this.givenName = givenName;
	}

	public String getTelephonenumber() {
		return telephonenumber;
	}

	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String[] getCn() {
		return cn;
	}

	public void setCn(String[] cn) {
		this.cn = cn;
	}

	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "User [externalId=" + externalId + ", mail=" + mail + ", userpassword=" + userpassword + ", givenName="
				+ Arrays.toString(givenName) + ", telephonenumber=" + telephonenumber + ", dn=" + dn + ", sn=" + sn
				+ ", cn=" + Arrays.toString(cn) + ", objectClass=" + objectClass + ", content=" + content + ", schemas="
				+ schemas + ", id=" + id + "]";
	}

	
}
