package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.*;
import com.shallwecode.backend.problem.domain.service.ProblemDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemDomainService problemDomainService;

    public List<FindProblemResDTO> findAllMyProblem(Long userId) {

        return problemDomainService.findAllMyProblem(userId);
    }

    public List<ProblemOneResDTO> selectOneProblem(Long problemId) {
        return problemDomainService.selectOneProblem(problemId);
    }

    public Long findAllNoTryProblemCount(Long userId) {
        return problemDomainService.findAllNoTryProblemCount(userId);
    }

    public Long findAllUnSolvedProblemCount(Long userId) {
            return problemDomainService.findAllUnSolvedProblemCount(userId);
    }

    public Long findAllSolvedProblemCount(long userId) {
        return problemDomainService.findAllSolvedProblemCount(userId);
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

    public ProblemListResDTO selectProblemList(Integer page, Long size, String keyword, Integer option) {
        Long offset = (page - 1) * size;

        System.out.println(offset);

        List<ProblemDTO> problemDTO = problemDomainService.selectProblemList(keyword, option, offset, size);
        Long problemCount = problemDomainService.selectProblemCount();

        return ProblemListResDTO.builder()
                .problemList(problemDTO)
                .currentPage(page)
                .totalPages((int) Math.ceil((double) problemCount / size))
                .totalItems(problemCount)
                .build();
    }

    public List<FindProblemResDTO> findAllProblem() {

        return problemDomainService.findAllProblem();
    }

    public List<FindProblemResDTO> findAllProblemByUser(Long loginUserId) {

        return problemDomainService.findAllProblemByUser(loginUserId);
    }
}
