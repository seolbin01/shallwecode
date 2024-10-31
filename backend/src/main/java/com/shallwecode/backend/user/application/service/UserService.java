package com.shallwecode.backend.user.application.service;

import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void createUser(CreateUserReqDTO newUser) {
        UserEntity user = modelMapper.map(newUser, UserEntity.class);
        user.encryptPassword(passwordEncoder.encode(newUser.getPwd()));
        userRepository.save(user);
    }

    /* 로그인 요청 시 AuthenticationManager를 통해서 호출 될 메소드 */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        /* 인증 토큰에 담긴 userId가 메소드로 넘어오므로 해당 값을 기준으로 DB에서 조회 한다. */
        UserEntity loginUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(loginUser.getUserRole().name()));

        return new User(loginUser.getUserId(), loginUser.getPwd(), grantedAuthorities);
    }

    public UserInfoResponse getUserInfoById(Long id) {

        UserEntity user = userRepository.findById(id).orElseThrow();
        return modelMapper.map(user, UserInfoResponse.class);

    }
}
