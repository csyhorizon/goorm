package com.ohgiraffers.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class TransactionAspect {

//    @AfterThrowing(pointcut = "@annotation(transactional)", throwing = "exception")
//    public void rollbackTransactionOnException(Transactional transactional, Exception exception) {
//        System.out.println("예외 발생, 롤백" + exception.getMessage());
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//    }


    @Around("@annotation(transactional)")
    public Object rollbackTransaction(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            System.out.println("트랜잭션 정상 처리 -- 트랜잭션");
            return result;
        } catch (Exception e) {
            System.out.println("예외 발생, 롤백 -- 트랜잭션");
            throw e;
        }
    }
}
