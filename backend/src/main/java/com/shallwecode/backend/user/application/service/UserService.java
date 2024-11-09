package com.shallwecode.backend.user.application.service;

import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.shallwecode.backend.user.application.dto.FindUserListDTO;
import com.shallwecode.backend.user.application.dto.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import com.shallwecode.backend.user.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // 회원 가입
    @Transactional
    public void saveUser(UserSaveDTO userSaveDTO) {
        userDomainService.validateNewUser(userSaveDTO); // 회원 유효성 검사
        userDomainService.save(userSaveDTO);

    }

    // 회원 닉네임 수정
    @Transactional
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        UserInfo userInfo = userRepository.findById(userUpdateDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("조회된 회원이 없습니다."));
        userDomainService.updateUser(userInfo, userUpdateDTO);
        userRepository.save(userInfo);
    }

    // 회원 삭제
    @Transactional
    public void deleteUser(Long userId) {
        userDomainService.deleteUser(userId);
    }


//    private final BCryptPasswordEncoder passwordEncoder;

//    @Transactional
//    public void createUser(CreateUserReqDTO newUser) {
//        UserEntity user = modelMapper.map(newUser, UserEntity.class);
//        user.encryptPassword(passwordEncoder.encode(newUser.getPwd()));
//        userRepository.save(user);
//    }

    /* 로그인 요청 시 AuthenticationManager를 통해서 호출 될 메소드 */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        /* 인증 토큰에 담긴 userId가 메소드로 넘어오므로 해당 값을 기준으로 DB에서 조회 한다. */
        UserInfo loginUser = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(loginUser.getAuth().toString()));

        return new User(String.valueOf(loginUser.getUserId()), "", grantedAuthorities);
    }

    public UserInfoResponse getUserInfoById(Long id) {

        UserInfo user = userRepository.findById(id).orElseThrow();
        return modelMapper.map(user, UserInfoResponse.class);

    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<UserInfo> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<FindUserListDTO> findRequestUser(Long loginUserId) {

        return userDomainService.findRequestUser(loginUserId);
    }
}
