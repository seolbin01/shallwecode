package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.CodingRoomReqDTO;
import com.shallwecode.backend.problem.application.dto.FindMyCodingRoomResDTO;
import com.shallwecode.backend.problem.application.service.CodingRoomService;
import com.shallwecode.backend.problem.domain.service.CodingRoomDomainService;
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
            description = "새로운 코딩방을 생성합니다."
    )
    @PostMapping
    public ResponseEntity<String> saveCodingRoom(@RequestBody CodingRoomReqDTO newCodingRoomInfo) {

        codingRoomService.saveCodingRoom(newCodingRoomInfo);
        return new ResponseEntity<>("코딩방 생성 완료", HttpStatus.CREATED);
    }

    /* 코딩방 삭제 */
    @Operation(
            summary = "코딩방 삭제",
            description = "호스트가 코딩방을 삭제하는 기능입니다."
    )
    @DeleteMapping("/{codingroomId}")
    public ResponseEntity<String> deleteCodingRoom(@PathVariable Long codingroomId) {

        // 호스트인지 여부를 확인해야 함

        codingRoomService.deleteCodingRoom(codingroomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    @Operation(summary = "내 코딩방 조회", description = "내 코딩방을 조회 한다.")
    public ResponseEntity<List<FindMyCodingRoomResDTO>> findMyCodingRoom() {

        Long userId = 1L;   // 추후 로그인 회원의 아이디로 수정
        List<FindMyCodingRoomResDTO> codingRoomList = codingRoomService.findMyCodingRoom(userId);

        return new ResponseEntity<>(codingRoomList, HttpStatus.OK);
    }
}
