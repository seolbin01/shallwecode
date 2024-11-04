package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.ProblemReqDTO;
import com.shallwecode.backend.problem.domain.service.ProblemDomainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/problem")
@Slf4j
@Tag(name = "Problem", description = "문제 관련 API")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemDomainService problemDomainService;

    /* 문제 등록 */
    @Operation(
            summary = "문제 등록",
            description = "새로운 문제를 등록합니다."
    )
    @PostMapping
    public ResponseEntity<String> saveProblem(@RequestBody ProblemReqDTO newProblemInfo) {

        problemDomainService.saveProblem(newProblemInfo);
        return new ResponseEntity<>("문제 추가 완료", HttpStatus.CREATED);
    }

    /* 문제 수정 */
    @Operation(summary = "문제 수정")
    @PutMapping("/{problemId}")
    public ResponseEntity<String> updateProblem(@PathVariable Long problemId, @RequestBody ProblemReqDTO newProblemInfo) {

        problemDomainService.updateProblem(problemId, newProblemInfo);

        return ResponseEntity.ok().build();

    }

    /* 문제 삭제 */
    @Operation(
            summary = "문제 삭제 기능",
            description = "관리자가 문제를 삭제하는 기능입니다."
    )
    @DeleteMapping("/{problemId}")
    public ResponseEntity<String> deleteProblem(@PathVariable Long problemId) {

        // 해당 글이 존재하는지 확인해야 함

        // 관리자 여부를 확인해야 함

        problemDomainService.deleteProblem(problemId);

        return ResponseEntity.noContent().build();
    }




}
