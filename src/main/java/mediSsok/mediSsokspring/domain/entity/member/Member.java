package mediSsok.mediSsokspring.domain.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
public class Member extends BaseTimeEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    @Column(name = "member_email", length = 50, nullable = false)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_nickname", length = 20, nullable = false)
    private String nickname;

    @Column(name = "member_phone", length = 30, nullable = false)
    private String phone;

    @Column(name = "member_location", length = 100)
    private String location;

    // 진동모드
    @Column(name = "member_vibration_mode", nullable = false)
    private Boolean vibration = false;

    // 푸쉬알림
    @Column(name = "member_push_alarm", nullable = false)
    private Boolean pushAlarms = true;

    // 위치알림
    @Column(name = "member_location_alarm", nullable = false)
    private Boolean locationAlarms = true;

    // 출근알림
    @Column(name = "member_work_alarm")
    private Date workAlarms;

    // 보충알림
    @Column(name = "member_replenish_alarm", nullable = false)
    private Boolean replenishAlarms = true;

    @Builder
    public Member(Long id, String email, String password, String nickname, String phone, String location, Boolean vibration, Boolean pushAlarms, Boolean locationAlarms, Date workAlarms, Boolean replenishAlarms) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.location = location;
        this.vibration = vibration;
        this.pushAlarms = pushAlarms;
        this.locationAlarms = locationAlarms;
        this.workAlarms = workAlarms;
        this.replenishAlarms = replenishAlarms;
    }

    // 개인정보 변경
    public void userUpdate(String nickname, String phone) {
        this.nickname = nickname;
        this.phone = phone;
    }

    // 알람 변경
    public void alarmUpdate(Boolean vibration, Boolean pushAlarms, Boolean locationAlarms, Boolean replenishAlarms) {
        this.vibration = vibration;
        this.pushAlarms = pushAlarms;
        this.locationAlarms = locationAlarms;
        this.replenishAlarms = replenishAlarms;
    }
}