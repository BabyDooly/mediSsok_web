package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicineBoxResponseDto {
    private long id;
    private String name;
    private String memo;
    private String color;
    private int count;
    private Long memberId;

    private List<MedicineList> medicineLists;

    public MedicineBoxResponseDto(MedicineBox entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.memo = entity.getMemo();
        this.color = entity.getColor();
        this.count = entity.getCount();
        this.memberId = entity.getMember().getId();
    }
}