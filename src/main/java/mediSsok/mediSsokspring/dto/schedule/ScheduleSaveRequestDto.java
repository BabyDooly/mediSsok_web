package mediSsok.mediSsokspring.dto.schedule;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class
ScheduleSaveRequestDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startday;
    private int cycle;
    private int week;
    private Long memberId;
    private Long medicineBoxId;

    public ScheduleDate toEntity(){
        return ScheduleDate.builder()
                .startday(startday)
                .cycle(cycle)
                .week(week)
                .member(Member.builder().id(memberId).build())
                .medicineBox(MedicineBox.builder().id(medicineBoxId).build())
                .build();
    }
}