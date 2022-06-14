package mediSsok.mediSsokspring.domain.repository;

import mediSsok.mediSsokspring.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String userId);
}