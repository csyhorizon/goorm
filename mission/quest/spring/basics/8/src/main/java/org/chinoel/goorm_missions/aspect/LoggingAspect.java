package org.chinoel.goorm_missions.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* org.chinoel.goorm_missions.service..*.*(..))")
    public void logPointCut() {}

    @Before("LoggingAspect.logPointCut()")
    public void logBefore(JoinPoint joinPoint) {
//        System.out.println("logBefore");
//        System.out.println("joinPoint.getTarget() = " + joinPoint.getTarget());
    }

    @After("logPointCut()")
    public void logAfter(JoinPoint joinPoint) {
//        System.out.println("logAfter");
//        System.out.println("joinPoint.getTarget() = " + joinPoint.getTarget());
    }

    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
//        System.out.println("logAfterReturning");
//        System.out.println("joinPoint.getTarget() = " + joinPoint.getTarget());
    }

    @AfterThrowing(pointcut = "logPointCut()", throwing = "exception")
    public void logAfterThrowing(Throwable exception) {
//        System.out.println("logAfterThrowing");
//        System.out.println("exception = " + exception);
    }

    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        System.out.println("around");
        System.out.println("joinPoint = " + joinPoint.getTarget());
        System.out.println("name = " + joinPoint.getSignature().getName());

        return result;
    }
}
