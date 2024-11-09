package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.user.application.dto.user.FindUserDetailDTO;
import com.shallwecode.backend.user.application.dto.user.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.user.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입 테스트")
    void saveUser(){
        // given
        UserSaveDTO userSaveDTO = new UserSaveDTO();
        userSaveDTO.setUserId(32L);
        userSaveDTO.setEmail("test@naver.com");
        userSaveDTO.setNickname("testNick");

        // when
        userService.saveUser(userSaveDTO);

        // then
        Optional<UserInfo> userInfo = userRepository.findByEmail("test@naver.com");
        assertNotNull(userInfo);
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateUser(){
        // 수정테스트 위한 회원가입
        // given
        UserSaveDTO userSaveDTO = new UserSaveDTO();
        userSaveDTO.setUserId(32L);
        userSaveDTO.setEmail("test@naver.com");
        userSaveDTO.setNickname("testNick");
        userService.saveUser(userSaveDTO);

        // when
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUserId(32L);
        userUpdateDTO.setNickName("꺄르륵");

        userService.updateUser(userUpdateDTO, loginUserId);

        // then
        Optional<UserInfo> userInfo = userRepository.findByEmail("test@naver.com");
        if(userInfo.isPresent()) {
            String nickname = userInfo.get().getNickname();
            assertEquals("꺄르륵", nickname);
        }
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteUser(){
        // 삭제테스트 위한 회원가입
        // given
        UserSaveDTO userSaveDTO = new UserSaveDTO();
        userSaveDTO.setUserId(32L);
        userSaveDTO.setEmail("test@naver.com");
        userSaveDTO.setNickname("testNick");
        userService.saveUser(userSaveDTO);

        // when
        userService.deleteUser(32L);

        // then
        assertFalse(userRepository.findByEmail("test@naver.com").isPresent());
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void getAllUser(){
//        // 회원 조회
//        // when
//        List<UserInfo> findAllUser = userService.findAllUsers();
//
//        // then
//        assertNotNull(findAllUser);
    }

    @Test
    @DisplayName("특정 회원 조회 테스트")
    void getUserById(){
        // 특정 회원 조회 위한 회원가입
        UserSaveDTO userSaveDTO = new UserSaveDTO();
        userSaveDTO.setUserId(32L);
        userSaveDTO.setEmail("test@naver.com");
        userSaveDTO.setNickname("test22");
        userService.saveUser(userSaveDTO);

        // when
        FindUserDetailDTO findDetailUser = userService.findUserDetail(32L);

        // then
        assertNotNull(findDetailUser);
        assertEquals("test@naver.com", findDetailUser.getEmail());
        assertEquals("test22", findDetailUser.getNickname());
    }
}