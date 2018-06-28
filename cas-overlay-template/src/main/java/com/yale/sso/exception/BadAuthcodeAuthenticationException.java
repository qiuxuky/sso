package com.yale.sso.exception;

import org.jasig.cas.authentication.RootCasException;

/**
 * Created by LIUJIE on 2016/9/23.
 */

public class BadAuthcodeAuthenticationException extends RootCasException {

    /** Serializable ID for unique id. */
    private static final long serialVersionUID = 5501212207531289993L;

    /** Code description. */
    public static final String CODE = "error.authentication.authcode.bad";

    /**
     * Constructs a TicketCreationException with the default exception code.
     */
    public BadAuthcodeAuthenticationException() {
        super(CODE);
    }

    /**
     * Constructs a TicketCreationException with the default exception code and
     * the original exception that was thrown.
     *
     * @param throwable the chained exception
     */
    public BadAuthcodeAuthenticationException(final Throwable throwable) {
        super(CODE, throwable);
    }
}
