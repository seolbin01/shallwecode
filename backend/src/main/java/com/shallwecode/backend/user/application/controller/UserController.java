package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.common.util.CustomUserUtils;
import com.shallwecode.backend.user.application.dto.FindUserDetailDTO;
import com.shallwecode.backend.user.application.dto.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.UserUpdateDTO;
import com.shallwecode.backend.user.application.service.UserService;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PutMapping
    @Operation(summary = "회원 가입", description = "회원 가입을 시도한다.")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserSaveDTO userSaveDTO) {
        userService.saveUser(userSaveDTO);
        return ResponseEntity.ok("회원 가입되었습니다.");
    }

    // 회원 닉네임 수정
    @PutMapping("nickname")
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

    @GetMapping("/profile")
    @Operation(summary = "내 정보 조회", description = "회원을 상세 조회한다.")
    public ResponseEntity<FindUserDetailDTO> findUserDetail() {
        Long loginUserId = CustomUserUtils.getCurrentUserSeq();

        FindUserDetailDTO findUserDetailDTO = userService.findUserDetail(loginUserId);

        return ResponseEntity.ok(findUserDetailDTO);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃을 한다.")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 쿠키 삭제
        Cookie accessCookie = new Cookie("accessToken", null);
        accessCookie.setHttpOnly(false);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);

        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setHttpOnly(false);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return ResponseEntity.ok("로그아웃 성공");
    }
}
