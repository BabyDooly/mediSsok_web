package mediSsok.mediSsokspring.domain.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
@Getter
@Entity
@Table(name = "link_info")
@DynamicInsert
@DynamicUpdate
public class LinkInfo extends BaseTimeEntity {
    @Id
    @Column(name = "link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    // 연동 유저 이메일
    @Column(name = "link_user_Email", length = 50, nullable = false)
    private String userEmail;

    // 연동 닉네임
    @Column(name = "link_nickname", length = 20, nullable = false)
    private String nickname;

    // 연동 허용
    @Column(name = "link_permit")
    @ColumnDefault("false")
    private Boolean permit;

    // 사용자 ID(Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public LinkInfo(Long id, String userEmail, String nickname, boolean permit, Member member) {
        this.id = id;
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.permit = permit;
        this.member = member;
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }
}