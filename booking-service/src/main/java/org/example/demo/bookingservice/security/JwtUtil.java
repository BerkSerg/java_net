package org.example.demo.bookingservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.bookingservice.model.User;
import org.example.demo.bookingservice.model.enums.Role;
import org.example.demo.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 30*60*1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 600000;

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(String sub, String authority, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        log.info("role: " + authority);


        Date date = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME);
        log.info("withExpiresAt: " + date);
        return
                JWT.create()
                        .withSubject(sub)
                        .withExpiresAt(date)
                        .withClaim("role", authority)
                        .withIssuer(issuer)
                        .sign(algorithm);
    }

    public Authentication build(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        String username = decodedJWT.getSubject(); // email address
        UserDetails userDetails = userService.userDetailedService().loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getEmailFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }

    public boolean isTokenExpired(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token).getExpiresAt().before(new Date());
    }
}
