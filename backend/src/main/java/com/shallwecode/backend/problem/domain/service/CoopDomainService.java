package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.CoopReqDTO;
import com.shallwecode.backend.problem.application.dto.CoopResDTO;
import com.shallwecode.backend.problem.domain.aggregate.Coop;
import com.shallwecode.backend.problem.domain.repository.CoopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoopDomainService {

    private final ModelMapper modelMapper;
    private final CoopRepository coopRepository;

    public void inviteCoopFriend(Long userId, Long coopId, CoopReqDTO coopReqDTO) {

        Coop coop = modelMapper.map(coopReqDTO, Coop.class);
        coop.updateCoopUser(userId);
        coop.updateCodingRoom(coopId);

        coopRepository.save(coop);
    }


    /* 협업 친구 강퇴 기능 */
    public CoopResDTO findByCoopId(Long coopId) {

        return modelMapper.map(coopRepository.findById(coopId), CoopResDTO.class);
    }

    public void delete(Long coopId){

        coopRepository.deleteById(coopId);

    }


}
