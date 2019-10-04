package org.nure.julia.exceptions;

public class SessionManagementException extends RuntimeException {

    public SessionManagementException() {}

    public SessionManagementException(String msg) {
        super(msg);
    }

    public SessionManagementException(String msg, Throwable t) {
        super(msg, t);
    }
}
