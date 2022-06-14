package mediSsok.mediSsokspring.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {

    /*
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
     */
    @Id
    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String phoneNum;

    @Builder
    public MemberEntity(String email, String password, String nickname, String phoneNum) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }
}