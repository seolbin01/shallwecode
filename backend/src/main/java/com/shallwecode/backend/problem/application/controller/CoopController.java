package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.common.util.CustomUserUtils;
import com.shallwecode.backend.problem.application.dto.coop.CoopUserResDTO;
import com.shallwecode.backend.problem.application.dto.coop.CoopUserResListDTO;
import com.shallwecode.backend.problem.application.service.CoopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/codingroom")
@Tag(name = "Coop", description = "협업 친구 관련 API")
@RequiredArgsConstructor
public class CoopController {

    private final CoopService coopService;

    @PostMapping("/{codingroomId}/friend/{userId}")
    @Operation(summary = "협업 친구 초대", description = "코딩방에 협업 친구를 초대한다.")
    public ResponseEntity<Void> saveCoopFriend(@PathVariable Long codingroomId, @PathVariable Long userId) {

        // 코딩방 Id 값과 초대할 유저 Id 값을 받아온다.

        coopService.saveCoopFriend(codingroomId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{codingroomId}/friend")
    @Operation(summary = "코딩방 나가기", description = "코딩방에서 나간다.")
    public ResponseEntity<Void> deleteCoop(@PathVariable Long codingroomId) {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();

        coopService.deleteCoop(codingroomId, loginUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{codingroomId}/friend/{coopId}")
    @Operation(summary = "협업 친구 강퇴하기", description = "코딩방에 있는 협업 친구를 강퇴한다.")
    public ResponseEntity<Void> deleteCoopFriend(@PathVariable Long codingroomId, @PathVariable Long coopId) {


        coopService.deleteCoopFriend(codingroomId, coopId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "협업 친구 조회하기", description = "코딩방에 있는 협업 친구를 조회한다.")
    @GetMapping("/friendList/{codingRoomId}")
    public ResponseEntity<CoopUserResListDTO> selectCoopFriend(@PathVariable Long codingRoomId) {
        List<CoopUserResDTO> coopUserList = coopService.selectCoopFriend(codingRoomId);
        return ResponseEntity.ok().body(new CoopUserResListDTO("조회 성공", coopUserList));
    }
}
