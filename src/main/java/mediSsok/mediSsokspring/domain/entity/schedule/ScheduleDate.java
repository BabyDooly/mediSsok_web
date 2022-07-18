package mediSsok.mediSsokspring.domain.entity.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
@Getter
@Entity
@Table(name = "schedule_date")
public class ScheduleDate extends BaseTimeEntity {
    @Id
    @Column(name = "sche_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    // 스케줄 시작 날짜
    @Column(name = "sche_startday")
    private LocalDateTime startday;

    // 스케줄 주기 (
    @Column(name = "sche_cycle", length = 20, nullable = false)
    private int cycle;

    // 스케줄 요일
    @Column(name = "sche_week", length = 10, nullable = false)
    private int week;

    // 사용자 ID(Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 약통 ID(MedicineBox)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medbox_id")
    private MedicineBox medicineBox;

    // 일정 리스트
    @OneToMany(mappedBy = "scheduleDate", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DateInfo> dateInfos = new ArrayList<>();


    @Builder
    public ScheduleDate(Long id, LocalDateTime startday, int cycle, int week, Member member, MedicineBox medicineBox) {
        this.id = id;
        this.startday = startday;
        this.cycle = cycle;
        this.week = week;
        this.member = member;
        this.medicineBox = medicineBox;
    }

    public void update(LocalDateTime startday, int cycle, int week) {
        this.startday = startday;
        this.cycle = cycle;
        this.week = week;
    }
}
