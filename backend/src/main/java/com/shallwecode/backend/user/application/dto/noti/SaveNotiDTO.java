package com.shallwecode.backend.user.application.dto.noti;

import com.shallwecode.backend.user.application.dto.user.FindUserDTO;
import com.shallwecode.backend.user.domain.aggregate.NotiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveNotiDTO {

    private FindUserDTO user;
    private NotiType notiType;
    private String content;
}
