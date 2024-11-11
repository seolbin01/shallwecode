package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.common.util.CustomUserUtils;
import com.shallwecode.backend.problem.application.dto.problem.*;
import com.shallwecode.backend.problem.application.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        problemService.saveProblem(newProblemInfo, loginUserId);

        return new ResponseEntity<>("문제 추가 완료", HttpStatus.CREATED);
    }

    /* 문제 수정 */
    @Operation(summary = "문제 수정")
    @PutMapping("/{problemId}")
    public ResponseEntity<String> updateProblem(@PathVariable Long problemId, @RequestBody ProblemReqDTO newProblemInfo) {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        problemService.updateProblem(problemId, newProblemInfo, loginUserId);

        return ResponseEntity.ok().build();

    }

    /* 문제 삭제 */
    @Operation(
            summary = "문제 삭제 기능",
            description = "관리자가 문제를 삭제하는 기능입니다."
    )
    @DeleteMapping("/{problemId}")
    public ResponseEntity<String> deleteProblem(@PathVariable Long problemId) {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        problemService.deleteProblem(problemId, loginUserId);

        return ResponseEntity.noContent().build();
    }

    /* 문제 단일 조회 - 문제 하나당 테스트 케이스가 여러 개 */
    @Operation(
            summary = "문제 단일 조회 기능",
            description = "관리자가 문제를 상세 조회하는 기능입니다."
    )
    @GetMapping("/{problemId}")
    public ResponseEntity<ProblemOneResDTO> selectProblem(@PathVariable Long problemId) {

        /* 데이터 조회 */
        ProblemOneResDTO oneProblem = problemService.selectOneProblem(problemId);
        return ResponseEntity.ok().body(oneProblem);
    }

    @GetMapping("/mylist")
    @Operation(summary = "내 문제 목록 전체 조회", description = "내 문제 목록을 전체 조회 한다.")
    public ResponseEntity<List<FindProblemResDTO>> findAllMyProblem() {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        List<FindProblemResDTO> myProblemList = problemService.findAllMyProblem(loginUserId);

        return new ResponseEntity<>(myProblemList, HttpStatus.OK);
    }

    /* 문제 목록 조회 - 관리자가 문제 관리 클릭시 문제 목록 조회 */
    @Operation(
            summary = "문제 목록 조회(관리자)",
            description = "문제 목록을 조회하는 기능입니다."
    )
    @GetMapping("/admin")
    public ResponseEntity<ProblemListResDTO> selectProblemList(@RequestParam(defaultValue = "1") Integer page,
                                                               @RequestParam(defaultValue = "10") Long size,
                                                               @RequestParam(required = false) String keyword,
                                                               @RequestParam(required = false) Integer option) {

        ProblemListResDTO problemListResDTO = problemService.selectProblemList(page, size, keyword, option);

        return ResponseEntity.ok().body(problemListResDTO);
    }

    @GetMapping({"", "/guest"})
    @Operation(summary = "문제 목록 조회", description = "문제 목록을 조회합니다. 회원/비회원 여부에 따라 해결 유무를 포함하여 반환합니다.")
    public ResponseEntity<List<FindProblemResDTO>> findAllProblem(
            HttpServletRequest request,
            @RequestParam(required = false) Boolean isSolved,
            @RequestParam(required = false) Integer problemLevel
    ) {

        boolean isGuestRequest = request.getRequestURI().endsWith("/guest");
        if (isGuestRequest && (isSolved != null)) {
            throw new CustomException(ErrorCode.GUEST_SOLVED_FILTER_FORBIDDEN);
        }

        ProblemSearchFilter searchFilter = ProblemSearchFilter.builder()
                .userId(isGuestRequest ? null : CustomUserUtils.getCurrentUserSeq())
                .isSolved(isSolved)
                .problemLevel(problemLevel)
                .build();

        List<FindProblemResDTO> problemList = problemService.findAllProblem(searchFilter);

        return new ResponseEntity<>(problemList, HttpStatus.OK);
    }

    @Operation(summary = "미시도 문제 개수 조회", description = "시도하지 않는 문제 개수를 조회합니다.")
    @GetMapping("/mylist/notry")
    public ResponseEntity<Long> findAllMyUnTryProblemCount() {

        Long userId = CustomUserUtils.getCurrentUserSeq();
        Long myNoTryProblemCount = problemService.findAllNoTryProblemCount(userId);

        return new ResponseEntity<>(myNoTryProblemCount, HttpStatus.OK);
    }

    @Operation(summary = "미해결 문제 개수 조회", description = "시도했지만 아직 해결하지 못한 문제 개수를 조회합니다.")
    @GetMapping("/mylist/unsolved")
    public ResponseEntity<Long> findAllMyUnsolvedProblemCount() {

        Long userId = CustomUserUtils.getCurrentUserSeq();
        Long myUnsolvedProblemCount = problemService.findAllUnSolvedProblemCount(userId);

        return new ResponseEntity<>(myUnsolvedProblemCount, HttpStatus.OK);
    }

    @Operation(summary = "해결 문제 개수 조회", description = "해결한 문제 개수를 조회합니다.")
    @GetMapping("/mylist/solved")
    public ResponseEntity<Long> findAllMySolvedProblemCount() {

        Long userId = CustomUserUtils.getCurrentUserSeq();
        Long mySolvedProblemCount = problemService.findAllSolvedProblemCount(userId);

        return new ResponseEntity<>(mySolvedProblemCount, HttpStatus.OK);
    }
}
