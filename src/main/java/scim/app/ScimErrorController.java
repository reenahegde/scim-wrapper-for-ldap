package scim.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import scim.entity.ScimErrorResponse;
import scim.error.ScimBaseException;

/**
 * 
 * @author AkshathaKadri
 *
 */
@RestController
public class ScimErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;
    
    @Override
    public String getErrorPath() {
        return PATH;
    }
    
    @RequestMapping(value = PATH)
    @ExceptionHandler({ScimBaseException.class})
    public ScimErrorResponse handleException(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> attr= getErrorAttributes(request, false);
    	String status = (String) attr.get("scimStatus");
    	String detail = (String) attr.get("scimDetail");
    	String scimType = (String) attr.get("scimType");
    	response.setStatus(Integer.parseInt(status));
        return new ScimErrorResponse(status, detail, scimType);
    }
    
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
    
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(
                    RequestAttributes requestAttributes,
                    boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
                Throwable error = getError(requestAttributes);
                if (error instanceof ScimBaseException) {
                    errorAttributes.put("scimStatus", ((ScimBaseException)error).getStatus());
                    errorAttributes.put("scimDetail", ((ScimBaseException)error).getDetail());
                    errorAttributes.put("scimType", ((ScimBaseException)error).getScimType());
                }
                return errorAttributes;
            }

        };
    }
}
