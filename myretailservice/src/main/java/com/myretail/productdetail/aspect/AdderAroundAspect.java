package com.myretail.productdetail.aspect;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdderAroundAspect {
	Logger logger = Logger.getLogger(this.getClass());
	 @Around("@annotation(AdderAround)")
	public Object aroundAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method "+ joinPoint.getSignature().getName()+ " Entry and Arguments passed to method are: " + Arrays.toString(joinPoint.getArgs()));
        final Object result = joinPoint.proceed();
        logger.info("Method "+ joinPoint.getSignature().getName()+ " Exit and Result from method is: " + result);
        return result;
    }

}
