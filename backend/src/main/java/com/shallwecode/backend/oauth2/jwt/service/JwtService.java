package com.shallwecode.backend.oauth2.jwt.service;

import com.shallwecode.backend.user.application.service.UserService;
import com.shallwecode.backend.user.domain.aggregate.AuthType;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.*;

@Service
@Getter
@Slf4j
@Transactional
public class JwtService {

    private final UserService userService;

    @Value("${jwt.access.expiration}")
    private Integer accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Integer refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "accessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "refreshToken";
    private static final String USER_ID_CLAIM = "userId";
    private static final String AUTH_CLAIM = "auth";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;
    private final Key key;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public JwtService(
            @Value("${token.secret}") String secretKey,
            UserRepository userRepository,
            UserService userService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public String createAccessToken(Long userId, AuthType auth) {
        Claims claims = Jwts.claims().setSubject(ACCESS_TOKEN_SUBJECT);
        claims.put(USER_ID_CLAIM, userId);
        claims.put(AUTH_CLAIM, auth);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationPeriod))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String createRefreshToken() {
        Claims claims = Jwts.claims().setSubject(REFRESH_TOKEN_SUBJECT);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Long extractUserId(String accessToken) {
        Object userIdClaim = parseClaims(accessToken).get(USER_ID_CLAIM);

        Long userId;
        if (userIdClaim instanceof Integer) {
            userId = ((Integer) userIdClaim).longValue();
        } else if (userIdClaim instanceof Long) {
            userId = (Long) userIdClaim;
        } else {
            throw new IllegalArgumentException("User ID claim is not of expected type.");
        }

        return userId;
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public void updateRefreshToken(Long userId, String refreshToken) { // 매개변수 변경
        userRepository.findById(userId)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(false);
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(accessTokenExpirationPeriod);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(false);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(refreshTokenExpirationPeriod);
        response.addCookie(refreshTokenCookie);
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Token claims empty {}", e);
        }

        return false;
    }

    public void saveAuthentication(Long userId) {
        System.out.println("UserId: " + userId);
        UserDetails userDetails = userService.loadUserByUsername(String.valueOf(userId));
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Cookie makeAccessTokenCookie(String accessToken) {
        Cookie tokenCookie = new Cookie("accessToken", accessToken);
        tokenCookie.setHttpOnly(false);
        tokenCookie.setSecure(false);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(accessTokenExpirationPeriod);
        return tokenCookie;
    }
}
