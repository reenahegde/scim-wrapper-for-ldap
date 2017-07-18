package scim.util;

public class ScimConstants {
	public static final String ORG = 				"SATH";
	public static final String ID_BUFFER = 			"0000";
	public static final String DEFAULT_EMAIL_SUFFIX="@Sath.comy";
	public static final String DEFAULT_PASSWORD = 	"password";
	public static final String DUMMY_PHONE = 		"0 000 000 0000";
	public static final String USER_CONTAINER  = 	"ou=users,o=people";
	public static final String URI_DELIM= 			"/";
	public static final String URI = 				"http://localhost:8080/";
	
	public static final String SCIM_CONTENT_TYPE = 	"application/json+scim";
	public static final String template = 			"Hello, %s!";
	
	public static final String SCHEMA_SERVICE =		"urn:ietf:params:scim:schemas:core:2.0:ServiceProviderConfig";
	public static final String SCHEMA_LIST_RESPONSE="urn:ietf:params:scim:api:messages:2.0:ListResponse";
	public static final String SCHEMA_RESOURCE_TYPE="urn:ietf:params:scim:schemas:core:2.0:ResourceType";
	public static final String SCHEMA_USER = 		"urn:ietf:params:scim:schemas:core:2.0:User";
	public static final String SCHEMA_ENT_USER = 	"urn:ietf:params:scim:schemas:extension:enterprise:2.0:User";
	public static final String SCHEMA_ERROR = 		"urn:ietf:params:scim:api:messages:2.0:Error";
	public static final String SCHEMA_PATCH = 		"urn:ietf:params:scim:api:messages:2.0:PatchOp";
	
	public static final String USER_RESOURCE_TYPE= 	"User";
	public static final String USER_PATH= 			"Users/";
	public static final String SERVICE_RESOURCE_TYPE= "ServiceProviderConfig";
	public static final String VERSION = 			"1";
	public static final String DATE_FORMAT = 		"EEE MMM dd HH:mm:ss z yyyy";
}
