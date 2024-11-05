package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.CoopReqDTO;
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
    public ResponseEntity<Void> saveCoopFriend(@PathVariable Long codingroomId, @RequestBody CoopReqDTO coopReqDTO) {

        Long userId = 1L; // 초대할 친구 id
        coopService.saveCoopFriend(codingroomId, userId, coopReqDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{codingroomId}")
    @Operation(summary = "코딩방 나가기", description = "코딩방에서 나간다.")
    public ResponseEntity<Void> deleteCoop(@PathVariable Long codingroomId) {

        // 유저 id와 코딩방 id를 가지고 coopId 찾기
        Long userId = 4L; // 유저 id
        coopService.deleteCoop(codingroomId, userId);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("{codingroomId}/friend/{coopId}")
    @Operation(summary = "협업 친구 강퇴하기", description = "코딩방에 있는 협업 친구를 강퇴한다.")
    public ResponseEntity<Void> deleteCoopFriend(@PathVariable Long codingroomId, @PathVariable Long coopId) {

        Long userId = 3L; // 호스트 유저 아이디

        coopService.deleteCoopFriend(codingroomId, userId ,coopId);

        return ResponseEntity.noContent().build();
    }


}
