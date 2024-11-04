package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.FindMyProblemResDTO;
import com.shallwecode.backend.problem.domain.service.ProblemDomainService;
import com.shallwecode.backend.problem.application.dto.ProblemReqDTO;
import com.shallwecode.backend.problem.application.dto.ProblemResDTO;
import com.shallwecode.backend.problem.application.dto.ProblemResListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemDomainService problemDomainService;

    public List<FindMyProblemResDTO> findAllMyProblem(Long userId) {

        return problemDomainService.findAllMyProblem(userId);
    }

    public List<ProblemResDTO> selectOneProblem(Long problemId) {
        return problemDomainService.selectOneProblem(problemId);
    }

    public void deleteProblem(Long problemId) {
        problemDomainService.deleteProblem(problemId);
    }

    public void updateProblem(Long problemId, ProblemReqDTO newProblemInfo) {
        problemDomainService.updateProblem(problemId, newProblemInfo);
    }

    public void saveProblem(ProblemReqDTO newProblemInfo) {
        problemDomainService.saveProblem(newProblemInfo);
    }

    public List<ProblemResListDTO> selectProblemList() {
        return problemDomainService.selectProblemList();
    }
}
