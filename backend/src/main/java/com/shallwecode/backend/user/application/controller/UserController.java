package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.common.util.CustomUserUtils;
import com.shallwecode.backend.user.application.dto.user.FindUserListDTO;
import com.shallwecode.backend.user.application.dto.user.FindUserDetailDTO;
import com.shallwecode.backend.user.application.dto.user.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.user.UserUpdateDTO;
import com.shallwecode.backend.user.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @PutMapping
    @Operation(summary = "회원 가입", description = "회원 가입을 시도한다.")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody UserSaveDTO userSaveDTO) {
        userService.saveUser(userSaveDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 닉네임 수정
    @PutMapping("nickname")
    @Operation(summary = "회원 닉네임 수정", description = "회원 닉네임을 수정한다.")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUser(userUpdateDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping
    @Operation(summary = "회원 삭제", description = "회원을 삭제한다.")
    public ResponseEntity<Void> deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    // 회원 조회
    @GetMapping
    @Operation(summary = "회원 목록 조회", description = "회원 목록을 조회한다.")
    public ResponseEntity<List<FindUserDetailDTO>> getAllUser(@RequestParam(defaultValue = "", required = false) String nickname) {
        List<FindUserDetailDTO> userList = userService.findUserDetailsByNickname(nickname);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/list")
    @Operation(summary = "회원 목록 조회", description = "친구 신청을 할 수 있는 회원을 조회한다.")
    public ResponseEntity<List<FindUserListDTO>> findRequestUser() {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        List<FindUserListDTO> userList = userService.findRequestUser(loginUserId);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/profile")
    @Operation(summary = "내 정보 조회", description = "회원을 상세 조회한다.")
    public ResponseEntity<FindUserDetailDTO> findUserDetail() {
        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        FindUserDetailDTO findUserDetailDTO = userService.findUserDetail(loginUserId);
        return ResponseEntity.ok(findUserDetailDTO);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "회원 상세 조회 관리자 기능", description = "관리자가 회원을 상세 조회한다.")
    public ResponseEntity<FindUserDetailDTO> findUserDetail(@PathVariable Long userId) {
        FindUserDetailDTO userDetail = userService.findUserDetail(userId);
        return ResponseEntity.ok(userDetail);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃을 한다.")
    public ResponseEntity<String> logout(HttpServletResponse response) {
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
