package com.sparta.spartaoutsourcing.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {
    @NotBlank(message = "사용자명은 null이나 공백이 아니어야 합니다.")
    private String username;
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$", message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함합니다.")
    private String password;
    private Boolean isOwner = false;
    private String ownerKey = "";

    public UserSignupRequestDto(String username, String email, String password, boolean isOwner, String ownerKey) {
        this.username = username;
        this.email = email;
        this.password = password;
        if (isOwner) {
            this.isOwner = isOwner;
            this.ownerKey = ownerKey;
        }
    }
}
