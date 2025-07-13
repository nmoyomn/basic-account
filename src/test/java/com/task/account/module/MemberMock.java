package com.task.account.module;


import com.task.account.module.member.application.dto.MemberCreate;
import org.apache.commons.lang3.StringUtils;

public interface MemberMock {

    default MemberCreate successCreate() {
        return MemberCreate.builder()
            .userId("msyoon@account.com")
            .password("Password1!")
            .passwordConfirm("Password1!")
            .build();
    }

    default MemberCreate failUserIdLengthExceeded() {
        return MemberCreate.builder()
            .userId(StringUtils.repeat("a", 32)+ "@ex.com")
            .password("Password1!")
            .passwordConfirm("Password1!")
            .build();
    }

    default MemberCreate failInvalidEmail() {
        return MemberCreate.builder()
            .userId("invalid-email")
            .password("Password1!")
            .passwordConfirm("Password1!")
            .build();
    }

    default MemberCreate failNotEqualsPassword() {
        return MemberCreate.builder()
            .userId("msyoon@account.com")
            .password("Password1!")
            .passwordConfirm("Password2!")  // 불일치
            .build();
    }

    default MemberCreate failInvalidPassword() {
        return MemberCreate.builder()
            .userId("msyoon@account.com")
            .password("password") // 대문자, 숫자, 특수문자 없음
            .passwordConfirm("password")
            .build();
    }

    default MemberCreate successCreate2() {
        return MemberCreate.builder()
            .userId("msyoon2@account.com")
            .password("Password1!")
            .passwordConfirm("Password1!")
            .build();
    }

    default MemberCreate failDuplicateUserId() {
        return successCreate2();
    }

}
