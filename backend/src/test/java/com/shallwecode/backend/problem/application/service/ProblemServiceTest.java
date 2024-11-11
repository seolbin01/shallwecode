package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.ProblemListResDTO;
import com.shallwecode.backend.problem.application.dto.ProblemOneResDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProblemServiceTest {

    @Autowired
    private ProblemService problemService;

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
}