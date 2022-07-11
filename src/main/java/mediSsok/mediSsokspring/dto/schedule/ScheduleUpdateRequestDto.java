package mediSsok.mediSsokspring.dto.schedule;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleUpdateRequestDto {
    private Date startday;
    private int cycle;
    private int week;

    public ScheduleDate toEntity(){
        return ScheduleDate.builder()
                .startday(startday)
                .cycle(cycle)
                .week(week)
                .build();
    }
}