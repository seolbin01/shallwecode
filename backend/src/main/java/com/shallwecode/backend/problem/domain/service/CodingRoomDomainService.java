package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.CodingRoomReqDTO;
import com.shallwecode.backend.problem.domain.aggregate.CodingRoom;
import com.shallwecode.backend.problem.domain.repository.CodingRoomRepository;
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
    private final ModelMapper modelMapper;

    @Transactional
    public void saveCodingRoom(CodingRoomReqDTO newCodingRoom) {

        CodingRoom codingRoom = modelMapper.map(newCodingRoom, CodingRoom.class);
        repository.save(codingRoom);

        // 추가적으로 설정이 필요한지 DB 관계 고려해보기
    }

    // 코딩방 수정 기능은 제공하지 않음.

    @Transactional
    public void deleteCodingRoom(Long codingRoomId) {

        repository.deleteById(codingRoomId);

    }




}
