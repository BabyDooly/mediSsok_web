package mediSsok.mediSsokspring.domain.repository.member;

import mediSsok.mediSsokspring.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String userId);

    // 중복 검사 로직 ( 닉네임, 이메일 )
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}