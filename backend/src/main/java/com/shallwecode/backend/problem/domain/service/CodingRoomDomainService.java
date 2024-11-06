package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.CodingRoomReqDTO;
import com.shallwecode.backend.problem.domain.aggregate.CodingRoom;
import com.shallwecode.backend.problem.domain.repository.CodingRoomRepository;
import com.shallwecode.backend.problem.domain.repository.CoopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodingRoomDomainService {

    private final CodingRoomRepository repository;
    private final CoopRepository coopRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long saveCodingRoom(CodingRoomReqDTO newCodingRoom) {

        CodingRoom codingRoom = modelMapper.map(newCodingRoom, CodingRoom.class);
        repository.save(codingRoom);

        return codingRoom.getCodingRoomId();

    }

    // 코딩방 수정 기능은 제공하지 않음.

    @Transactional
    public void deleteCodingRoom(Long codingRoomId) {

        // 코딩방에 초대된 협업 친구 목록 전부 삭제 후 코딩방 제거
        coopRepository.deleteByCodingRoomId(codingRoomId);
        repository.deleteById(codingRoomId);

    }

}
