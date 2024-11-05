package com.shallwecode.backend.user.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFriendReqDTO {

    @NotNull(message = "Null이면 안됩니다.")
    private Long fromUserId;
}
