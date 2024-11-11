package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.application.dto.coop.CoopDTO;
import com.shallwecode.backend.problem.domain.aggregate.CodingRoom;
import com.shallwecode.backend.problem.domain.repository.CodingRoomRepository;
import com.shallwecode.backend.problem.domain.service.CoopDomainService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class CodingRoomServiceTest {

    
    @Autowired
    private CodingRoomService codingRoomService;

    @Autowired
    private CodingRoomRepository codingRoomRepository;

    @Autowired
    private CoopDomainService coopDomainService;
    

    @Test
    @DisplayName("코딩방 생성 테스트")
    void saveCodingRoomTest() {

        Long problemId = 1L;    // 예시 문제 ID
        Long loginUserId = 23L; // 예시 사용자 ID

        // 코딩방 생성 메서드를 호출
        CodingRoom codingRoom = codingRoomService.saveCodingRoom(problemId, loginUserId);

        // 코딩방이 정상적으로 생성되었는지 확인
        assertThat(codingRoom).isNotNull();
        assertThat(codingRoom.getCodingRoomId()).isNotNull();
        assertThat(codingRoom.getProblemId()).isEqualTo(problemId);

        // 생성된 코딩방이 데이터베이스에 저장되었는지 확인
        CodingRoom savedRoom = codingRoomRepository.findById(codingRoom.getCodingRoomId()).orElse(null);
        assertThat(savedRoom).isNotNull();
        assertThat(savedRoom.getProblemId()).isEqualTo(problemId);

        // 초대된 협업 멤버가 정상적으로 처리되었는지 확인하기 위해 CoopDTO를 검사
        CoopDTO coopDTO = new CoopDTO();
        coopDTO.setCodingRoomId(codingRoom.getCodingRoomId());
        coopDTO.setUserId(loginUserId);
        coopDTO.setHost(true);

        // 초대된 협업 멤버 정보가 저장되었는지 확인
        List<CoopDTO> coopMembers = coopDomainService.findCoopMembersByCodingRoomId(codingRoom.getCodingRoomId());

        // 협업 멤버가 한 명 추가된 상태인지 확인
        assertThat(coopMembers).hasSize(1);

        CoopDTO savedCoopMember = coopMembers.get(0);
        assertThat(savedCoopMember.getCodingRoomId()).isEqualTo(codingRoom.getCodingRoomId());
        assertThat(savedCoopMember.getUserId()).isEqualTo(loginUserId);
        assertThat(savedCoopMember.isHost()).isTrue(); // Host로 설정되었는지 확인
    }

}