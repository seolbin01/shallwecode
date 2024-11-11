package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.problem.application.dto.problem.ProblemListResDTO;
import com.shallwecode.backend.problem.application.dto.problem.ProblemReqDTO;
import com.shallwecode.backend.problem.application.dto.problem.TestcaseDTO;
import com.shallwecode.backend.problem.domain.aggregate.Problem;
import com.shallwecode.backend.problem.domain.aggregate.Testcase;
import com.shallwecode.backend.problem.domain.repository.ProblemRepository;
import com.shallwecode.backend.problem.domain.repository.TestcaseRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProblemServiceTest {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private TestcaseRepository testcaseRepository;

//    @Test
//    @DisplayName("한 문제 상세 조회 테스트")
//    void selectOneProblemTest() {
//        List<ProblemOneResDTO> problemResList = problemService.selectOneProblem(1L);
//
//        for (ProblemOneResDTO problemOneResDTO : problemResList) {
//            System.out.println(problemOneResDTO.toString());
//        }
//    }

    @Test
    @DisplayName("문제 목록 조회 테스트")
    void selectProblemListTest() {
        ProblemListResDTO problemList = problemService.selectProblemList(1, 10L, "문제", null);
        System.out.println(problemList.getCurrentPage());
        System.out.println(problemList.getTotalItems());
        System.out.println(problemList.getTotalPages());
        for(int i = 0; i < problemList.getProblemList().size(); i++) {
            System.out.println(problemList.getProblemList().get(i));
        }
    }


    @Test
    @DisplayName("문제 등록 테스트")
    void saveProblem() {
        // given
        // 1. 문제 정보 및 테스트 케이스 준비
        ProblemReqDTO newProblemInfo = new ProblemReqDTO(
                "문제 제목",
                "문제 내용",
                2,
                List.of(
                        new TestcaseDTO("입력1", "출력1"),
                        new TestcaseDTO("입력2", "출력2")
                )
        );

        // 2. 관리자 권한을 가진 사용자 준비 (여기서는 adminId를 예시로 사용)
        Long adminId = 34L;  // 관리자 ID 설정

        // when
        problemService.saveProblem(newProblemInfo, adminId);

        // then
        // 1. 문제 등록이 잘 되었는지 확인
        List<Problem> problems = problemRepository.findAll();
        Problem savedProblem = problems.get(problems.size() - 1);

        assertNotNull(savedProblem);
        assertEquals("문제 제목", savedProblem.getTitle());
        assertEquals("문제 내용", savedProblem.getContent());
        assertEquals(2, savedProblem.getProblemLevel());

        // 2. 테스트 케이스가 잘 저장되었는지 확인
        List<Testcase> savedTestcases = testcaseRepository.findAll();

        Testcase savedTestcase1 = savedTestcases.get(savedTestcases.size() - 2);
        assertEquals("입력1", savedTestcase1.getInput());
        assertEquals("출력1", savedTestcase1.getOutput());
        assertEquals(savedProblem.getProblemId(), savedTestcase1.getProblemId());

        Testcase savedTestcase2 = savedTestcases.get(savedTestcases.size() - 1);
        assertEquals("입력2", savedTestcase2.getInput());
        assertEquals("출력2", savedTestcase2.getOutput());
        assertEquals(savedProblem.getProblemId(), savedTestcase2.getProblemId());
    }


    @Test
    @DisplayName("문제 수정 테스트")
    void updateProblem() {

        // given
        // 1. 문제 정보 및 테스트 케이스 준비
        ProblemReqDTO newProblemInfo = new ProblemReqDTO(
                "문제 수정",
                "수정된 내용",
                2,
                List.of(
                        new TestcaseDTO("입력A", "출력A"),
                        new TestcaseDTO("입력B", "출력B")
                )
        );

        // 2. 관리자 권한을 가진 사용자 준비 (여기서는 adminId를 예시로 사용)
        Long adminId = 34L;  // 관리자 ID 설정
        Long problemId = 18L; // 문제 ID 설정

        // when
        problemService.updateProblem(problemId, newProblemInfo, adminId);

        // then
        // 1. 문제 수정이 잘 되었는지 확인
        Problem updatedProblem = problemRepository.findById(problemId).get();
        assertNotNull(updatedProblem);

        // 2. 테스트 케이스가 잘 수정되었는지 확인
        List<Testcase> updatedTestcases = testcaseRepository.findAll();

        Testcase updatedTestcase1 = updatedTestcases.get(updatedTestcases.size() - 2);
        assertEquals("입력A", updatedTestcase1.getInput());
        assertEquals("출력A", updatedTestcase1.getOutput());
        assertEquals(updatedProblem.getProblemId(), updatedTestcase1.getProblemId());

        Testcase updatedTestcase2 = updatedTestcases.get(updatedTestcases.size() - 1);
        assertEquals("입력B", updatedTestcase2.getInput());
        assertEquals("출력B", updatedTestcase2.getOutput());
        assertEquals(updatedProblem.getProblemId(), updatedTestcase2.getProblemId());

    }


    @Test
    @DisplayName("관리자 문제 삭제 테스트")
    void testDeleteProblemByAdmin() {

        Long adminId = 34L;  // 관리자 ID 설정
        Long problemId = 18L; // 문제 ID 설정

        // 관리자가 문제를 삭제
        problemService.deleteProblem(problemId, adminId);

        // 문제와 연관된 테스트 케이스가 모두 삭제되었는지 확인
        assertThat(problemRepository.findById(problemId)).isEmpty();
        assertThat(testcaseRepository.findByProblemId(problemId)).isEmpty();

    }

    @Test
    @DisplayName("유저 문제 삭제 테스트")
    public void testDeleteProblemByUser() {

        Long userId = 75L;  // 유저 ID 설정
        Long problemId = 18L; // 문제 ID 설정

        // 관리자가 아닌 사용자가 문제를 삭제하려고 할 때 예외 발생을 확인
        assertThrows(CustomException.class, () -> problemService.deleteProblem(problemId, userId));
    }




}