package org.chinoel.goorm_missions.fixed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    private final ServiceA serviceA;

    @Autowired
    public ServiceB(@Lazy ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public String getMessageB() {
        return "Message from B";
    }
}
