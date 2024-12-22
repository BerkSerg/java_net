package org.example.demo.bookingservice.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.bookingservice.utils.BlackList;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenAuthProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        if (jwtUtil.isTokenExpired(token) || BlackList.isRevoked(token)){
            log.error("Token is expired or has been revoked: {}", token);
            throw new RuntimeException("Token is expired or has been revoked");
        }

        try {
            return jwtUtil.build(token);
        } catch (JWTVerificationException e){
            log.error("JWT verification failed: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
