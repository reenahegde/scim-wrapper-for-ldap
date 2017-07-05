package scim;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import entity.User;
import scim.ldap.UserAdd;
import scim.ldap.UserSearchService;
import util.ScimConstants;

@RestController
public class UserEndPoint {
	
    @RequestMapping(value = "/Users", method = RequestMethod.GET)
    public List<User> searchUser(@RequestParam(value="filter", defaultValue="userName") String filter) {
    	String search = "("+filter.split(" ")[0]+"="+filter.split(" ")[2]+")";
    	System.out.println(search);
    	List<User> users= UserSearchService.search(search);
    	System.out.println("Endpoint:"+users.size());
    	return users;
       // return new User(counter.incrementAndGet(),  String.format(template, value));
    }
    /*,  produces = ScimConstants.SCIM_CONTENT_TYPE*/
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/Users", method = RequestMethod.POST )
    public String createUser(@RequestParam(value="id", required=true) String id,
    		@RequestParam(value="name", required=true) String[] name, @RequestParam(value="sn") String sn,
    		@RequestParam(value="phone") String phone, @RequestParam(value="password") String password) {
    	
    	User user = new User(id);
    	user.setExternalId(id);
    	user.setMail(id+ScimConstants.EMAIL_SUFFIX);
    	user.setGivenName(name);
    	user.setTelephonenumber(phone);
    	user.setUserpassword(password);
    	user.setSn(sn);
    	String[] cn = {id,sn};
    	user.setCn(cn);
    	System.out.println(user);
    	UserAdd.addUser(user);
    	return user.getDn();
    }
    
   /* @RequestMapping(value = "/Users", method = RequestMethod.POST,  
        	consumes =ScimConstants.SCIM_CONTENT_TYPE, produces = ScimConstants.SCIM_CONTENT_TYPE)
        public String createUser(@RequestParam(value="id", required=true) String id,
        		@RequestParam(value="name", required=true) String[] name, @RequestParam(value="sn") String sn,
        		@RequestParam(value="phone") String phone, @RequestParam(value="password") String password) {
        	
        	User user = new User(id);
        	user.setUid(id);
        	user.setMail(id+ScimConstants.EMAIL_SUFFIX);
        	user.setGivenName(name);
        	user.setTelephonenumber(phone);
        	user.setUserpassword(password);
        	user.setSn(sn);
        	System.out.println(user);
        	UserAdd.addUser(user);
        	return user.getDn();
        }*/
}
