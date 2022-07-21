package mediSsok.mediSsokspring.domain.entity.member;

import lombok.*;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
@Getter
@Entity
@Table(name = "member")
@DynamicInsert
@DynamicUpdate
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

    @Column(name = "member_phone", length = 30)
    private String phone;

    // 진동모드
    @Column(name = "member_vibration_mode")
    @ColumnDefault("false")
    private Boolean vibration;

    // 푸쉬알림
    @Column(name = "member_push_alarm")
    @ColumnDefault("true")
    private Boolean pushAlarms;

    // 위치알림
    @Column(name = "member_location_alarm")
    @ColumnDefault("false")
    private Boolean locationAlarms;

    // 보충알림
    @Column(name = "member_replenish_alarm")
    @ColumnDefault("false")
    private Boolean replenishAlarms;

    // 출근알림
    @Column(name = "member_work_alarm")
    private LocalTime workAlarms;

    // 약통 리스트
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MedicineBox> medicineBoxs = new ArrayList<>();

    // 스케줄 리스트
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ScheduleDate> ScheduleDates = new ArrayList<>();

    // 연동 리스트
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LinkInfo> linkInfo = new ArrayList<>();

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

    // 비밀번호 변경
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    // 기존비밀번호 체크
    public void nowPassword(String password) {
        this.password = password;
    }

    // 알람 변경
    public void alarmUpdate(Boolean vibration, Boolean pushAlarms, Boolean locationAlarms, Boolean replenishAlarms, LocalTime workAlarms) {
        this.vibration = vibration;
        this.pushAlarms = pushAlarms;
        this.locationAlarms = locationAlarms;
        this.replenishAlarms = replenishAlarms;
        this.workAlarms = workAlarms;
    }
}

