package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.member.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MedicineListSaveResponseDto {
    private String name;

    private Long medicineBox;

    public MedicineList toEntity(){
        return MedicineList.builder()
                .name(name)
                .medicineBox(MedicineBox.builder().id(medicineBox).build())
                .build();
    }
}