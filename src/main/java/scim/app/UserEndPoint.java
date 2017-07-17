package scim.app;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import scim.entity.ScimUser;
import scim.error.ScimBadRequest;
import scim.error.ScimResourseNotFound;
import scim.ldap.UserService;
import scim.util.ScimConstants;
import scim.util.ScimErrorConstants;

/**
 * 
 * @author AkshathaKadri
 *
 */
@RestController
public class UserEndPoint {
	@Autowired
	UserService userService;
/*	@Inject
    private Logger log;*/
	
	/**
	 * Search Users
	 * @param filter
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/Users", method = RequestMethod.GET, produces = "application/scim+json")
	public List<ScimUser> searchUsers(@RequestParam(value="filter", defaultValue="userName") String filter) {
		//TODO: multiple search parameters
		String search = "("+filter.split(" ")[0]+"="+filter.split(" ")[2]+")";
		//System.out.println(search);
		List<ScimUser> users= userService.search(search);
		//System.out.println("Endpoint:"+users.size());
		return users;
	}

	/**
	 * Search User by ID
	 * @param id
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.GET, produces = "application/scim+json")
	public ScimUser getUserById(@PathVariable("id") String id) {
		//System.out.println(id);
		ScimUser users= userService.getUserById(id);
		//System.out.println(users);
		return users;
	}

	/**
	 * DELETE User
	 * @param id
	 * @param response
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") String id,HttpServletResponse response) {
		//System.out.println(id);
		boolean deleteStatus = userService.deleteUser(id);
		if(deleteStatus){
			//System.out.println(id+" Deleted");
		} else {
			throw new ScimResourseNotFound(id);
		}
	}

	/**
	 * Create User
	 * @param input
	 * @return
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/Users", method = RequestMethod.POST, produces = "application/scim+json")
	public ScimUser createUser(@RequestParam(value="user") String input){
		ScimUser user = getUserFromJson(input);

		if(user==null || user.getExternalId()==null || user.getExternalId().isEmpty())
			throw new ScimBadRequest();

		if(user.getEmails()==null || user.getEmails().isEmpty())
			user.setEmail(user.getExternalId()+ScimConstants.DEFAULT_EMAIL_SUFFIX);

		if(user.getPassword()==null || user.getPassword().isEmpty())
			user.setPassword(ScimConstants.DEFAULT_PASSWORD);
		
		//System.out.println(user);
		ScimUser newUser = userService.addUser(user);

		return newUser;// Response.created(URI.create(uri)).entity(newUser).build();
	}

	/**
	 * Put User
	 * @param id
	 * @param response
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.PUT)
	public ScimUser putUser(@PathVariable("id") String id, @RequestParam(value="user") String input) {
		ScimUser user = getUserFromJson(input);

		ScimUser updateUser = userService.replaceUser(id, user);
		if(updateUser!=null){
			//System.out.println(user+" Updated");
			return updateUser;
		} else {
			throw new ScimResourseNotFound(user.getId());
		}
	}
	
	
	/**
	 * PATCH User
	 * @param id
	 * @param response
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.PATCH)
	public ScimUser patchUser(@PathVariable("id") String id, @RequestParam(value="user") String input) {
		ScimUser user = getUserFromJson(input);
		ScimUser updateUser = userService.updateUser(id, user);
		if(updateUser!=null){
			//System.out.println(user+" Updated");
			return updateUser;
		} else {
			throw new ScimResourseNotFound(user.getId());
		}
	}
	
	/**
	 * get ScimUser From Json String
	 * @param input
	 * @return
	 */
	private ScimUser getUserFromJson(String input){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(input, ScimUser.class);
		} catch (JsonParseException e) {
			throw new ScimBadRequest(ScimErrorConstants.REQ_PARSE_ERROR);
		} catch (JsonMappingException e) {
			throw new ScimBadRequest(ScimErrorConstants.REQ_SYNTAX_ERROR);
		} catch (IOException e) {
			throw new ScimBadRequest(ScimErrorConstants.REQ_PARSE_ERROR);
		}
	}
}
