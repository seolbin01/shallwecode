package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.user.application.dto.NotiResListDTO;
import com.shallwecode.backend.user.application.service.NotiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/noti")
@Tag(name = "Noti", description = "알림 API")
public class NotiController {

    private final NotiService notiService;

    @Operation(summary = "헤더 알림 조회", description = "헤더 알림을 조회한다.")
    @GetMapping
    public ResponseEntity<List<NotiResListDTO>> findHeaderNotiList() {
        Long loginUserId = 1L;

        List<NotiResListDTO> headerNotiResList = notiService.findMyNoti(loginUserId, true);
        return ResponseEntity.ok(headerNotiResList);
    }

    @Operation(summary = "마이페이지 알림 조회", description = "마이페이지 알림을 조회한다.")
    @GetMapping("/all")
    public ResponseEntity<List<NotiResListDTO>> findMyPageNotiList() {
        Long loginUserId = 1L;

        List<NotiResListDTO> headerNotiResList = notiService.findMyNoti(loginUserId, false);
        return ResponseEntity.ok(headerNotiResList);
    }
}
