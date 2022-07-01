package mediSsok.mediSsokspring.domain.entity.member;

import lombok.Getter;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Table(name = "persistent_logins")
public class PersistentLogin implements Serializable {
    @Id
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false, length = 64)
    private Date lastUsed;

    // JPA의 한계로 기본생성자가 반드시 필요하지만 private으로는 설정할 수 없다.
    protected PersistentLogin() {
    }

    // 생성자를 외부에 노출하지 않습니다.
    private PersistentLogin(final PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.token = token.getTokenValue();
        this.lastUsed = token.getDate();
    }

    // 정적 팩토리 메서드
    public static PersistentLogin from(final PersistentRememberMeToken token) {
        return new PersistentLogin(token);
    }

    public void updateToken(final String tokenValue, final Date lastUsed) {
        this.token = tokenValue;
        this.lastUsed = lastUsed;
    }
}