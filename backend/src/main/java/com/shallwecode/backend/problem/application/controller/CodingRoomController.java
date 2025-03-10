package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.common.util.CustomUserUtils;
import com.shallwecode.backend.problem.application.dto.codingRoom.FindMyCodingRoomResDTO;
import com.shallwecode.backend.problem.application.service.CodingRoomService;
import com.shallwecode.backend.problem.domain.aggregate.CodingRoom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/codingroom")
@Slf4j
@Tag(name = "CodingRoom", description = "코딩방 관련 API")
@RequiredArgsConstructor
public class CodingRoomController {

    private final CodingRoomService codingRoomService;

    /* 코딩방 등록 */
    @Operation(
            summary = "코딩방 등록",
            description = "새로운 코딩방을 생성합니다. 생성된 코딩방의 번호를 반환합니다."
    )
    @PostMapping("/{problemId}")
    public ResponseEntity<CodingRoom> saveCodingRoom(@PathVariable Long problemId) {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        CodingRoom codingRoom = codingRoomService.saveCodingRoom(problemId, loginUserId);
        return ResponseEntity.ok().body(codingRoom);
    }

    /* 코딩방 삭제 */
    @Operation(
            summary = "코딩방 삭제",
            description = "호스트가 코딩방을 삭제하는 기능입니다."
    )
    @DeleteMapping("/{codingroomId}")
    public ResponseEntity<String> deleteCodingRoom(@PathVariable Long codingroomId) {


        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        codingRoomService.deleteCodingRoom(codingroomId, loginUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    @Operation(summary = "내 코딩방 조회", description = "내 코딩방을 조회 한다.")
    public ResponseEntity<List<FindMyCodingRoomResDTO>> findMyCodingRoom() {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        List<FindMyCodingRoomResDTO> codingRoomList = codingRoomService.findMyCodingRoom(loginUserId);

        return new ResponseEntity<>(codingRoomList, HttpStatus.OK);
    }
}
