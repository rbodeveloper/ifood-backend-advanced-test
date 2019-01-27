package com.ifood.ifood.exceptions;

public class UnexpectedException extends Exception {


    public UnexpectedException() {
        super();
    }

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(String message, Throwable e) {
        super(message, e);
    }
}
