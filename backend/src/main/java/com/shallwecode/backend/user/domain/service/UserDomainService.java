package com.shallwecode.backend.user.domain.service;

import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDomainService {
    private final UserRepository userRepository;


    public void updateUserDetails(UserInfo userInfo, UserUpdateDTO userUpdateDTO) {
        userInfo.updateUser(userUpdateDTO.getNickName());
    }


    public void deleteUser(Long userId){
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("user not found " + userId));
        userRepository.delete(userInfo);
    }


}
