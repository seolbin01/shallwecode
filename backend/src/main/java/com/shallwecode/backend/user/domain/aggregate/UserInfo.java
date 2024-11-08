package com.shallwecode.backend.user.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="user_info")
@Getter
@ToString
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(value = EnumType.STRING)
    private SocialType provider;
    @Column(unique = true, nullable = false)
    private String email;
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private AuthType auth;
    private Date createdAt;

//    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Friend> friendsFrom;
//
//    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Friend> friendsTo;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Noti> notiList;

    public UserInfo()
    {}

    public void updateUser(String nickname){
        this.nickname = nickname;
    }
}
