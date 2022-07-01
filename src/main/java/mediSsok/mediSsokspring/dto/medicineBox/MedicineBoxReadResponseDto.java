package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.member.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MedicineBoxReadResponseDto {
    private String name;

    private String memo;

    private String color;

    private int count;

    public MedicineBoxReadResponseDto(MedicineBox entity) {
        this.name = entity.getName();
        this.memo = entity.getMemo();
        this.color = entity.getColor();
        this.count = entity.getCount();
    }
}