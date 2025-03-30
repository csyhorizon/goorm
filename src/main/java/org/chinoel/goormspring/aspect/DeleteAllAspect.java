package org.chinoel.goormspring.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DeleteAllAspect {

    @Before("execution(* *..*Repository.deleteAll(..))")
    public void blockDeleteAll() {
        throw new UnsupportedOperationException("deleteAll은 제한된 명령");
    }
}
