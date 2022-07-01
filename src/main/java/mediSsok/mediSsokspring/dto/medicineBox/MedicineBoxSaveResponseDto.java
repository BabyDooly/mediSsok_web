package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.*;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.member.Member;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MedicineBoxSaveResponseDto {
    private String name;
    private String memo;
    private String color;
    private int count;
    private String member;

    public MedicineBox toEntity(){
        return MedicineBox.builder()
                .name(name)
                .color(color)
                .memo(memo)
                .count(count)
                .member(Member.builder().email(member).build())
                .build();
    }
}