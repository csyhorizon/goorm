package org.chinoel.goorm_missions.service.impl;

import org.chinoel.goorm_missions.service.Message;
import org.springframework.stereotype.Component;

@Component
public class HelloMessage implements Message {
    @Override
    public String getMessage() {
        return "Hello, World!";
    }
}
