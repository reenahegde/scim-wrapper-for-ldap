package scim.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import scim.entity.ServiceProviderConfig;

/**
 * 
 * @author AkshathaKadri
 *
 */
@RestController
public class ServiceEndPoint {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Search Users
	 * @param filter
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/ServiceProviderConfig", method = RequestMethod.GET, produces = "application/scim+json")
	public ServiceProviderConfig getConfig() {
		logger.info("In getConfig");
		ServiceProviderConfig conf = new ServiceProviderConfig();;
		logger.info("ServiceProviderConfig:"+conf);
		return conf;
	}

}
