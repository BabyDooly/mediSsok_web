package mediSsok.mediSsokspring.dto.schedule;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {
    private long id;
    private Date startday;
    private int cycle;
    private int week;
    private Long memberId;
    private Long medicineBoxId;

    public ScheduleResponseDto(ScheduleDate entity) {
        this.id = entity.getId();
        this.startday = entity.getStartday();
        this.cycle = entity.getCycle();
        this.week = entity.getWeek();
        this.memberId = entity.getMember().getId();
        this.medicineBoxId = entity.getMedicineBox().getId();
    }
}