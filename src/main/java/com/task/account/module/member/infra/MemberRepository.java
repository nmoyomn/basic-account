package com.task.account.module.member.infra;


import com.task.account.module.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  boolean existsByUserId(String userId);
  Optional<Member> findByUserId(String userId);
}
