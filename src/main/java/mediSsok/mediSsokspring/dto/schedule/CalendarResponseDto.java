package mediSsok.mediSsokspring.dto.schedule;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.schedule.DateInfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static mediSsok.mediSsokspring.dto.schedule.ScheduleResponseDto.setTime;

@Getter
@Setter
@NoArgsConstructor
public class CalendarResponseDto {
    private long _id;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm")
    private LocalDateTime start;
    private String backgroundColor;
    private String textColor;
    private Boolean allDay;

    // 캘린더 형식
     /*
     "_id": 2,
     "title": "혈압약",
     "description": "계속 챙겨먹는 혈압 약",
     "start": "2022-07-07T09:30",
     "backgroundColor": "#D25565",
     "textColor": "#ffffff",
     "allDay": false
      */

    public CalendarResponseDto(DateInfo entity) {
        this._id = entity.getId();
        this.title = entity.getMedicineBox().getName();
        this.description = entity.getMedicineBox().getMemo();
        this.start = entity.getAlarmDatetime();
        this.backgroundColor = eatChange(entity.getEatCheck());
        this.textColor = "#ffffff";
        this.allDay = false;
    }

    public String eatChange(boolean eatCheck){
        if (eatCheck)
            return "#2bad00";
        else
            return "#D25565";
    }
}