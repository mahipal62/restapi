package com.myretail.productdetail.exception;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.myretail.productdetail.aspect.AdderAround;
import com.myretail.productdetail.model.Message;


@Component
@ControllerAdvice
public class MyRetailServiceExceptionHandler {
	
	
	
	@AdderAround
	@ExceptionHandler(IOException.class)
	public final ResponseEntity<Message> ioExceptionHandler(IOException ex){
		Message exceptionResponse= new Message(ex.getMessage()," JSON parsing failed while retreiving product name");
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.NOT_FOUND);
	}
	
	@AdderAround
	@ExceptionHandler(HttpClientErrorException.class)
	public final ResponseEntity<Message> httpClientErrorExceptionHandler(HttpClientErrorException ex){
		Message exceptionResponse= new Message(ex.getMessage(),"Product details are not available for requested id");
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),ex.getStatusCode());
	}
	
	@AdderAround
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<Message> methodArgTypeMiExceptionHandler(MethodArgumentTypeMismatchException ex){
		Message exceptionResponse= new Message(ex.getMessage()," Failed to convert value of argument, expected number but got something else  ");
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
	
	@AdderAround
	@ExceptionHandler(ProductNotFoundException.class)
	public final ResponseEntity<Message> notExceptionHandler(ProductNotFoundException ex){
		Message exceptionResponse= new Message(ex.getMessage(),ex.getMessage());
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.NOT_FOUND);
	}
	@AdderAround
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Message> exceptionHandler(Exception ex){
		Message exceptionResponse= new Message(ex.getMessage(),ex.getMessage());
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}

}
