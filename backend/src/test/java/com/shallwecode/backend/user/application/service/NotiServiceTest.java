package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.user.application.dto.noti.NotiResListDTO;
import com.shallwecode.backend.user.application.dto.noti.UpdateNotiReqDTO;
import com.shallwecode.backend.user.domain.aggregate.Noti;
import com.shallwecode.backend.user.domain.repository.NotiRepository;
import com.shallwecode.backend.user.domain.service.NotiDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NotiServiceTest {

    @Autowired
    private NotiService notiService;
    private NotiDomainService notiDomainService;
    @Autowired
    private NotiRepository notiRepository;

    @Test
    @DisplayName("내 헤더 알림 찾기")
    void findMyHeaderNoti() {
        // given
        Long userId = 3L;

        // when
        List<NotiResListDTO> myHeaderNotis = notiService.findMyNoti(userId, true);

        // then
        assertThat(myHeaderNotis.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("내 마이페이지 알림 찾기")
    void findMyPageNoti() {
        // given
        Long userId = 3L;

        // when
        List<NotiResListDTO> myHeaderNotis = notiService.findMyNoti(userId, false);

        // then
        assertThat(myHeaderNotis.size()).isEqualTo(6);
    }

    @Test
    void updateNoti() {
        // given
        Long notiId = 16L;
        UpdateNotiReqDTO updatedNoti = new UpdateNotiReqDTO();
        updatedNoti.setNotiId(notiId);
        Noti noti = notiRepository.findById(notiId).orElse(null);

        // when
        notiService.updateNoti(updatedNoti);

        // then
        assertThat(noti.getIsRead()).isEqualTo(1);
    }
}