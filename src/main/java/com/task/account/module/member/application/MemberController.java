package com.task.account.module.member.application;

import com.task.account.common.dto.ResponseDto;
import com.task.account.module.member.application.dto.MemberCreate;
import com.task.account.module.member.application.dto.MemberLoginRequest;
import com.task.account.module.member.application.dto.MemberLoginResponse;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Long>> signup(@Valid @RequestBody MemberCreate memberCreate) {
        return ResponseDto.created(memberService.create(memberCreate));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<MemberLoginResponse>> login(@Valid @RequestBody MemberLoginRequest memberLoginRequest) {
        return ResponseDto.ok(memberService.login(memberLoginRequest));
    }
}
