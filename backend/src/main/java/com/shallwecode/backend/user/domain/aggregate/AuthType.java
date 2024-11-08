package com.shallwecode.backend.user.domain.aggregate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthType {
    GUEST("GUEST"), USER("USER"), ADMIN("ADMIN");

    private final String key;
}
