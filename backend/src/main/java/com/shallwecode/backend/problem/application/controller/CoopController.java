package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.service.CoopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/codingroom")
@Tag(name = "Coop", description = "협업 친구 관련 API")
@RequiredArgsConstructor
public class CoopController {

    private final CoopService coopService;

    @PostMapping("/{codingroomId}/friend")
    @Operation(summary = "협업 친구 초대", description = "코딩방에 협업 친구를 초대한다.")
    public ResponseEntity<Void> saveCoopFriend(@PathVariable Long codingroomId) {

        // 코딩방에 존재하는 회원만 친구 초대 가능

        // 이미 코딩방에 존재하는 친구는 재초대하면 안됨

        // 친구 초대시 userId 값으로 초대하되, 본인의 친구여야 함



        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{codingroomId}/friend/{coopId}")
    @Operation(summary = "협업 친구 강퇴", description = "코딩방에 있는 협업 친구를 강퇴한다.")
    public ResponseEntity<Void> deleteCoopFriend(@PathVariable Long codingroomId, @PathVariable Long coopId) {

        Long userId = 1L; // 추후 로그인된 회원의 아이디를 가져오도록 수정
        coopService.deleteCoopFriend(userId, codingroomId, coopId);

        return ResponseEntity.noContent().build();
    }


}
