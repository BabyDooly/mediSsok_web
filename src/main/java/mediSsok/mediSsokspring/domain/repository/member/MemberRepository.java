package mediSsok.mediSsokspring.domain.repository.member;

import mediSsok.mediSsokspring.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String userId);
}