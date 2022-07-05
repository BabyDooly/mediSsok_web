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
public class MedicineBoxUpdateResponseDto {
    private String name;
    private String memo;
    private String color;
    private int count;
    public MedicineBox toEntity(){
        return MedicineBox.builder()
                .name(name)
                .color(color)
                .memo(memo)
                .count(count)
                .build();
    }
}