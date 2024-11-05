package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.user.application.dto.*;
import com.shallwecode.backend.user.domain.aggregate.FriendStatus;
import com.shallwecode.backend.user.domain.aggregate.NotiType;
import com.shallwecode.backend.user.domain.repository.FriendRepository;
import com.shallwecode.backend.user.domain.service.FriendDomainService;
import com.shallwecode.backend.user.domain.service.NotiDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendDomainService friendDomainService;
    private final NotiDomainService notiDomainService;

    @Transactional
    public void saveFriend(SaveFriendReqDTO saveFriendReqDTO) {

        Long loginUserId = 3L;

        boolean isExistFriendResult = friendDomainService.isExistFriend(loginUserId, saveFriendReqDTO.getToUserId());

        if (isExistFriendResult) {
            throw new CustomException(ErrorCode.SENDED_FRIEND);
        }

        if (loginUserId.equals(saveFriendReqDTO.getToUserId())) {
            throw new CustomException(ErrorCode.DUPLICATED_FRIEND_USER);
        }

        try {
            SaveFriendDTO SaveFriendDTO = friendDomainService.save(saveFriendReqDTO);

            String notiContent = SaveFriendDTO.getFromUser().getNickname()
                    + "님이 친구 신청하였습니다.";
            SaveNotiDTO saveNotiDTO = new SaveNotiDTO(
                    SaveFriendDTO.getToUser(),
                    NotiType.FRIEND,
                    notiContent
            );

            notiDomainService.save(saveNotiDTO);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.NOT_SAVED_FRIEND);
        }
    }

    @Transactional
    public void updateFriend(UpdateFriendReqDTO updateFriendReqDTO, boolean result) {

        FindFriendDTO findFriendDTO = friendDomainService.findMyFriend(updateFriendReqDTO);

        try {
            String notiContent = "";
            String toUserNickname = findFriendDTO.getToUser().getNickname();
            if (result) {
                notiContent += toUserNickname + "님이 친구 요청을 수락하셨습니다.";
                findFriendDTO.setFriendStatus(FriendStatus.ACCEPTED);
                friendDomainService.updateFriendStatus(findFriendDTO);
            } else {
                notiContent += toUserNickname + "님이 친구 요청을 거절하셨습니다.";
                friendDomainService.deleteFriend(findFriendDTO);
            }

            notiDomainService.save(new SaveNotiDTO(
                    findFriendDTO.getFromUser(),
                    NotiType.FRIEND,
                    notiContent
            ));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.NOT_CHANGED_FRIEND);
        }
    }
}
