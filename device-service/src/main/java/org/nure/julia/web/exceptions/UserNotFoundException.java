package org.nure.julia.web.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {}

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
