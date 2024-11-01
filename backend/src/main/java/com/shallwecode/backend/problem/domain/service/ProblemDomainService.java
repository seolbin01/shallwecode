package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.ProblemReqDTO;
import com.shallwecode.backend.problem.application.dto.ProblemResDTO;
import com.shallwecode.backend.problem.domain.aggregate.Problem;
import com.shallwecode.backend.problem.domain.repository.ProblemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemDomainService {

    private final ProblemRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public void saveProblem(ProblemReqDTO newProblem) {
        repository.save(modelMapper.map(newProblem, Problem.class));
    }

    @Transactional
    public void updateProblem(Long id, ProblemReqDTO updateProblem) {

        Problem foundProblem = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id에 맞는 문제가 존재하지 않습니다."));
        foundProblem.updateProblemTitle(updateProblem.getTitle());
        foundProblem.updateProblemContent(updateProblem.getContent());
        foundProblem.updateProblemProblemLevel(updateProblem.getProblemLevel());

    }

    @Transactional
    public void deleteProblem(Long problemId) {
        repository.deleteById(problemId);
    }




}
