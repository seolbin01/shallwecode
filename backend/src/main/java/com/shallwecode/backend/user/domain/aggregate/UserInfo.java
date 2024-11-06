package com.shallwecode.backend.user.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="user_info")
@Getter
@Setter
@ToString
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String provider;
    private String providerId;
    @Column(unique = true, nullable = false)
    private String email;
    private String nickname;
    private String auth;
    private Date createdAt;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Friend> friendsFrom;

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Friend> friendsTo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Noti> notiList;

    public UserInfo()
    {}

    public void updateUser(String nickname){
        this.nickname = nickname;
    }
}
