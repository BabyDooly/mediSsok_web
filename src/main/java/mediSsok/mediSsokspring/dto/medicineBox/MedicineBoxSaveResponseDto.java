package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicineBoxSaveResponseDto {
    private String name;
    private String memo;
    private String color;
    private int count;

    private List<MedicineList> medicineLists;
    private Member member;

    public MedicineBox toEntity(){
        return MedicineBox.builder()
                .name(name)
                .color(color)
                .memo(memo)
                .count(count)
                .member(member)
                .medicineLists(medicineLists)
                .build();
    }
}