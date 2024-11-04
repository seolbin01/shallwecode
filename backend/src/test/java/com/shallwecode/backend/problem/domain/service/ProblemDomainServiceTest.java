package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.ProblemResDTO;
import com.shallwecode.backend.problem.application.dto.ProblemResListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProblemDomainServiceTest {

    @Autowired
    private ProblemDomainService problemDomainService;

    @Test
    void selectOneProblem() {
        List<ProblemResDTO> problemResList = problemDomainService.selectOneProblem(1L);

        for(int i = 0; i < problemResList.size(); i++) {
            System.out.println(problemResList.get(i).toString());
        }
    }

    @Test
    void selectProblemList() {
        List<ProblemResListDTO> problemList = problemDomainService.selectProblemList();

        for(ProblemResListDTO problemResListDTO : problemList) {
            System.out.println(problemResListDTO.toString());
        }
    }
}