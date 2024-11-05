package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.CoopReqDTO;
import com.shallwecode.backend.problem.application.dto.CoopResDTO;
import com.shallwecode.backend.problem.domain.service.CoopDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CoopService {

    private final CoopDomainService coopDomainService;

    public void saveCoopFriend(Long userId, Long coopId, CoopReqDTO coopReqDTO) {

        coopDomainService.inviteCoopFriend(userId, coopId, coopReqDTO);
    }

    /* 코딩방에서 협업 친구 삭제 */
    public void deleteCoopFriend(Long userId, Long codingRoomId, Long coopId) {

        // 호스트 여부 확인
        // userId에 해당하는 사람의 권한이 관리자인지 확인해야 한다.
        // userId가 협업 친구인가 아니면 본인인가?


        // coopId를 통해 협업 친구 정보 조회
        CoopResDTO findCoop = coopDomainService.findByCoopId(coopId);



        // 1. 사용자 ID 검증: 요청한 userId와 협업 친구의 userId가 일치하는지    확인
        if (!Objects.equals(findCoop.getUserId(), userId)) {
            throw new IllegalArgumentException("사용자 ID가 일치하지 않습니다.");
        }

        // 2. 코딩방 ID 검증: 요청한 codingRoomId와 협업 친구의 codingRoomId가 일치하는지 확인
        if (!Objects.equals(findCoop.getCodingRoomId(), codingRoomId)) {
            throw new IllegalArgumentException("코딩방 ID가 일치하지 않습니다.");
        }

        // 3. 협업 친구 삭제
        coopDomainService.delete(coopId);
    }

}
