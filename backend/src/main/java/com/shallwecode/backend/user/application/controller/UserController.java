package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.application.service.UserService;
import com.shallwecode.backend.user.domain.aggregate.User;
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
    @PutMapping("/{nickName}")
    public ResponseEntity<User> UpdateUser(@PathVariable String nickName, @RequestBody UserUpdateDTO userUpdateDTO) {
        userUpdateDTO.setNickName(nickName);
        userService.UpdateUser(userUpdateDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping
    public ResponseEntity<String> DeleteUser(@RequestParam("userId") Long userId) throws Exception {
        userService.DeleteUser(userId);
        return ResponseEntity.ok("회원이 삭제되었습니다.");
    }
}
