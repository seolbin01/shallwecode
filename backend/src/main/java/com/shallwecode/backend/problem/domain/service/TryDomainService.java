package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.SaveTryReqDTO;
import com.shallwecode.backend.problem.domain.aggregate.Try;
import com.shallwecode.backend.problem.domain.repository.TryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TryDomainService {

    private final ModelMapper modelMapper;
    private final TryRepository tryRepository;

    public void save(Long problemId, Long userId, SaveTryReqDTO saveTryReqDTO) {

        Try newTry = modelMapper.map(saveTryReqDTO, Try.class);
        newTry.updateUser(userId);
        newTry.updateProblem(problemId);

        tryRepository.save(newTry);
    }
}
