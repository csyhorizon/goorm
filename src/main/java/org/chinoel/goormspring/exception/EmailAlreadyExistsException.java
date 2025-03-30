package org.chinoel.goormspring.exception;

public class EmailAlreadyExistsException extends IllegalArgumentException{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
