package org.example.demo.bookingservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.example.demo.bookingservice.security.JwtAuthenticationFilter.AUTH_URL;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final ReqAuthHeaderUtil reqAuthHeaderUtil;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals(AUTH_URL)){
            filterChain.doFilter(request, response);
        } else if (reqAuthHeaderUtil.hasToken(request)) {
            String token = reqAuthHeaderUtil.extractToken(request);
            Authentication auth = jwtUtil.build(token);
             if (auth != null && auth.isAuthenticated()) {
                 SecurityContextHolder.getContext().setAuthentication(auth);
                 filterChain.doFilter(request, response);

             } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
             }

        } else {
            filterChain.doFilter(request, response);
        }

    }
}
