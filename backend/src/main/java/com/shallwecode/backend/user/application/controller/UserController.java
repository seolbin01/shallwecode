package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.user.application.dto.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.application.service.UserService;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/api/v1/user")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserSaveDTO userSaveDTO) {
        userService.saveUser(userSaveDTO);
        return ResponseEntity.ok("회원 가입되었습니다.");
    }

    // 회원 닉네임 수정
    @PutMapping("/api/v1/user/nickname")
    public ResponseEntity<UserInfo> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
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
