package mediSsok.mediSsokspring.domain.repository.member;

import mediSsok.mediSsokspring.domain.entity.member.LinkInfo;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkInfoRepository extends JpaRepository<LinkInfo, Long> {
    // 링크 리스트
    List<LinkInfo> findByMemberId(Long memberId);

    // 연동 신청
    List<LinkInfo> findByUserEmailAndPermit(String email, Boolean permit);

    // 이메일 중복하면 true 반환
    boolean existsByUserEmailAndMemberId (String email, Long memberId);
}