package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.ProblemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProblemDomainServiceTest {

    @Autowired
    private ProblemDomainService problemDomainService;

//    @Test
//    void selectOneProblem() {
//        List<ProblemOneResDTO> problemResList = problemDomainService.selectOneProblem(1L);
//
//        for (ProblemOneResDTO problemOneResDTO : problemResList) {
//            System.out.println(problemOneResDTO.toString());
//        }
//    }

    @Test
    void selectProblemList() {
        List<ProblemDTO> problemList = problemDomainService.selectProblemList(null, null, 1L, 10L);

        for(ProblemDTO problemDTO : problemList) {
            System.out.println(problemDTO.toString());
        }
    }
}