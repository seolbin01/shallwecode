package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.user.application.dto.SaveFriendReqDTO;
import com.shallwecode.backend.user.application.dto.SaveFriendResDTO;
import com.shallwecode.backend.user.application.dto.SaveNotiDTO;
import com.shallwecode.backend.user.domain.aggregate.NotiType;
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
            SaveFriendResDTO saveFriendResDTO = friendDomainService.save(saveFriendReqDTO);

            String notiContent = saveFriendResDTO.getFromUser().getNickname()
                    + "님이 친구 신청하였습니다.";
            SaveNotiDTO saveNotiDTO = new SaveNotiDTO(
                    saveFriendResDTO.getToUser(),
                    NotiType.FRIEND,
                    notiContent
            );

            notiDomainService.save(saveNotiDTO);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.NOT_SAVED_FRIEND);
        }
    }
}
