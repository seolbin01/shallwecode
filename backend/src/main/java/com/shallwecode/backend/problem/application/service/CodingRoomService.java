package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.FindMyCodingRoomResDTO;
import com.shallwecode.backend.problem.domain.service.CodingRoomDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodingRoomService {

    private final CodingRoomDomainService codingRoomDomainService;

    public List<FindMyCodingRoomResDTO> findMyCodingRoom(Long userId) {

        return codingRoomDomainService.findMyCodingRoom(userId);
    }
}
