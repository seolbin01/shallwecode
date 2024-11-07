package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.problem.application.dto.CoopResDTO;
import com.shallwecode.backend.problem.application.dto.FindMyCodingRoomResDTO;
import com.shallwecode.backend.problem.domain.service.CodingRoomDomainService;
import com.shallwecode.backend.problem.domain.service.CoopDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.shallwecode.backend.problem.application.dto.CodingRoomReqDTO;
import com.shallwecode.backend.problem.application.dto.CoopDTO;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodingRoomService {

    private final CodingRoomDomainService codingRoomDomainService;
    private final CoopDomainService coopDomainService;

    public List<FindMyCodingRoomResDTO> findMyCodingRoom(Long userId) {

        return codingRoomDomainService.findMyCodingRoom(userId);
    }

    public void saveCodingRoom(CodingRoomReqDTO codingRoomReqDTO) {

        // 해당 번호의 문제가 있는지 확인해야 하지만 프론트에서 문제를 조회해서
        // 확인 후 코딩방 생성 예정이므로 패스.

        Long codingRoomId = codingRoomDomainService.saveCodingRoom(codingRoomReqDTO);

        // 호스트 정보 협업 친구 테이블에 저장하기
        Long loginUserId = 3L; // 로그인 유저 가져오기

        CoopDTO coopDTO = new CoopDTO();
        coopDTO.setCodingRoomId(codingRoomId);
        coopDTO.setUserId(loginUserId);
        coopDTO.setHost(true);

        coopDomainService.inviteCoopFriend(coopDTO);

    }

    public void deleteCodingRoom(Long codingRoomId) {

        // 호스트인지 여부를 확인해야 함
        Long loginUserId = 3L;
        CoopResDTO UserInfo = coopDomainService.findByCoop(codingRoomId, loginUserId);

        if (!UserInfo.isHost()) {
            throw new CustomException(ErrorCode.NOT_HOST);
        }

        codingRoomDomainService.deleteCodingRoom(codingRoomId);

    }
}
