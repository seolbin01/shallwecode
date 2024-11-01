package com.shallwecode.backend.user.domain.service;

import com.shallwecode.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDomainService {
    private final UserRepository userRepository;
}
