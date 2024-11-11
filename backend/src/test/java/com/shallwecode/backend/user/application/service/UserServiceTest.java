package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.user.application.dto.user.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteUserTest() {
        // 기존 회원 데이터로 삭제 테스트
        // when
        userService.deleteUser(71L);

        // then
        Optional<UserInfo> findUser = userRepository.findById(71L);
        assertTrue(findUser.isEmpty());
    }

    @Test
    @DisplayName("회원 닉네임 수정 테스트")
    void updateUserTest() {
        // 기존 회원 데이터로 닉네임 수정 테스트
        Long userId = 71L;
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setNickname("꺄르륵");

        // when
        userService.updateUser(userUpdateDTO,userId);

        // then
        Optional<UserInfo> findUser = userRepository.findById(userId);
        assertEquals("꺄르륵", findUser.get().getNickname());
    }

}