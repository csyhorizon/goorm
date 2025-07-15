package org.chinoel.goormspring.exception;

public class PostNotFoundException extends IllegalArgumentException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
