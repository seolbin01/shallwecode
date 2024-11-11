package com.shallwecode.backend.oauth2.service;

import com.shallwecode.backend.oauth2.CustomOAuth2User;
import com.shallwecode.backend.oauth2.OAuthAttributes;
import com.shallwecode.backend.user.domain.aggregate.SocialType;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        UserInfo createdUser = getUser(extractAttributes, socialType);
        System.out.println(createdUser.getUserId());

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getAuth().toString())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getUserId(),
                createdUser.getAuth()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if(NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }

    private UserInfo getUser(OAuthAttributes attributes, SocialType socialType) {
        UserInfo findUser = userRepository.findByProviderAndSocialId(socialType,
                attributes.getOauth2UserInfo().getId()).orElse(null);

        if(findUser == null) {
            return saveUser(attributes, socialType);
        }
        return findUser;
    }

    private UserInfo saveUser(OAuthAttributes attributes, SocialType socialType) {
        UserInfo createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return userRepository.saveAndFlush(createdUser);
    }
}
