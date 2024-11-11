package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.CoopUserResDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CoopDomainServiceTest {

    @Autowired
    private CoopDomainService coopDomainService;

    @Test
    void selectCoopFriendTest() {
        List<CoopUserResDTO> coopUserList = coopDomainService.selectCoopFriend(3L);
        for (CoopUserResDTO coopUserResDTO : coopUserList)
            System.out.println(coopUserResDTO);
    }
}