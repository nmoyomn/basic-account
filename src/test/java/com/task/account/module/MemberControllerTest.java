package com.task.account.module;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.account.module.member.application.dto.MemberCreate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest implements MemberMock{

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void successCreateTest() throws Exception {
    MemberCreate member = successCreate();

    mockMvc.perform(post("/member/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(member)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message").doesNotExist());
  }

  @Test
  void failUserIdLengthExceededTest() throws Exception {
    MemberCreate member = failUserIdLengthExceeded();

    mockMvc.perform(post("/member/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(member)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsString("userId")));
  }

  @Test
  void failInvalidEmailTest() throws Exception {
    MemberCreate member = failInvalidEmail();

    mockMvc.perform(post("/member/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(member)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void failNotEqualsPasswordTest() throws Exception {
    MemberCreate member = failNotEqualsPassword();

    mockMvc.perform(post("/member/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(member)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("비밀번호가 일치하지 않습니다"));
  }

  @Test
  void failInvalidPasswordTest() throws Exception {
    MemberCreate member = failInvalidPassword();

    mockMvc.perform(post("/member/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(member)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsString("비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다")));

  }

  @Test
  void failDuplicateUserIdTest() throws Exception {
    MemberCreate successMember = successCreate2();
    MemberCreate duplicateMember = failDuplicateUserId();

    mockMvc.perform(post("/member/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(successMember)))
        .andExpect(status().isCreated());

    mockMvc.perform(post("/member/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(duplicateMember)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("아이디가 중복되었습니다"));
  }
}

