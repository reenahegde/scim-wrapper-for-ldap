package scim.app;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import scim.entity.ListResponse;
import scim.entity.ResourceType;
import scim.util.ScimUtils;

/**
 * 
 * @author AkshathaKadri
 *
 */
@RestController
public class ResourceTypesEndPoint {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Search Users
	 * @param filter
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/ResourceTypes", method = RequestMethod.GET, produces = "application/scim+json")
	public ListResponse getResourceTypes() {
		logger.info("In getConfig");
		ListResponse list = new ListResponse();
		ResourceType userRes =ScimUtils.getUserResource();
		ArrayList<Object> resource  = new ArrayList<>();
		resource.add(userRes);
		list.setTotalResults(1);
		list.setItemsPerPage(1);
		list.setStartIndex(0);
		list.setResources(resource);
		//ResourceType groupRes = new ResourceType();
		logger.info("ResourceTypes:"+list);
		return list;
	}

}
