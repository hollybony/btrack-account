package com.gs.btrack.account.security.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author LENOVO
 */
public class InvalidJwtAuthenticationException extends AuthenticationException {

    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }

    public InvalidJwtAuthenticationException(String message, Throwable ex) {
        super(message, ex);
    }
}
