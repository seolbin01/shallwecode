package com.shallwecode.backend.oauth2;

import com.shallwecode.backend.user.domain.aggregate.AuthType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Map;

/**
 * DefaultOAuth2User를 상속하고, email과 role 필드를 추가로 가진다.
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private Long userId; // userId 필드 추가
    private AuthType auth;    // auth 필드 추가 (필요에 따라 타입 변경 가능)

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            Long userId, AuthType auth) { // 매개변수 변경
        super(authorities, attributes, nameAttributeKey);
        this.userId = userId; // userId 설정
        this.auth = auth;      // auth 설정
    }
}