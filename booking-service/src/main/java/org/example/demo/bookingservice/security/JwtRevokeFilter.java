package org.example.demo.bookingservice.security;

import lombok.RequiredArgsConstructor;
import org.example.demo.bookingservice.utils.BlackList;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRevokeFilter extends OncePerRequestFilter {
    private final ReqAuthHeaderUtil reqAuthHeaderUtil;
    private static final  String REVOKE_URL = "api/v1/token/revoke";
    private final AntPathRequestMatcher revokeMatcher = new AntPathRequestMatcher(REVOKE_URL, "POST");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (revokeMatcher.matches(request)){
            String token = reqAuthHeaderUtil.extractToken(request);
            BlackList.revokeToken(token);
            response.setStatus(200);
        } else {
            filterChain.doFilter(request, response);
        }


    }
}
