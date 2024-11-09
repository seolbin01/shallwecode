package com.shallwecode.backend.oauth2.jwt.filter;

import com.shallwecode.backend.oauth2.jwt.service.JwtService;
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

    private static final AntPathRequestMatcher[] SWAGGER_URLS = {
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/login"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/login/oauth2/code/kakao", "GET"),
            new AntPathRequestMatcher("/login/oauth2/code/naver", "GET"),
            new AntPathRequestMatcher("/login/oauth2/code/google", "GET"),
            new AntPathRequestMatcher("/api/v1/problem/guest", "GET"),
            new AntPathRequestMatcher("/api/v1/user", "PUT")
    };
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("doFilterInternal");

        // WebSocket 요청 예외 처리: Upgrade 헤더가 websocket인 경우 필터를 우회합니다.
        if ("websocket".equalsIgnoreCase(request.getHeader("Upgrade"))) {
            filterChain.doFilter(request, response);
            return;
        }

        for (AntPathRequestMatcher url : SWAGGER_URLS) {
            if (url.matches(request)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        Optional<String> refreshToken = jwtService.extractToken(request, "refresh");

        if (refreshToken.isPresent() && jwtService.isTokenValid(refreshToken.get())) {
            String newAccessToken = jwtService.reIssueAccessToken(refreshToken.get());
            Long userId = jwtService.extractUserId(newAccessToken);
            String newRefreshToken = jwtService.createRefreshToken(userId);

            response.setHeader("accessToken", newAccessToken);
            response.setHeader("refreshToken", newRefreshToken);

            log.info("accessToken {} ", newAccessToken);
            log.info("refreshToken {} ", newRefreshToken);

            jwtService.updateRefreshToken(userId, newRefreshToken);

            jwtService.saveAuthentication(jwtService.extractUserId(newAccessToken));

            filterChain.doFilter(request, response);
        }


        Optional<String> accessToken = jwtService.extractToken(request, "access");

        if (accessToken.isPresent()) {
            if (jwtService.isTokenValid(accessToken.get())){
                jwtService.saveAuthentication(jwtService.extractUserId(accessToken.get()));
                log.info("accessToken {} ", accessToken.get());
                filterChain.doFilter(request, response); // 다음 필터로 요청 전달
                return;
            }

        }

        // 모든 토큰이 유효하지 않은 경우 401 에러
        if (!response.isCommitted()) { // 응답이 커밋되지 않았을 때만 에러 전송
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid tokens");
        }
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        for (String url : SWAGGER_URLS) {
//            if (new AntPathRequestMatcher(url).matches(request)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
