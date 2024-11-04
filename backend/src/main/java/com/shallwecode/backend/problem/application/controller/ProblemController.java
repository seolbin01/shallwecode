package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.ProblemReqDTO;
import com.shallwecode.backend.problem.application.dto.ProblemResDTO;
import com.shallwecode.backend.problem.application.dto.ProblemResListDTO;
import com.shallwecode.backend.problem.application.service.ProblemService;
import com.shallwecode.backend.problem.domain.service.ProblemDomainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/problem")
@Slf4j
@Tag(name = "Problem", description = "문제 관련 API")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;
    private final ProblemDomainService problemDomainService;

    /* 문제 등록 */
    @Operation(
            summary = "문제 등록",
            description = "새로운 문제를 등록합니다."
    )
    @PostMapping
    public ResponseEntity<String> saveProblem(@RequestBody ProblemReqDTO newProblemInfo) {

        problemService.saveProblem(newProblemInfo);

        return new ResponseEntity<>("문제 추가 완료", HttpStatus.CREATED);
    }

    /* 문제 수정 */
    @Operation(summary = "문제 수정")
    @PutMapping("/{problemId}")
    public ResponseEntity<String> updateProblem(@PathVariable Long problemId, @RequestBody ProblemReqDTO newProblemInfo) {

        problemService.updateProblem(problemId, newProblemInfo);

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
        problemService.deleteProblem(problemId);

        return ResponseEntity.noContent().build();
    }

    /* 문제 단일 조회 - 문제 하나당 테스트 케이스가 여러 개 */
    @Operation(
            summary = "문제 단일 조회 기능",
            description = "관리자가 문제를 상제 조회하는 기능입니다."
    )
    @GetMapping("/{problemId}")
    public ResponseEntity<List<ProblemResDTO>> selectProblem(@PathVariable Long problemId) {

        /* 데이터 조회 */
        List<ProblemResDTO> oneProblem = problemService.selectOneProblem(problemId);

        return ResponseEntity.ok().body(oneProblem);
    }

    /* 문제 목록 조회 - 관리자가 문제 관리 클릭시 문제 목록 조회 */
    @GetMapping("/list")
    public ResponseEntity<List<ProblemResListDTO>> selectProblemList() {
        /* 데이터 조회 */
        List<ProblemResListDTO> problemList = problemService.selectProblemList();

        return ResponseEntity.ok().body(problemList);
    }
}
