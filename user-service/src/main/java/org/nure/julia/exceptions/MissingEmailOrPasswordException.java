package org.nure.julia.exceptions;

public class MissingEmailOrPasswordException extends RuntimeException {

    public MissingEmailOrPasswordException() {}

    public MissingEmailOrPasswordException(String msg) {
        super(msg);
    }

    public MissingEmailOrPasswordException(String msg, Throwable t) {
        super(msg, t);
    }
}
