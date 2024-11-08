package com.shallwecode.backend.oauth2.jwt.service;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.user.application.service.UserService;
import com.shallwecode.backend.user.domain.aggregate.AuthType;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import com.shallwecode.backend.user.domain.service.UserDomainService;
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
import java.util.Date;
import java.util.Optional;

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
    private final UserDomainService userDomainService;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public JwtService(
            @Value("${token.secret}") String secretKey,
            UserRepository userRepository,
            UserService userService,
            UserDomainService userDomainService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDomainService = userDomainService;
    }

    // 엑세스 토큰 생성
    // userId, auth를 넣는다.
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

    // 리프레시 토큰 생성
    // userId만 넣는다.
    public String createRefreshToken(Long userId) {
        Claims claims = Jwts.claims().setSubject(REFRESH_TOKEN_SUBJECT);
        claims.put(USER_ID_CLAIM, userId);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    // 토큰에서 userId를 추출
    public Long extractUserId(String token) {
        return Long.valueOf(parseClaims(token).get(USER_ID_CLAIM).toString());
    }

    // request 에서 리프레시 토큰을 추출
    public Optional<String> extractToken(HttpServletRequest request, String tokenType) {

        String authorizationHeader;
        if (tokenType.equals("refresh")) authorizationHeader = request.getHeader(refreshHeader);
        else authorizationHeader = request.getHeader(accessHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return Optional.of(authorizationHeader.substring(7));
        }

        return Optional.empty();
    }

    // refreshToken을 다시 저장하는 메서드
    public void updateRefreshToken(Long userId, String refreshToken) { // 매개변수 변경
        userDomainService.updateRefreshToken(userId, refreshToken);
    }

    // 로그인 성공시 쿠키로 토큰 보내는 메서드
    public void sendAccessAndRefreshTokenByCookie(HttpServletResponse response, String accessToken, String refreshToken) {
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

    // authentication 저장
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

    // 엑세스 토큰 재발급
    public String reIssueAccessToken(String refreshToken) {
        // 리프레시 토큰에서 사용자 ID 추출
        Long userId = extractUserId(refreshToken);
        UserInfo findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        // 새로운 액세스 토큰 생성
        return createAccessToken(userId, findUser.getAuth());
    }
}
