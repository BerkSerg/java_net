package org.example.demo.bookingservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.example.demo.bookingservice.model.responses.JwtAuthenticationResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String USERNAME_PARAM = "email";
    public static final String AUTH_URL = "api/v1/auth/login";

    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    private final ReqAuthHeaderUtil reqAuthHeaderUtil;


    public JwtAuthenticationFilter(
            AuthenticationConfiguration authenticationConfiguration,
            ObjectMapper objectMapper,
            JwtUtil jwtUtil,
            ReqAuthHeaderUtil reqAuthHeaderUtil) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.setUsernameParameter(USERNAME_PARAM);
        this.setFilterProcessesUrl(AUTH_URL);
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.reqAuthHeaderUtil = reqAuthHeaderUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (reqAuthHeaderUtil.hasToken(request)) {
            String token = reqAuthHeaderUtil.extractToken(request);
            //TODO: !!!
            if (jwtUtil.isTokenExpired(token)) {
                throw new JwtException("Token is expired");
            }
            RefreshTokenAuthentication refreshTokenAuthentication = new RefreshTokenAuthentication(token);


            String email = jwtUtil.getEmailFromToken(token);
            if (email == null) {
                throw new JwtException("Invalid token");
            }
            return super.getAuthenticationManager().authenticate(refreshTokenAuthentication);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        response.setContentType("application/json");
        GrantedAuthority grantedAuthority = authResult.getAuthorities().stream().findFirst().orElseThrow(IllegalStateException::new);
        String userName = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        String issuer = request.getRequestURL().toString();
        String token = jwtUtil.generateAccessToken(userName, grantedAuthority.getAuthority(), issuer);
        objectMapper.writeValue(response.getOutputStream(), new JwtAuthenticationResponse(token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
