package com.task.account.config.jwt;

import com.task.account.common.code.Constants;
import com.task.account.common.dto.JwtProperties;
import com.task.account.common.util.SecurityUtil;
import com.task.account.module.member.application.MemberService;
import com.task.account.module.member.application.dto.MemberResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class JwtTokenService implements UserDetailsService {
  private final MemberService memberService;
  private final JwtProperties jwtProperties;
  private final SecurityUtil securityUtil;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    MemberResponse memberResponse = memberService.detailResponse(username);
    Collection<SimpleGrantedAuthority> authorities =
            Collections.singletonList(new SimpleGrantedAuthority(Constants.ROLE_USER));
    return new User(memberResponse.getUserId(), memberResponse.getPassword(), authorities);
  }

  public boolean validateJwtToken(HttpServletRequest request) {
    String jwtToken = resolveToken(request);
    if (StringUtils.isBlank(jwtToken)) {
      log.debug("Failed to get header token");
      return false;
    }

    try {
      Claims claims = jwtBuilder(jwtToken);

      if (validateClaims(claims)) {
        log.debug("Failed to get claims");
        return false;
      }

      String userId = claims.get("userId", String.class);
      if (StringUtils.isNotBlank(userId)) {
        UserDetails userDetails = loadUserByUsername(userId);
        securityUtil.setAuthentication(userDetails);
      }

      return true;
    } catch (Exception e) {
      log.warn("Invalid JWT token from IP {}: {}", request.getRemoteAddr(), e.getMessage());
      return false;
    }
  }

  private String resolveToken(HttpServletRequest request) {
    String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.isBlank(authToken)) {
      return StringUtils.EMPTY;
    }

    if (authToken.startsWith(Constants.BEARER)) {
      authToken = StringUtils.substringAfter(authToken, Constants.BEARER);
    };

    return authToken;
  }

  private Claims jwtBuilder(String jwtToken) {
    return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()))
            .build()
            .parseClaimsJws(jwtToken)
            .getBody();
  }

  private boolean validateClaims(Claims claims) {
    return ObjectUtils.isEmpty(claims) || new Date().after(claims.getExpiration());
  }

}
