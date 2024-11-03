package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.SaveTryReqDTO;
import com.shallwecode.backend.problem.domain.service.TryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TryService {

    private final TryDomainService tryDomainService;

    public void saveTry(Long problemId, Long userId, SaveTryReqDTO saveTryReqDTO) {

        tryDomainService.save(problemId, userId, saveTryReqDTO);
    }
}
