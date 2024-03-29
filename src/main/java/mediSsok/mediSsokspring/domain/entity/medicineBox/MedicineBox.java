package mediSsok.mediSsokspring.domain.entity.medicineBox;

import lombok.*;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.entity.schedule.DateInfo;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
@Getter
@Setter
@Entity
@Table(name = "medicine_box")
public class MedicineBox extends BaseTimeEntity {
    @Id
    @Column(name = "medbox_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    // 약통 이름
    @Column(name = "medbox_name", length = 20, nullable = false)
    private String name;

    // 약통 메모
    @Column(name = "medbox_memo", length = 50, nullable = false)
    private String memo;

    // 약통 색상
    @Column(name = "medbox_color", length = 10, nullable = false)
    private String color;

    // 소지 개수
    @Column(name = "medbox_count", length = 10)
    private int count;

    // 사용자 ID(Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 약 리스트트
    @OneToMany(mappedBy = "medicineBox",  cascade = CascadeType.ALL, orphanRemoval = true)
    List<MedicineList> medicineLists = new ArrayList<>();

    // 스케줄 리스트
    @OneToMany(mappedBy = "medicineBox", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ScheduleDate> ScheduleDates = new ArrayList<>();

    // 세부일정 리스트
    @OneToMany(mappedBy = "medicineBox", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DateInfo> dateInfos = new ArrayList<>();

    @Builder
    public MedicineBox(Long id, String name, String memo, String color, int count, Member member, List<MedicineList> medicineLists) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.color = color;
        this.count = count;
        this.member = member;
        this.medicineLists = medicineLists;
    }

    public void update(String name, String memo, String color, int count) {
        this.name = name;
        this.memo = memo;
        this.color = color;
        this.count = count;
    }
}