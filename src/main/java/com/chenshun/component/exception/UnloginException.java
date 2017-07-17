package com.chenshun.component.exception;

/**
 * User: chenshun131 <p />
 * Time: 17/7/11 21:38  <p />
 * Version: V1.0  <p />
 * Description: 未登录异常 <p />
 */
public class UnloginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnloginException() {
        super("用户未登录");
    }

}
