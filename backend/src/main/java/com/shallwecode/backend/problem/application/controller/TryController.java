package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.SaveTryReqDTO;
import com.shallwecode.backend.problem.application.service.TryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        tryService.saveTry(problemId, userId, saveTryReqDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
