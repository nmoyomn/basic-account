package com.task.account.common.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.jwt.token")
@Data
public class JwtProperties {
  private String secret;
  private int expire;
}
