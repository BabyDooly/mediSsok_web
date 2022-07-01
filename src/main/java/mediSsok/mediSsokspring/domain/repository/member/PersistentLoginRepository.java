package mediSsok.mediSsokspring.domain.repository.member;

import mediSsok.mediSsokspring.domain.entity.member.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String> {

    Optional<PersistentLogin> findBySeries(final String series);

    List<PersistentLogin> findByUsername(final String username);

}