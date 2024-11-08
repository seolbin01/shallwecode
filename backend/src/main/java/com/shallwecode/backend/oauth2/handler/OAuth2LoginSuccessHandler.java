package com.shallwecode.backend.oauth2.handler;

import com.shallwecode.backend.oauth2.CustomOAuth2User;
import com.shallwecode.backend.oauth2.jwt.service.JwtService;
import com.shallwecode.backend.user.domain.aggregate.AuthType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if (oAuth2User.getAuth() == AuthType.GUEST) {

                String accessToken = jwtService.createAccessToken(oAuth2User.getUserId(), AuthType.GUEST);

                Cookie tokenCookie = jwtService.makeAccessTokenCookie(accessToken);

                response.addCookie(tokenCookie);
                loginSuccess(response, oAuth2User, oAuth2User.getUserId());
                response.sendRedirect("http://localhost:5173/sign-up");
            } else {
                loginSuccess(response, oAuth2User, oAuth2User.getUserId());
                response.sendRedirect("http://localhost:5173");
            }
        } catch (Exception e) {
            log.error("Authentication success handling failed", e);
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User, Long userId) throws IOException {
        String accessToken = jwtService.createAccessToken(userId, oAuth2User.getAuth());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getUserId(), refreshToken);
    }
}