package com.shallwecode.backend.user.application.controller;

import com.shallwecode.backend.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

}
