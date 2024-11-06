package com.shallwecode.backend.user.domain.aggregate;

import lombok.Getter;

@Getter
public enum AuthType {
    GUEST, USER, ADMIN;
}
