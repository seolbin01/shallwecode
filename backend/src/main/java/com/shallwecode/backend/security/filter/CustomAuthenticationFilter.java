package com.shallwecode.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shallwecode.backend.security.dto.LoginReqDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.ArrayList;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/v1/user/social", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        LoginReqDTO creds = new ObjectMapper().readValue(request.getInputStream(), LoginReqDTO.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(creds.getUserId(), creds.getPwd(), new ArrayList<>())
        );
    }
}
