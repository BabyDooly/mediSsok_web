package mediSsok.mediSsokspring.domain.entity.schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
@Getter
@Entity
@Table(name = "date_info")
public class DateInfo extends BaseTimeEntity {
    @Id
    @Column(name = "date_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    // 일정 날짜,시간
    @Column(name = "date_alarm_datetime")
    private LocalDateTime alarmDatetime;

    // 복용 여부
    @Column(name = "date_eatCheck")
    private Boolean eatCheck = true;

    // 알람 여부
    @Column(name = "date_alarmCheck")
    private Boolean alarmCheck = true;

    // 사용자 ID(Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 스케줄 ID(ScheduleDate)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sche_id")
    private ScheduleDate scheduleDate;

    @Builder
    public DateInfo(LocalDateTime alarmDatetime, Member member, ScheduleDate scheduleDate) {
        this.alarmDatetime = alarmDatetime;
        this.member = member;
        this.scheduleDate = scheduleDate;
    }

    public void update(LocalDateTime alarmDatetime) {
        this.alarmDatetime = alarmDatetime;
    }
}
