package com.task.account.module.member.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginResponse {
  private String userId;
  private String accessToken;
}
