package com.shallwecode.backend.user.domain.service;

import com.shallwecode.backend.user.application.dto.SaveNotiDTO;
import com.shallwecode.backend.user.domain.aggregate.Noti;
import com.shallwecode.backend.user.domain.repository.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotiDomainService {

    private final NotiRepository notiRepository;
    private final ModelMapper modelMapper;

    public void save(SaveNotiDTO saveNotiDTO) {
        Noti newNoti = modelMapper.map(saveNotiDTO, Noti.class);
        notiRepository.save(newNoti);
    }
}
