package z_reference;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import app.Application;
import exception.ErrorResponse404;
import exception.ScimObjectNotFound;

@ControllerAdvice(basePackageClasses = Application.class)
public class GlobalErrorHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ScimObjectNotFound.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        return new ResponseEntity<>(new ErrorResponse404("test"), HttpStatus.NOT_FOUND);
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}