package com.task.account.module.member.application.dto;


import com.task.account.common.util.BcryptUtil;
import com.task.account.module.member.domain.Member;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
public class MemberCreate {
    @NotBlank(message = "userId는 필수입니다.")
    @Email(message = "이메일 형식이어야 합니다")
    @Size(max = 32, message = "아이디는 최대 32자까지 입력 가능합니다")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$",
        message = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다"
    )
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String passwordConfirm;

    public Member convertEntity() {
        return Member.builder()
            .userId(userId)
            .password(BcryptUtil.encrypt(password))
            .build();
    }

}
