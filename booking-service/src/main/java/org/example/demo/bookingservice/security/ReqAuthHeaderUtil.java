package org.example.demo.bookingservice.security;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ReqAuthHeaderUtil {
    private static final String AUTH_HEADER = "AUTHORIZATION";
    private static final String BEARER = "Bearer "; //Bearer fdkjhfhdhkdjfjdshfjdsjfhskakjhfkjf655565554554

    public boolean hasToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);
        return authHeader != null && authHeader.startsWith(BEARER);
    }

    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER)) {
            return authHeader.substring(BEARER.length());
        }
        return null;
    }
}
