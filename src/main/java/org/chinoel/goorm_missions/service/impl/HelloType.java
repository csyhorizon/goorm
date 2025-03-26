package org.chinoel.goorm_missions.service.impl;

import org.chinoel.goorm_missions.service.MessageType;
import org.springframework.stereotype.Component;

@Component
public class HelloType implements MessageType {

    @Override
    public String getType() {
        return "SEND";
    }
}
