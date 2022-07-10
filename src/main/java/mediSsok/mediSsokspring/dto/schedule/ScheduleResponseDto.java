package mediSsok.mediSsokspring.dto.schedule;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {
    private long id;
    private Date date;
    private String startday;
    private String time;
    private String cycle;
    private Long memberId;
    private Long medicineBoxId;

    public ScheduleResponseDto(ScheduleDate entity) {
        this.id = entity.getId();
        this.date = entity.getStartday();
        this.startday = entity.getStartday().toString().substring(0,10);
        this.time = setTime(entity.getStartday().toString().substring(11,16));
        this.cycle = cycleCheck(entity.getCycle(),entity.getWeek());
        this.memberId = entity.getMember().getId();
        this.medicineBoxId = entity.getMedicineBox().getId();
    }

    public String setTime(String time) {
        String hours = time.substring(0,2);
        String minutes = time.substring(3,5);
        int int_hours = Integer.parseInt(hours);

        String result = "";
        if (int_hours >= 12){
            result += "오후";
            int_hours -= 12;

            if (int_hours == 0)
                int_hours = 12;
        }
        else {
            result += "오전";
            if (int_hours == 0)
                int_hours = 12;
        }

        return result + " " + int_hours + ":" + minutes;
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