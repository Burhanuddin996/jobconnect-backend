package com.jobconnect.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Small helper so services can find out "who is calling right now"
 * (the email inside the validated JWT) without repeating
 * SecurityContextHolder boilerplate everywhere.
 */
public class SecurityUtil {

    private SecurityUtil() {}

    public static String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
