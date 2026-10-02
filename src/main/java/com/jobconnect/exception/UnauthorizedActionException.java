package com.jobconnect.exception;

/**
 * Thrown when an authenticated user tries to act outside what their
 * role/ownership allows - e.g. a recruiter editing another recruiter's job.
 */
public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
