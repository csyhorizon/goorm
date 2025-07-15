package org.chinoel.goorm_missions.loop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {

    private final ServiceB serviceB;

    @Autowired
    public ServiceA(ServiceB serviceB) {
        this.serviceB = serviceB;
    }

    public String getMessageA() {
        return "Message from A --> " + serviceB.getMessageB();
    }
}
