package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.problem.domain.aggregate.CodingRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodingRoomServiceTest {

    @Autowired
    private CodingRoomService codingRoomService;

    @Test
    void saveCodingRoomTest() {
        CodingRoom codingRoom = codingRoomService.saveCodingRoom(4L);
        System.out.println(codingRoom.getCodingRoomId());
        System.out.println(codingRoom.getCodeContent());
        System.out.println(codingRoom.getProblemId());
        System.out.println(codingRoom.isOpen());
        System.out.println(codingRoom.getTryLanguage());
    }
}