package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.application.service.UserService;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //회원 닉네임 수정
    @PutMapping("/nickName")
    public ResponseEntity<UserInfo> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        userUpdateDTO.setNickName(userUpdateDTO.getNickName());
        userService.updateUser(userUpdateDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam("userId") Long userId) throws Exception {
        userService.deleteUser(userId);
        return ResponseEntity.ok("회원이 삭제되었습니다.");
    }
}
