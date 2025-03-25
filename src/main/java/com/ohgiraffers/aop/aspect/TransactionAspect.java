package com.ohgiraffers.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Aspect
@Component
public class TransactionAspect {

    private final TransactionTemplate transactionTemplate;

    public TransactionAspect(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object handleTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=== 트랜잭션 시작 ===");
        System.out.println("메서드 : " + joinPoint.getSignature().getName());
        return transactionTemplate.execute(status -> {
            try {
                System.out.println("=== 메서드 실행 중 ===");
                Object result = joinPoint.proceed();
                System.out.println("=== 트랜잭션 정상 처리 ===");
                return result;
            } catch (Throwable e) {
                System.out.println("=== 예외 발생 : 롤백 ===");
                System.out.println("예외 메시지 : " + e.getMessage());
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
    }
}
