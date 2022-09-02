package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.member.Member;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicineBoxSaveRequestDto {
    private String name;
    private String memo;
    private String color;
    private int count;
    private Long memberId;
    private List<String> medicineLists;

    public MedicineBox toEntity(){
        return MedicineBox.builder()
                .name(name)
                .color(color)
                .memo(memo)
                .count(count)
                .member(Member.builder().id(memberId).build())
                .build();
    }
}