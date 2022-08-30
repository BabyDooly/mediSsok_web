package mediSsok.mediSsokspring.dto.schedule;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    private String timeUpdate;
    private int cycle;
    private String cycleUpdate;
    private int week;
    private Long memberId;
    private Long medicineBoxId;
    private String medicineBoxName;

    public ScheduleResponseDto(ScheduleDate entity) {
        this.id = entity.getId();
        this.dateTime = entity.getStartday();
        this.timeUpdate = setTime(entity.getStartday());
        this.cycle = entity.getCycle();
        this.cycleUpdate = cycleCheck(entity.getCycle(),entity.getWeek());
        this.week = entity.getWeek();
        this.memberId = entity.getMember().getId();
        this.medicineBoxId = entity.getMedicineBox().getId();
        this.medicineBoxName = entity.getMedicineBox().getName();
    }

    public static String setTime(LocalDateTime time) {
        int hours = time.getHour();
        int minutes = time.getMinute();

        String result = "";
        if (hours >= 12){
            result += "오후";
            hours -= 12;

            if (hours == 0)
                hours = 12;
        }
        else {
            result += "오전";
            if (hours == 0)
                hours = 12;
        }

        String H = String.format("%02d", hours);
        String M = String.format("%02d", minutes);

        return result + " " + H + ":" + M;
    }

    public String cycleCheck(int cycle, int week) {
        if (cycle == 1)
            return "매일";
        else if (cycle == 0){
            String str_week = "";
            if (((week>>0)&1) == 1)
                str_week += "월 ";
            if (((week>>1)&1) == 1)
                str_week += "화 ";
            if (((week>>2)&1) == 1)
                str_week += "수 ";
            if (((week>>3)&1) == 1)
                str_week += "목 ";
            if (((week>>4)&1) == 1)
                str_week += "금 ";
            if (((week>>5)&1) == 1)
                str_week += "토 ";
            if (((week>>6)&1) == 1)
                str_week += "일 ";
            return str_week;
        }
        else {
            String str = cycle + "일에 한번";
            return str;
        }
    }
}