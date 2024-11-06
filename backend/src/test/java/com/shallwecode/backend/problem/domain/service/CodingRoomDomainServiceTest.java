package com.shallwecode.backend.problem.domain.service;

import com.shallwecode.backend.problem.application.dto.SendCodeDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class CodingRoomDomainServiceTest {

    @Autowired
    private CodingRoomDomainService codingRoomDomainService;

    private static Stream<Arguments> updateSendCode() {
        return Stream.of(
                Arguments.of(
                        1,
                        1,
                        "JAVA",
                        "Update Code"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("updateSendCode")
    void updateCodeTest(Long codingRoomId, Long problemId, String tryLanguage, String codeContent) {
        SendCodeDTO sendCodeDTO = new SendCodeDTO(codingRoomId, problemId, tryLanguage, codeContent);
        codingRoomDomainService.updateCode(sendCodeDTO);
    }
}
