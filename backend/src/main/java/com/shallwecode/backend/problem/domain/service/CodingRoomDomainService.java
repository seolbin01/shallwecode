package com.shallwecode.backend.problem.domain.service;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwecode.backend.problem.application.dto.CodingRoomReqDTO;
import com.shallwecode.backend.problem.application.dto.FindMyCodingRoomResDTO;
import com.shallwecode.backend.problem.domain.aggregate.CodingRoom;
import com.shallwecode.backend.problem.domain.aggregate.QCodingRoom;
import com.shallwecode.backend.problem.domain.aggregate.QCoop;
import com.shallwecode.backend.problem.domain.aggregate.QProblem;
import com.shallwecode.backend.problem.domain.repository.CodingRoomRepository;
import com.shallwecode.backend.problem.domain.repository.CoopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodingRoomDomainService {

    private final CodingRoomRepository repository;
    private final CoopRepository coopRepository;
    private final ModelMapper modelMapper;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void saveCodingRoom(CodingRoomReqDTO newCodingRoom) {

        CodingRoom codingRoom = modelMapper.map(newCodingRoom, CodingRoom.class);
        repository.save(codingRoom);

        // 추가적으로 설정이 필요한지 DB 관계 고려해보기
    }

    // 코딩방 수정 기능은 제공하지 않음.

    @Transactional
    public void deleteCodingRoom(Long codingRoomId) {

        // 코딩방에 초대된 협업 친구 목록 전부 삭제 후 코딩방 제거
        coopRepository.deleteByCodingRoomId(codingRoomId);
        repository.deleteById(codingRoomId);

    }

    public List<FindMyCodingRoomResDTO> findMyCodingRoom(Long userId) {

        QCodingRoom codingRoom = QCodingRoom.codingRoom;
        QCoop coop = QCoop.coop;
        QProblem problem = QProblem.problem;

        return queryFactory
                .select(Projections.constructor(FindMyCodingRoomResDTO.class,
                        codingRoom.codingRoomId,
                        problem.title,
                        codingRoom.isOpen,
                        Expressions.asNumber(
                                JPAExpressions.select(coop.userId.count())
                                        .from(coop)
                                        .where(coop.codingRoomId.eq(codingRoom.codingRoomId))
                        ).intValue()))
                .from(codingRoom)
                .join(coop).on(codingRoom.codingRoomId.eq(coop.codingRoomId))
                .join(problem).on(codingRoom.problemId.eq(problem.problemId))
                .where(coop.userId.eq(userId))
                .fetch();
    }
}
