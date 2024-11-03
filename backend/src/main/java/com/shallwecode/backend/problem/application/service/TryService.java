package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.SaveTryReqDTO;
import com.shallwecode.backend.problem.domain.aggregate.Try;
import com.shallwecode.backend.problem.domain.service.TryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TryService {

    private final TryDomainService tryDomainService;

    public void saveTry(Long userId, Long problemId, SaveTryReqDTO saveTryReqDTO) {

        tryDomainService.save(userId, problemId, saveTryReqDTO);
    }

    public void deleteTry(Long userId, Long problemId, Long tryId) {

        Try curTry = tryDomainService.findById(tryId);

        if(!Objects.equals(curTry.getUserId(), userId)) {
            throw new IllegalArgumentException();
        }

        if(!Objects.equals(curTry.getProblemId(), problemId)) {
            throw new IllegalArgumentException();
        }

        tryDomainService.delete(tryId);
    }
}
