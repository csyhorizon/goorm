package com.example.backend.domain.global.aop;

import com.example.backend.domain.global.config.DataSourceContextHolder;
import com.example.backend.domain.global.config.DataSourceType;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class ReadOnlyRoutingAspect {

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {}

    @Before("transactionalMethods() && @annotation(tx)")
    public void setRoutingDataSource(Transactional tx) {
        if (tx.readOnly()) {
            DataSourceContextHolder.set(DataSourceType.READ);
        } else {
            DataSourceContextHolder.set(DataSourceType.WRITE);
        }
    }

    @After("transactionalMethods()")
    public void clearRoutingDataSource() {
        DataSourceContextHolder.clear();
    }
}
