package mediSsok.mediSsokspring.domain.entity.schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
@Getter
@Entity
@Table(name = "date_info")
@DynamicInsert
@DynamicUpdate
public class DateInfo extends BaseTimeEntity {
    @Id
    @Column(name = "date_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    // 일정 날짜,시간
    @Column(name = "date_alarm_datetime", nullable = false)
    private LocalDateTime alarmDatetime;

    // 복용 여부
    @Column(name = "date_eat_check")
    @ColumnDefault("false")
    private Boolean eatCheck;

    // 알람 여부
    @Column(name = "date_alarm_check")
    @ColumnDefault("true")
    private Boolean alarmCheck;

    // 사용자 ID(Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 스케줄 ID(ScheduleDate)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sche_id")
    private ScheduleDate scheduleDate;

    // 약통 ID(MedicineBox)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medbox_id")
    private MedicineBox medicineBox;
    @Builder
    public DateInfo(LocalDateTime alarmDatetime, Boolean eatCheck, Boolean alarmCheck, Member member, ScheduleDate scheduleDate, MedicineBox medicineBox) {
        this.alarmDatetime = alarmDatetime;
        this.eatCheck = eatCheck;
        this.alarmCheck = alarmCheck;
        this.member = member;
        this.scheduleDate = scheduleDate;
        this.medicineBox = medicineBox;
    }

    public void timeUpdate(LocalDateTime alarmDatetime) {
        this.alarmDatetime = alarmDatetime;
    }

    public void eatUpdate(Boolean eatCheck) {
        this.eatCheck = eatCheck;
    }

    public void alarmUpdate(Boolean alarmCheck) {
        this.alarmCheck = alarmCheck;
    }

    public void calendarUpdate(LocalDateTime alarmDatetime, Boolean eatCheck) {
        this.alarmDatetime = alarmDatetime;
        this.eatCheck = eatCheck;
    }
}
