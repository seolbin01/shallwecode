package com.shallwecode.backend.user.domain.service;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.user.application.dto.FoundUserDTO;
import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDomainService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void updateUserDetails(UserInfo userInfo, UserUpdateDTO userUpdateDTO) {
        userInfo.updateUser(userUpdateDTO.getNickName());
    }


    public void deleteUser(Long userId){
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("user not found " + userId));
        userRepository.delete(userInfo);
    }

    public FoundUserDTO findById(Long userId) {
        UserInfo foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return modelMapper.map(foundUser, FoundUserDTO.class);
    }


}
