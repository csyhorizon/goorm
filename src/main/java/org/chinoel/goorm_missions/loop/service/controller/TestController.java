package org.chinoel.goorm_missions.loop.service.controller;

import org.chinoel.goorm_missions.fixed.service.ServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final ServiceA serviceA;

    @Autowired
    public TestController(ServiceA serviceA) {
        this.serviceA = serviceA;
    }
}
