package com.task.account.module.member.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponse {
  private Long id;
  private String userId;
  private String password;

}
