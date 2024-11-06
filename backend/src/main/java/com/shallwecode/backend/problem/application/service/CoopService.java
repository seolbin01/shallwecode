package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.CoopDTO;
import com.shallwecode.backend.problem.application.dto.CoopReqDTO;
import com.shallwecode.backend.problem.application.dto.CoopResDTO;
import com.shallwecode.backend.problem.domain.service.CodingRoomDomainService;
import com.shallwecode.backend.problem.domain.service.CoopDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CoopService {

    private final CoopDomainService coopDomainService;
    private final CodingRoomDomainService codingRoomDomainService;

    public void saveCoopFriend(Long codingRoomId, Long userId, CoopReqDTO coopReqDTO) {

        CoopDTO coopDTO = new CoopDTO();
        coopDTO.setCodingRoomId(codingRoomId);
        coopDTO.setUserId(userId);
        coopDTO.setHost(coopReqDTO.isHost());

        coopDomainService.inviteCoopFriend(coopDTO);
    }

    /* 코딩방에서 나가기 */
    public void deleteCoop(Long codingRoomId, Long userId) {

        CoopResDTO findCoop = coopDomainService.findByCoop(codingRoomId, userId);

        coopDomainService.delete(findCoop.getCoopId());

        // 코딩방 호스트일 경우 코딩방 제거
        // 호스트가 아닌 유저가 코딩방에 마지막까지 존재할수는 없음.
        if(findCoop.isHost()){
            codingRoomDomainService.deleteCodingRoom(findCoop.getCodingRoomId());
        }

    }

    /* 코딩방에서 협업 친구 삭제 */
    public void deleteCoopFriend(Long codingRoomId, Long userId, Long coopId) {

        // 호스트 여부 확인
        CoopResDTO UserInfo = coopDomainService.findByCoop(codingRoomId, userId);


        // 1. 코딩방 ID 검증: 요청한 codingRoomId와 협업 친구의 codingRoomId가 일치하는지 확인
        if (!UserInfo.isHost()) {
            throw new IllegalArgumentException("호스트가 아닙니다.");
        }

        // 2. 자기 자신인지 확인
        if (Objects.equals(UserInfo.getCoopId(), coopId)) {
            throw new IllegalArgumentException("본인을 강퇴할 수 없습니다.");
        }

        // 2 협업 친구 삭제
        coopDomainService.delete(coopId);
    }

}
