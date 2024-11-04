package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.FindMyTryResDTO;
import com.shallwecode.backend.problem.application.dto.FindTryResDTO;
import com.shallwecode.backend.problem.application.dto.SaveTryReqDTO;
import com.shallwecode.backend.problem.application.service.TryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problem")
@Tag(name = "Problem", description = "문제 관련 API")
@RequiredArgsConstructor
public class TryController {

    private final TryService tryService;

    @PostMapping("/{problemId}/try")
    @Operation(summary = "풀이 시도 등록", description = "풀이 시도를 등록한다.")
    public ResponseEntity<Void> saveTry(@PathVariable Long problemId,
                                        @RequestBody SaveTryReqDTO saveTryReqDTO) {

        Long userId = 1L;   // 추후 로그인된 회원의 userId를 가져오도록 수정
        tryService.saveTry(userId, problemId, saveTryReqDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{problemId}/{tryId}")
    @Operation(summary = "풀이 시도 삭제", description = "풀이 시도를 삭제한다.")
    public ResponseEntity<String> deleteTry(@PathVariable Long problemId,
                                            @PathVariable Long tryId) {

        Long userId = 1L;   // 추후 로그인된 회원의 userId를 가져오도록 수정
        tryService.deleteTry(userId, problemId, tryId);

        return ResponseEntity.ok().body("풀이 시도 삭제 완료");
    }

    @GetMapping("/{problemId}/try")
    @Operation(summary = "내 풀이 시도 조회", description = "내 풀이 시도를 조회 한다.")
    public ResponseEntity<List<FindMyTryResDTO>> findAllMyTry(@PathVariable Long problemId) {

        Long userId = 1L;   // 추후 로그인된 회원의 userId를 가져오도록 수정
        List<FindMyTryResDTO> myTryList = tryService.findAllMyTry(userId, problemId);

        return new ResponseEntity<>(myTryList, HttpStatus.OK);
    }

    @GetMapping("/{tryId}")
    @Operation(summary = "특정 풀이 시도 조회", description = "특정 풀이 시도를 조회 한다.")
    public ResponseEntity<FindTryResDTO> findTry(@PathVariable Long tryId) {

        Long userId = 1L;   // 추후 로그인된 회원의 userId를 가져오도록 수정
        FindTryResDTO tryResDTO = tryService.findTry(tryId);

        return new ResponseEntity<>(tryResDTO, HttpStatus.OK);
    }
}
