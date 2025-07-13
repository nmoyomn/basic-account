package com.task.account.module.member.domain;


import com.task.account.module.member.application.dto.MemberResponse;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(name = "uk01_member_user_id", columnNames = {"userId"})})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;

    public MemberResponse convertDto() {
        return MemberResponse.builder()
                .id(this.id)
                .userId(this.userId)
                .password(this.password)
                .build();
    }

}
