package com.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/* RestControllerAdvice allows you to define exception handlers that apply to all @RestController controllers, 
 * providing a centralized place to manage error responses.
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotfoundException.class)
	public ResponseEntity<?> resourcenotfound(ResourceNotfoundException r){
		String msg=r.getMessage();
		Map  m=new HashMap<>();
		m.put("message", msg);
		return new ResponseEntity(m,HttpStatus.NOT_FOUND);
	}
	
	//for validation handling
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?>  validhandler(MethodArgumentNotValidException ex){
		Map  m=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(
				error->{
				 String field = ((FieldError)error).getField();
				 String defaultMessage = error.getDefaultMessage();
				 m.put(field, defaultMessage);
				}
				);
		
		return new ResponseEntity(m,HttpStatus.BAD_REQUEST);
	}
}
