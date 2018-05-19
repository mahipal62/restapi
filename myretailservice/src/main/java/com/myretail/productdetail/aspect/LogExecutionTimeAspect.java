package com.myretail.productdetail.aspect;


import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LogExecutionTimeAspect {
	private final Logger logger = Logger.getLogger(this.getClass());
	 @Around("@annotation(LogExecutionTime)")
	    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	        final long start = System.currentTimeMillis();

	        final Object proceed = joinPoint.proceed();

	        final long executionTime = System.currentTimeMillis() - start;

	        logger.info("Method "+joinPoint.getSignature()+ " executed in " + executionTime + "ms");

	        return proceed;
	    }

}
