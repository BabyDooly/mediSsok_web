package mediSsok.mediSsokspring.domain.entity.medicines;

import lombok.*;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
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
@Table(name = "medicines")
public class Medicines {
    @Id
    @Column(name = "med_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    // 제품명
    @Column(name = "med_name", length = 50)
    private String name;

    // 제조사
    @Column(name = "med_company_name", length = 50)
    private String companyName;

    // 이미지
    @Column(name = "med_image_url", length = 100)
    private String imageUrl;

    // 약 표시(앞)
    @Column(name = "med_mark_front", length = 20)
    private String markFront;

    // 약 표시(뒤)
    @Column(name = "med_mark_back", length = 20)
    private String markBack;

    // 제형
    @Column(name = "med_formulation", length = 20)
    private String formulation;

    // 모양
    @Column(name = "med_shape", length = 20)
    private String shape;

    // 색상
    @Column(name = "med_color", length = 20)
    private String color;

    // 분류명
    @Column(name = "med_code_name", length = 30)
    private String codeName;

    // 전문|일반 구분
    @Column(name = "med_SB", length = 20)
    private String SB;
}