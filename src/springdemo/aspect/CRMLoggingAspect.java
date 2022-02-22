package springdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());	
	
	// setup pointcut declarations               controller.(Match on any class in the package).(Match on any method in the class).(Match on any number of arguments)
	@Pointcut("execution(* springdemo.controller.*.*.(..))")
	private void forControllerPackage() {}
	
	// do the same for service and dao 
	@Pointcut("execution(* springdemo.service.*.*.(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* springdemo.dao.*.*.(..))")
	private void forDAOackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOackage()")
	private void forAppFlow() {}
	
	// add @Before advice
	
	
	
	
	
	
	
	// add @AfterReturning advice
	
}
