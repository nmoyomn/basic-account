package com.task.account.module.member.application.dto;


import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequest {
  @NotBlank(message = "userId cannot be blank")
  private String userId;
  @NotBlank(message = "password cannot be blank")
  private String password;

}
