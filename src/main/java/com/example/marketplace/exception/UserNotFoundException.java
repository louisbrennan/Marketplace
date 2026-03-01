package com.example.marketplace.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() { super("User not found"); }
    public UserNotFoundException(String username) {
        super("User " + username + " not found");
    }
}
