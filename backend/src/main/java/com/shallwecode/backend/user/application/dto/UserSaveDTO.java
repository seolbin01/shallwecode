package com.shallwecode.backend.user.application.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "user_info")
public class UserSaveDTO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String provider;
    private String ProviderId;
    @Column(unique = true, nullable = false)
    private String email;
    private String nickname;
    private String auth;
    private Date createdAt;
}
