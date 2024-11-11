package com.shallwecode.backend.problem.domain.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwecode.backend.problem.application.dto.CoopDTO;
import com.shallwecode.backend.problem.application.dto.CoopResDTO;
import com.shallwecode.backend.problem.application.dto.CoopUserResDTO;
import com.shallwecode.backend.problem.domain.aggregate.Coop;
import com.shallwecode.backend.problem.domain.aggregate.QCoop;
import com.shallwecode.backend.problem.domain.repository.CoopRepository;
import com.shallwecode.backend.user.domain.aggregate.QUserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoopDomainService {

    private final ModelMapper modelMapper;
    private final CoopRepository coopRepository;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void inviteCoopFriend(CoopDTO coopDTO) {

        Coop coop = modelMapper.map(coopDTO, Coop.class);
        coopRepository.save(coop);
    }

    @Transactional
    public CoopResDTO findByCoop(Long codingRoomId, Long userId) {

        return modelMapper.map(coopRepository.findByCodingRoomIdAndUserId(codingRoomId, userId), CoopResDTO.class);

    }

    @Transactional
    public CoopResDTO findCoopByCoopId(Long codingRoomId, Long coopId) {

        return modelMapper.map(coopRepository.findByCodingRoomIdAndCoopId(codingRoomId, coopId), CoopResDTO.class);

    }

    @Transactional
    public void delete(Long coopId){

        coopRepository.deleteById(coopId);

    }

    public List<CoopUserResDTO> selectCoopFriend(Long codingRoomId) {
        /* UserInfo */
        QUserInfo qUserInfo = QUserInfo.userInfo;
        QCoop qCoop = QCoop.coop;

        return queryFactory.select(Projections.constructor(CoopUserResDTO.class,
                qCoop.codingRoomId,
                qCoop.userId,
                qUserInfo.nickname))
                .from(qCoop)
                .join(qUserInfo).on(qCoop.userId.eq(qUserInfo.userId))
                .where(qCoop.codingRoomId.eq(codingRoomId))
                .fetch();
    }
}
