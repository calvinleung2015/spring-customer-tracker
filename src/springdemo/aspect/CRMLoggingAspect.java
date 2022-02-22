package springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());	
	
	// setup pointcut declarations               controller.(Match on any class in the package).(Match on any method in the class)(Match on any number of arguments)
	// It means run this code before target object method * springdemo.controller.*.*(..)
	@Pointcut("execution(* springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	// do the same for service and dao 
	@Pointcut("execution(* springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	// add @Before advice
	@Before("forAppFlow()")
	// JoinPoint has metadata about method call
	public void before(JoinPoint theJoinPoint) {
		
		// display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===>> in @Before: calling method: " + theMethod);
		
		// display the arguments to the method
		
		// get the arguments   //INFO: ==>> argument: 4 means customerID 4
		Object[] args = theJoinPoint.getArgs();
		
		// loop through and display args
		for (Object tempArg : args) {
			myLogger.info("===>> argument: " + tempArg);
		}
		
		
	}
	
	
	// add @AfterReturning advice
	// run after the method (success execution)
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		// display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===>> in @AfterReturning: from method: " + theMethod);
		
		
		// display data returned
		myLogger.info("===>> result: " + theResult);
	}
	
}
