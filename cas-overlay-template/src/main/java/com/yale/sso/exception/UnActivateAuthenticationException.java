package com.yale.sso.exception;

import javax.security.auth.login.LoginException;

/**
 * Created by LIUJIE on 2016/9/24.
 */
public class UnActivateAuthenticationException extends LoginException {
    private static final long serialVersionUID = 5501212207531289993L;

    /**
     * Constructs a FailedLoginException with no detail message. A detail
     * message is a String that describes this particular exception.
     */
    public UnActivateAuthenticationException() {
        super();
    }

    /**
     * Constructs a FailedLoginException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *
     * @param msg the detail message.
     */
    public UnActivateAuthenticationException(String msg) {
        super(msg);
    }
}