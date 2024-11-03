package com.shallwecode.backend.friend.application.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_SAVED_FRIEND(HttpStatus.CONFLICT, "친구 신청 실패");

    private final HttpStatus httpStatus;
    private final String message;
}
