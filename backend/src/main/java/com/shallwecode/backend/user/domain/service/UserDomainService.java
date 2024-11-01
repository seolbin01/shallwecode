package com.shallwecode.backend.user.domain.service;

import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.User;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDomainService {
    private final UserRepository userRepository;


    public void updateUserDetails(User user, UserUpdateDTO userUpdateDTO) {
        user.UpdateUser(userUpdateDTO.getNickName());
    }


    public void DeleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("user not found " + userId));
        userRepository.delete(user);
    }


}
