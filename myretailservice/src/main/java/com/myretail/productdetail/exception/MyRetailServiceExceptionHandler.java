package com.myretail.productdetail.exception;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.mongodb.MongoException;
import com.myretail.productdetail.model.Message;


@Component
@ControllerAdvice
public class MyRetailServiceExceptionHandler {
	
	
private final Logger log = Logger.getLogger(MyRetailServiceExceptionHandler.class);
	
	
	@ExceptionHandler(IOException.class)
	public final ResponseEntity<Message> ioExceptionHandler(IOException ex){
		log.info("in ioExceptionHandler");
		Message exceptionResponse= new Message(ex.getMessage()," JSON parsing failed while retreiving product name");
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(HttpClientErrorException.class)
	public final ResponseEntity<Message> httpClientErrorExceptionHandler(HttpClientErrorException ex){
		log.info("in httpClientErrorExceptionHandler");
		Message exceptionResponse= new Message(ex.getMessage(),"Product details are not available for requested id");
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<Message> methodArgTypeMiExceptionHandler(MethodArgumentTypeMismatchException ex){
		log.info("in httpClientErrorExceptionHandler");
		Message exceptionResponse= new Message(ex.getMessage()," Failed to convert value of argument, expected number but got something else  ");
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MongoException.class)
	public final ResponseEntity<Message> mongoExceptionHandler(MongoException ex){
		log.info("in mongoExceptionHandler");
		Message exceptionResponse= new Message(ex.getMessage()," Product details are not available in mongo dbcollection 'productprice' ");
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Message> exceptionHandler(Exception ex){
		log.info("in exceptionHandler");
		Message exceptionResponse= new Message(ex.getMessage(),ex.getMessage());
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<Message>(exceptionResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}

}
