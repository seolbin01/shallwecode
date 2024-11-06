package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.CodingRoomReqDTO;
import com.shallwecode.backend.problem.application.dto.CoopDTO;
import com.shallwecode.backend.problem.domain.service.CodingRoomDomainService;
import com.shallwecode.backend.problem.domain.service.CoopDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodingRoomService {

    private final CodingRoomDomainService codingRoomDomainService;
    private final CoopDomainService coopDomainService;

    public void saveCodingRoom(CodingRoomReqDTO codingRoomReqDTO) {

        // 해당 번호의 문제가 있는지 확인해야 하지만 프론트에서 문제를 조회해서
        // 확인 후 코딩방 생성 예정이므로 패스

        Long codingRoomId = codingRoomDomainService.saveCodingRoom(codingRoomReqDTO);

        // 연관된 협업 친구 저장
        Long loginUserId = 3L; // 로그인 유저 가져오기

        CoopDTO coopDTO = new CoopDTO();
        coopDTO.setCodingRoomId(codingRoomId);
        coopDTO.setUserId(loginUserId);
        coopDTO.setHost(true);

        coopDomainService.inviteCoopFriend(coopDTO);

    }

    public void deleteCodingRoom(Long codingRoomId) {

        codingRoomDomainService.deleteCodingRoom(codingRoomId);

    }
}
