package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.user.application.dto.FriendResListDTO;
import com.shallwecode.backend.user.application.dto.SaveFriendReqDTO;
import com.shallwecode.backend.user.application.dto.UpdateFriendReqDTO;
import com.shallwecode.backend.user.application.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
@Tag(name = "Friend", description = "친구")
public class FriendController {

    private final FriendService friendService;

    @Operation(summary = "친구 신청", description = "친구를 신청한다")
    @PostMapping("/request")
    public ResponseEntity<Void> saveFriend(@Valid @RequestBody SaveFriendReqDTO saveFriendReqDTO) {
        friendService.saveFriend(saveFriendReqDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "친구 신청 수락", description = "친구 신청을 수락한다.")
    @PostMapping("/request/accept")
    public ResponseEntity<Void> acceptFriend(@Valid @RequestBody UpdateFriendReqDTO updateFriendReqDTO) {
        friendService.updateFriend(updateFriendReqDTO, true);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "친구 신청 거절", description = "친구 신청을 거절한다.")
    @PostMapping("/request/reject")
    public ResponseEntity<Void> rejectFriend(@Valid @RequestBody UpdateFriendReqDTO updateFriendReqDTO) {
        friendService.updateFriend(updateFriendReqDTO, false);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "친구 목록 조회", description = "친구 목록을 조회한다.")
    @GetMapping
    public ResponseEntity<List<FriendResListDTO>> findAllFriend() {

        Long loginUserId = 1L;
        List<FriendResListDTO> friendList = friendService.findAllFriend(loginUserId);
        return ResponseEntity.ok(friendList);
    }

    @Operation(summary = "친구 신청 목록 조회", description = "친구 신청이 온 목록을 조회한다.")
    @GetMapping("/request")
    public ResponseEntity<List<FriendResListDTO>> findAllFriendReq() {

        Long loginUserId = 1L;
        List<FriendResListDTO> friendReqList = friendService.findAllFriendReq(loginUserId);
        return ResponseEntity.ok(friendReqList);
    }

    @Operation(summary = "친구 삭제", description = "친구를 삭제한다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(@RequestParam Long userId) {

        friendService.deleteFriend(userId);
        return ResponseEntity.ok().build();
    }
}
