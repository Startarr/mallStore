package com.hxx.mallstore.service.ex;

/**
 * @author
 * @title
 */
public class ValidCodeNotMatchException extends ServiceException{
    public ValidCodeNotMatchException() {
    }

    public ValidCodeNotMatchException(String message) {
        super(message);
    }

    public ValidCodeNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidCodeNotMatchException(Throwable cause) {
        super(cause);
    }

    public ValidCodeNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
