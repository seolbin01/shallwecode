package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.*;
import com.shallwecode.backend.problem.application.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problem")
@Slf4j
@Tag(name = "Problem", description = "문제 관련 API")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

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
            description = "관리자가 문제를 상세 조회하는 기능입니다."
    )
    @GetMapping("/{problemId}")
    public ResponseEntity<List<ProblemOneResDTO>> selectProblem(@PathVariable Long problemId) {

        /* 데이터 조회 */
        List<ProblemOneResDTO> oneProblem = problemService.selectOneProblem(problemId);

        return ResponseEntity.ok().body(oneProblem);
    }

    @GetMapping("/mylist")
    @Operation(summary = "내 문제 목록 전체 조회", description = "내 문제 목록을 전체 조회 한다.")
    public ResponseEntity<List<FindMyProblemResDTO>> findAllMyProblem() {

        Long userId = 1L;   // 추후 로그인된 회원의 userId를 가져오도록 수정
        List<FindMyProblemResDTO> myProblemList = problemService.findAllMyProblem(userId);

        return new ResponseEntity<>(myProblemList, HttpStatus.OK);
    }

    /* 문제 목록 조회 - 관리자가 문제 관리 클릭시 문제 목록 조회 */
    @Operation(
            summary = "문제 목록 조회 기능",
            description = "문제 목록을 조회하는 기능입니다."
    )
    @GetMapping("/adminList")
    public ResponseEntity<ProblemListResDTO> selectProblemList(@RequestParam(defaultValue = "1") Integer page,
                                                               @RequestParam(defaultValue = "10") Long size,
                                                               @RequestParam(required = false) String keyword,
                                                               @RequestParam(required = false) Integer option) {

        ProblemListResDTO problemListResDTO = problemService.selectProblemList(page, size, keyword, option);

        return ResponseEntity.ok().body(problemListResDTO);
    }

    @GetMapping("/list")
    @Operation(summary = "문제 전체 조회", description = "")
    public ResponseEntity<List<FindProblemResDTO>> findAllProblem() {

        List<FindProblemResDTO> problemList = problemService.findAllProblem();

        return new ResponseEntity<>(problemList, HttpStatus.OK);
    }
  
    @Operation(summary = "미시도 문제 개수 조회", description = "시도하지 않는 문제 개수를 조회합니다.")
    @GetMapping("/mylist/notry")
    public ResponseEntity<Long> findAllMyUnTryProblemCount() {

        Long userId = 1L; // 추후 로그인된 회원의 userId를 가져오도록 수정
        Long myNoTryProblemCount = problemService.findAllNoTryProblemCount(userId);

        return new ResponseEntity<>(myNoTryProblemCount, HttpStatus.OK);
    }

    @Operation(summary = "미해결 문제 개수 조회", description = "시도했지만 아직 해결하지 못한 문제 개수를 조회합니다.")
    @GetMapping("/mylist/unsolved")
    public ResponseEntity<Long> findAllMyUnsolvedProblemCount() {

        Long userId = 1L; // 추후 로그인된 회원의 userId를 가져오도록 수정
        Long myUnsolvedProblemCount = problemService.findAllUnSolvedProblemCount(userId);

        return new ResponseEntity<>(myUnsolvedProblemCount, HttpStatus.OK);
    }

    @Operation(summary = "해결 문제 개수 조회", description = "해결한 문제 개수를 조회합니다.")
    @GetMapping("/mylist/solved")
    public ResponseEntity<Long> findAllMySolvedProblemCount() {

        long userId = 1L;
        Long mySolvedProblemCount = problemService.findAllSolvedProblemCount(userId);

        return new ResponseEntity<>(mySolvedProblemCount, HttpStatus.OK);
    }
}
