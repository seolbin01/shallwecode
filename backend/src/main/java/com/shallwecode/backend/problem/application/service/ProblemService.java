package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.FindMyProblemResDTO;
import com.shallwecode.backend.problem.domain.service.ProblemDomainService;
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
}
