package exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(CustomException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public Map<String,String> resourceNotFoundException(CustomException ex, WebRequest request, HttpServletResponse res) {
	  Map<String,String> resultBody= new HashMap<String,String>();
	  resultBody.put("code", ex.getCode());
	  resultBody.put("message", ex.getMsg());
    return resultBody;
  }
  
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String,String> globalExceptionHandler(Exception ex, WebRequest request, HttpServletResponse res) {
	  Map<String,String> resultBody= new HashMap<String,String>();
	  resultBody.put("code", Integer.toString(res.getStatus()));
	  resultBody.put("message", ex.getMessage());
    return resultBody;
  }
}
