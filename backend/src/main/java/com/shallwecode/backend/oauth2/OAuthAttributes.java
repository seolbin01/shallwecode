package com.shallwecode.backend.oauth2;

import com.shallwecode.backend.oauth2.loginDTO.*;
import com.shallwecode.backend.user.domain.aggregate.AuthType;
import com.shallwecode.backend.user.domain.aggregate.SocialType;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Map;
import java.util.UUID;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로
 * 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(SocialType provider,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (provider == SocialType.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (provider == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }


    public UserInfo toEntity(SocialType provider, OAuth2UserInfo oauth2UserInfo) {
        UserInfo craeteUserInfo = new UserInfo();

        return craeteUserInfo.createUser(provider, oauth2UserInfo.getId(), UUID.randomUUID() + "@socialUser.com", AuthType.GUEST);
    }
}