package com.ifood.ifood.exceptions;

public class NotFoundCitytException extends Exception {


    public NotFoundCitytException() {
        super();
    }

    public NotFoundCitytException(String message) {
        super(message);
    }

    public NotFoundCitytException(String message, Throwable e) {
        super(message, e);
    }
}
