package org.nure.julia.exceptions;

public class UserEmailExistsException extends RuntimeException {

    public UserEmailExistsException() {}

    public UserEmailExistsException(String msg) {
        super(msg);
    }

    public UserEmailExistsException(String msg, Throwable t) {
        super(msg, t);
    }
}
