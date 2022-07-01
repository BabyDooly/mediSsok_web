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
public class MedicineBoxMainResponseDto {
    private String name;
    private String color;
    private long member;

    public MedicineBoxMainResponseDto(MedicineBox entity) {
        this.name = entity.getName();
        this.color = entity.getColor();
        this.member = entity.getMember().getId();
    }
}