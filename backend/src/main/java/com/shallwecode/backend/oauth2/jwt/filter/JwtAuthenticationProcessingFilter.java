package com.shallwecode.backend.oauth2.jwt.filter;

import com.shallwecode.backend.oauth2.jwt.service.JwtService;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login"; // "/login"으로 들어오는 요청은 Filter 작동 X
    private static final String[] SWAGGER_URLS = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**"
    };
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("doFilterInternal");

        if (request.getRequestURI().contains(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }


//        checkAccessTokenAndAuthentication(request, response, filterChain);
        Optional<String> accessToken = jwtService.extractAccessToken(request);

        if (accessToken.isPresent()) {
            if (jwtService.isTokenValid(accessToken.get())) {
                jwtService.saveAuthentication(jwtService.extractUserId(accessToken.get()));
                filterChain.doFilter(request, response);
            } else {
                Optional<String> refreshToken = jwtService.extractRefreshToken(request);
                if (refreshToken.isPresent() && jwtService.isTokenValid(refreshToken.get())) {
                    checkRefreshTokenAndReIssueAccessToken(response, refreshToken.get());
                }
                filterChain.doFilter(request, response);
            }
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        for (String url : SWAGGER_URLS) {
            if (new AntPathRequestMatcher(url).matches(request)) {
                return true; // Swagger URL인 경우 필터를 적용하지 않음
            }
        }
        return false; // 나머지 URL에서는 필터를 적용
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(user -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(user);
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(user.getUserId(), user.getAuth()), // user.getEmail() 대신 user.getUserId() 사용
                            reIssuedRefreshToken);
                });
    }

    private String reIssueRefreshToken(UserInfo user) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        user.updateRefreshToken(reIssuedRefreshToken);
        userRepository.saveAndFlush(user);
        return reIssuedRefreshToken;
    }




}
