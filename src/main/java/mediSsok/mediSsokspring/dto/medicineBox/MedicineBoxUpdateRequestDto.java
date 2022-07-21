package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MedicineBoxUpdateRequestDto {
    private String name;
    private String memo;
    private String color;
    private int count;
    private List<MedicineList> medicineLists;
}