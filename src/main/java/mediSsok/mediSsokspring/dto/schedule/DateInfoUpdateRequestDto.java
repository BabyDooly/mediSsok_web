package mediSsok.mediSsokspring.dto.schedule;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mediSsok.mediSsokspring.domain.entity.schedule.DateInfo;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DateInfoUpdateRequestDto {
    @JsonFormat(pattern="HH:mm")
    private LocalDateTime alarmDatetime;


    public DateInfo toEntity(){
        return DateInfo.builder()
                .alarmDatetime(alarmDatetime)
                .build();
    }
}