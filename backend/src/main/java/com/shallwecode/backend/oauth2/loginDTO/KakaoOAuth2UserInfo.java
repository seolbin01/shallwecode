package com.shallwecode.backend.oauth2.loginDTO;

import lombok.Getter;

import java.util.Map;

@Getter
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }
}
