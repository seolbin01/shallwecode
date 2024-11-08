package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.user.application.dto.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.application.service.UserService;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "회원 관련 API")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/api/v1/user")
    @Operation(summary = "회원 가입", description = "회원 가입을 시도한다.")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserSaveDTO userSaveDTO) {
        userService.saveUser(userSaveDTO);
        return ResponseEntity.ok("회원 가입되었습니다.");
    }

    // 회원 닉네임 수정
    @PutMapping("/api/v1/user/nickname")
    @Operation(summary = "회원 닉네임 수정", description = "회원 닉네임을 수정한다.")
    public ResponseEntity<UserInfo> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUser(userUpdateDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping
    @Operation(summary = "회원 삭제", description = "회원을 삭제한다.")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") Long userId) throws Exception {
        userService.deleteUser(userId);
        return ResponseEntity.ok("회원이 삭제되었습니다.");
    }

    // 회원 조회
    @GetMapping
    @Operation(summary = "회원 전체 조회", description = "전체 회원을 조회한다.")
    public ResponseEntity<List<UserInfo>> getAllUser() {
        List<UserInfo> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
}
