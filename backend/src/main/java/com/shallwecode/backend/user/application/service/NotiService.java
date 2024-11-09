package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.user.application.dto.noti.NotiResListDTO;
import com.shallwecode.backend.user.application.dto.noti.UpdateNotiReqDTO;
import com.shallwecode.backend.user.domain.service.NotiDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiService {

    private final NotiDomainService notiDomainService;

    public List<NotiResListDTO> findMyNoti(Long loginUserId, boolean result) {

        if (result) {
            return notiDomainService.findMyNoti(loginUserId, result);
        } else {
            return notiDomainService.findMyNoti(loginUserId, result);
        }
    }

    @Transactional
    public void updateNoti(UpdateNotiReqDTO updateNotiReqDTO) {
        notiDomainService.updateNotiStatus(updateNotiReqDTO);
    }
}
