package app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author AkshathaKadri
 *
 */
@RestController
@RequestMapping("/Schemas")
public class SchemaEndpoint {

   /* @RequestMapping(method = RequestMethod.GET, produces = ScimConstants.SCIM_CONTENT_TYPE)
    public List<Schema> retrieveAllSchemas() {
        List<Schema> allSchemas = database.findAll();
        allSchemas.removeIf(schema -> isSecretSchema(schema.getId()));
        if (allSchemas.isEmpty()) {
            throw new Exception("Schema not found");
        }
        return allSchemas;
    }*/
    
}
