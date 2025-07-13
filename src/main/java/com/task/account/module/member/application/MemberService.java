package com.task.account.module.member.application;


import com.task.account.common.dto.JwtProperties;
import com.task.account.common.util.BcryptUtil;
import com.task.account.module.member.application.dto.MemberCreate;
import com.task.account.module.member.application.dto.MemberLoginRequest;
import com.task.account.module.member.application.dto.MemberLoginResponse;
import com.task.account.module.member.application.dto.MemberResponse;
import com.task.account.module.member.domain.Member;
import com.task.account.module.member.infra.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  private final JwtProperties jwtProperties;

  @Transactional
  public Long create(MemberCreate memberCreate) {
    validateCreate(memberCreate);
    Member member = memberCreate.convertEntity();
    memberRepository.save(member);
    return member.getId();
  }

  @Transactional(readOnly = true)
  public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
    String userId = memberLoginRequest.getUserId();
    String password = memberLoginRequest.getPassword();
    Member member = findByUserId(userId);
    if (!BcryptUtil.checkPassword(password, member.getPassword())) {
      throw new RuntimeException("아이디와 비밀번호를 확인해주세요");
    };

    return MemberLoginResponse.builder()
            .userId(userId)
            .accessToken(createAccessToken(userId))
            .build();
  }

  @Transactional(readOnly = true)
  public MemberResponse detailResponse(String userId) {
    return findByUserId(userId).convertDto();
  }

  @Transactional(readOnly = true)
  public Member findByUserId(String userId) {
    return memberRepository.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("아이디와 비밀번호를 확인해주세요"));
  }

  private void validateCreate(MemberCreate memberCreate) {

    if (isNotEqualsPassword(memberCreate)) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다");
    }

    if (isInvalidPassword(memberCreate.getPassword())) {
      throw new RuntimeException("비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다");
    }

    if (isExistMemberByUserId(memberCreate.getUserId())) {
      throw new RuntimeException("아이디가 중복되었습니다");
    }
  }

  private boolean isNotEqualsPassword(MemberCreate memberCreate) {
    return !memberCreate.getPassword().equals(memberCreate.getPasswordConfirm());
  }

  private boolean isInvalidPassword(String password) {
    return !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,}$");
  }


  private boolean isExistMemberByUserId(String userId) {
    return memberRepository.existsByUserId(userId);
  }

  public String createAccessToken(String userId) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expiryDate = now.plusMinutes(30);

    return Jwts.builder()
            .setSubject(userId)
            .claim("userId", userId)
            .setIssuedAt(ldtToDate(now))
            .setExpiration(ldtToDate(expiryDate))
            .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()))
            .compact();
  }

  private Date ldtToDate(LocalDateTime ldt) {
    return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
  }
}