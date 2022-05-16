package com.tenpo.challenge.user;

public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }
}
