package org.nure.julia.exceptions;

public class MessagePushException extends RuntimeException {

    public MessagePushException() {}

    public MessagePushException(String msg) {
        super(msg);
    }

    public MessagePushException(String msg, Throwable t) {
        super(msg, t);
    }
}
