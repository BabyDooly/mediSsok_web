package mediSsok.mediSsokspring.domain.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
@Getter
@Entity
@Table(name = "member")
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

    // 약통 리스트
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MedicineBox> MedicineBoxs = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String password, String nickname, String phone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
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