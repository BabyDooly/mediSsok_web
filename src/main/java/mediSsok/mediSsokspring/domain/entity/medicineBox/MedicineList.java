package mediSsok.mediSsokspring.domain.entity.medicineBox;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediSsok.mediSsokspring.domain.BaseTimeEntity;
import mediSsok.mediSsokspring.domain.entity.member.Member;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 추가
@Getter
@Entity
@Table(name = "medicine_list")
public class MedicineList extends BaseTimeEntity {
    @Id
    @Column(name = "medlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    // 약 이름
    @Column(name = "medlist_name", length = 20, nullable = false)
    private String name;

    // 약통 ID(MedicineBox)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medbox_id", nullable = false)
    private MedicineBox medicineBox;

    @Builder
    public MedicineList(Long id, String name, MedicineBox medicineBox) {
        this.id = id;
        this.name = name;
        this.medicineBox = medicineBox;
    }
}