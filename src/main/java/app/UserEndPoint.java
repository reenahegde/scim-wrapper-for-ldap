package app;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import scim.error.ScimResourceInvalid;
import scim.error.ScimResourseNotFound;
import scim.ldap.UserService;
import util.ScimConstants;

@RestController
public class UserEndPoint {
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/Users", method = RequestMethod.GET, produces = "application/scim+json")
	public List<ScimUser> searchUsers(@RequestParam(value="filter", defaultValue="userName") String filter) {
		String search = "("+filter.split(" ")[0]+"="+filter.split(" ")[2]+")";
		System.out.println(search);
		List<ScimUser> users= UserService.search(search);
		System.out.println("Endpoint:"+users.size());
		return users;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.GET, produces = "application/scim+json")
	public ScimUser searchUser(@PathVariable("id") String id) {
		System.out.println(id);
		ScimUser users= UserService.getUser(id);
		System.out.println(users);
		return users;
		// return new User(counter.incrementAndGet(),  String.format(template, value));
	}

	/**
	 * DELETE User
	 * @param id
	 * @param response
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") String id,HttpServletResponse response) {
		System.out.println(id);
		boolean deleteStatus = UserService.deleteUser(id);
		if(deleteStatus){
			System.out.println(id+" Deleted");
		} else {
			throw new ScimResourseNotFound(id);
			//response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			//response.seentity(error).build();
			//throw new NotFound404Error();
		}
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/Users", method = RequestMethod.POST, produces = "application/scim+json")
	public ScimUser createUser(@RequestParam(value="user") String input){
		System.out.println("\n"+input);

		ObjectMapper mapper = new ObjectMapper();
		ScimUser user = null;
		try {
			user = mapper.readValue(input, ScimUser.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.getCause();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.getCause();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getCause();
		}

		if(user==null || user.getExternalId()==null || user.getExternalId().isEmpty())
			throw new ScimResourceInvalid();

		if(user.getEmails()==null || user.getEmails().isEmpty())
			user.setEmail(user.getExternalId()+ScimConstants.DEFAULT_EMAIL_SUFFIX);

		if(user.getPassword()==null || user.getPassword().isEmpty())
			user.setPassword(ScimConstants.DEFAULT_PASSWORD);
		
		System.out.println(user);
		ScimUser newUser = UserService.addUser(user);

		return newUser;// Response.created(URI.create(uri)).entity(newUser).build();
	}

	/**
	 * Put User
	 * @param id
	 * @param response
	 */
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/Users/{id}", method = RequestMethod.PUT)
	public void putUser(@PathVariable("id") String id, @RequestParam(value="user") String input) {
		System.out.println("\n"+input);

		ObjectMapper mapper = new ObjectMapper();
		ScimUser user = null;
		try {
			user = mapper.readValue(input, ScimUser.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.getCause();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.getCause();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getCause();
		}

		boolean updateStatus = UserService.replaceUser(id, user);
		if(updateStatus){
			System.out.println(user+" Updated");
		} else {
			throw new ScimResourseNotFound(user.getId());
		}
	}
}
