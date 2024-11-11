package com.shallwecode.backend.user.domain.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.user.application.dto.noti.NotiResListDTO;
import com.shallwecode.backend.user.application.dto.noti.SaveNotiDTO;
import com.shallwecode.backend.user.application.dto.noti.UpdateNotiReqDTO;
import com.shallwecode.backend.user.domain.aggregate.Noti;
import com.shallwecode.backend.user.domain.aggregate.QNoti;
import com.shallwecode.backend.user.domain.repository.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiDomainService {

    private final NotiRepository notiRepository;
    private final ModelMapper modelMapper;
    private final JPAQueryFactory queryFactory;

    public void save(SaveNotiDTO saveNotiDTO) {
        Noti newNoti = modelMapper.map(saveNotiDTO, Noti.class);
        notiRepository.save(newNoti);
    }

    public List<NotiResListDTO> findMyNoti(Long loginUserId, boolean result) {
        QNoti noti = QNoti.noti;

        if (result) { // 헤더 알림
            return queryFactory
                    .select(Projections.constructor(NotiResListDTO.class,
                            noti.notiId,
                            noti.codingRoomId,
                            noti.content,
                            noti.isRead,
                            noti.createdAt))
                    .from(noti)
                    .where(noti.user.userId.eq(loginUserId)
                            .and(noti.isRead.eq(0)))
                    .fetch();
        } else { // 마이페이지 알림
            return queryFactory
                    .select(Projections.constructor(NotiResListDTO.class,
                            noti.notiId,
                            noti.codingRoomId,
                            noti.content,
                            noti.isRead,
                            noti.createdAt))
                    .from(noti)
                    .where(noti.user.userId.eq(loginUserId))
                    .fetch();
        }
    }

    public void updateNotiStatus(UpdateNotiReqDTO updateNotiReqDTO) {
        Noti findNoti = notiRepository.findById(updateNotiReqDTO.getNotiId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_NOTI));

        findNoti.updateStatus();
    }

    public void deleteNotiByUserId(Long userId) {
        notiRepository.deleteByUser_UserId(userId);
    }

}
