package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.user.application.dto.SaveFriendReqDTO;
import com.shallwecode.backend.user.application.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
@Tag(name = "Friend", description = "친구")
public class FriendController {

    private final FriendService friendService;

    @Operation(summary = "친구 신청", description = "친구를 신청한다")
    @PostMapping("/request")
    public ResponseEntity<Void> saveFriend(@RequestBody SaveFriendReqDTO saveFriendReqDTO) {
        friendService.saveFriend(saveFriendReqDTO);
        return ResponseEntity.ok().build();
    }



}
