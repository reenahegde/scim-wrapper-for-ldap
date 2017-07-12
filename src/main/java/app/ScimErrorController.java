package app;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import scim.error.ErrorResponse404;

@RestController
public class ScimErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse404 error(HttpServletRequest request, HttpServletResponse response) {
    	/*Map<String, Object> attr= getErrorAttributes(request, false);
    	String id = ((String) attr.get("path")).split(ScimConstants.USER_PATH)[1];*/
    	HashMap<?, ?> attr= (HashMap<?, ?>) request.getAttribute("org.springframework.web.servlet.View.pathVariables");
    	String id = (String) attr.get("id");
        return new ErrorResponse404(id);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
    
    /*@Autowired
    private ErrorAttributes errorAttributes;
    
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);

    }*/
    
   /* @ExceptionHandler(ScimObjectNotFound.class)
   	public ModelAndView handleError(HttpServletRequest req, ScimObjectNotFound exception)
   			throws Exception {

   		// Rethrow annotated exceptions or they will be processed here instead.
   		if (AnnotationUtils.findAnnotation(exception.getClass(),
   				ResponseStatus.class) != null)
   			throw exception;

   		System.out.println("Request: " + req.getRequestURI() + " raised " + exception);

   		ModelAndView mav = new ModelAndView();
   		mav.addObject("exception", exception);
   		mav.addObject("url", req.getRequestURL());
   		mav.addObject("timestamp", new Date().toString());
   		mav.addObject("status", 500);

   		mav.setViewName("support");
   		return mav;
   	}*/
}
