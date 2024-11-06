package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.user.application.dto.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import com.shallwecode.backend.user.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        UserInfo userInfo = modelMapper.map(userSaveDTO, UserInfo.class);
        userDomainService.validateNewUser(userInfo); // 회원 유효성 검사
        userRepository.save(userInfo);

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

    // 회원 상세 조회
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<UserInfo> getAllUsers() {
        return userRepository.findAll();
    }
}
