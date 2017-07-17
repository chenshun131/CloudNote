package com.chenshun.component.exception;

/**
 * User: chenshun131 <p />
 * Time: 17/7/11 21:41  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
