package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MedicineListReadResponseDto {
    private String name;

    public MedicineListReadResponseDto(MedicineList entity) {
        this.name = entity.getName();
    }
}