package mediSsok.mediSsokspring.dto.medicineBox;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.member.Member;

@Getter
@Setter
@NoArgsConstructor
public class MedicineBoxResponseDto {
    private long id;
    private String name;
    private String memo;
    private String color;
    private int count;
    private String email;

    public MedicineBoxResponseDto(MedicineBox entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.memo = entity.getMemo();
        this.color = entity.getColor();
        this.count = entity.getCount();
        this.email = entity.getMember().getEmail();
    }
}