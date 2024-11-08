package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.problem.application.dto.CoopDTO;
import com.shallwecode.backend.problem.application.dto.CoopResDTO;
import com.shallwecode.backend.problem.application.dto.CoopUserResDTO;
import com.shallwecode.backend.problem.domain.service.CodingRoomDomainService;
import com.shallwecode.backend.problem.domain.service.CoopDomainService;
import com.shallwecode.backend.user.domain.service.FriendDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CoopService {

    private final CoopDomainService coopDomainService;
    private final CodingRoomDomainService codingRoomDomainService;
    private final FriendDomainService friendDomainService;

    public void saveCoopFriend(Long codingRoomId, Long userId) {

        Long loginUserId = 3L; // 유저 id

        // 초대하려는 친구가 이미 코딩방에 있으면 안됨. (협업 친구 테이블에 있으면 안됨)
        CoopResDTO findCoop = coopDomainService.findByCoop(codingRoomId, userId);
        if(findCoop != null) {
            throw new CustomException(ErrorCode.DUPLICATED_COOP_USER);
        }

        // 초대할려는 userId가 친구 관계에 있는지 확인해야 함
        // 테스트하기 불편하면 이부분만 주석처리
        boolean isFriendResult = friendDomainService.isExistFriend(loginUserId, userId);
        if(!isFriendResult){ // 친구 관계가 아니라면
            throw new CustomException(ErrorCode.NOT_INVITE_FRIEND);
        }

        CoopDTO coopDTO = new CoopDTO();
        coopDTO.setCodingRoomId(codingRoomId);
        coopDTO.setUserId(userId);
        coopDTO.setHost(false);

        coopDomainService.inviteCoopFriend(coopDTO);
    }

    /* 코딩방에서 나가기 */
    public void deleteCoop(Long codingRoomId) {

        // 유저 id와 코딩방 id를 가지고 coopId 찾기
        Long loginUserId = 3L; // 유저 id

        CoopResDTO findCoop = coopDomainService.findByCoop(codingRoomId, loginUserId);

        coopDomainService.delete(findCoop.getCoopId());

        // 코딩방 호스트일 경우 코딩방 제거
        // 호스트가 아닌 유저가 코딩방에 마지막까지 존재할수는 없음.
        if(findCoop.isHost()){
            codingRoomDomainService.deleteCodingRoom(findCoop.getCodingRoomId());
        }

    }

    /* 코딩방에서 협업 친구 강퇴 */
    public void deleteCoopFriend(Long codingRoomId, Long coopId) {

        Long loginUserId = 3L; // 호스트 유저 아이디

        // 코딩방에 해당 유저가 존재하는지 확인(협업 친구 테이블 조회)
        // coopId 값으로 조회한 결과를 반환
        CoopResDTO userInfo = coopDomainService.findCoopByCoopId(codingRoomId, coopId);
        if (userInfo == null) {
            throw new CustomException(ErrorCode.NOT_GUEST_COOP_USER);
        }


        CoopResDTO hostInfo = coopDomainService.findByCoop(codingRoomId, loginUserId);
        if (hostInfo == null) {
            throw new CustomException(ErrorCode.NOT_HOST_COOP_USER);
        }

        // 호스트 여부 확인
        if (!hostInfo.isHost()) {
            throw new CustomException(ErrorCode.NOT_AUTH_HOST);
        }

        // 2. 자기 자신인지 확인
        if (Objects.equals(hostInfo.getCoopId(), coopId)) {
            throw new CustomException(ErrorCode.NOT_FIRED_SELF);
        }

        // 2 협업 친구 삭제
        coopDomainService.delete(coopId);
    }

    // 협업 친구 구하기
    public List<CoopUserResDTO> selectCoopFriend(Long codingRoomId) {
        return coopDomainService.selectCoopFriend(codingRoomId);
    }
}
