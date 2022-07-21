package mediSsok.mediSsokspring.domain.repository.member;

import mediSsok.mediSsokspring.domain.entity.member.LinkInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkInfoRepository extends JpaRepository<LinkInfo, Long> {
    
    // 중복하면 true 반환
    boolean existsByUserEmailAndMemberId (String email, Long memberId);
}