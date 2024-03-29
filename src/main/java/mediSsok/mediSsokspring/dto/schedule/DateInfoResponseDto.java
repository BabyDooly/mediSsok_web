package mediSsok.mediSsokspring.dto.schedule;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.schedule.DateInfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static mediSsok.mediSsokspring.dto.schedule.ScheduleResponseDto.setTime;

@Getter
@Setter
@NoArgsConstructor
public class DateInfoResponseDto {
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alarmDatetime;
    private String timeUpdate;
    private Boolean eatCheck;
    private Boolean alarmCheck;
    private Long memberId;
    private Long scheduleDateId;
    private String medicineBoxName;
    private Boolean replenishAlarms;
    private Boolean pushAlarms;
    private LocalTime workAlarms;
    private int medCount;

    public DateInfoResponseDto(DateInfo entity) {
        this.id = entity.getId();
        this.alarmDatetime = entity.getAlarmDatetime();
        this.timeUpdate = setTime(entity.getAlarmDatetime());
        this.eatCheck = entity.getEatCheck();
        this.alarmCheck = entity.getAlarmCheck();
        this.memberId = entity.getMember().getId();
        this.scheduleDateId = entity.getScheduleDate().getId();
        this.medicineBoxName = entity.getScheduleDate().getMedicineBox().getName();
        this.replenishAlarms = entity.getMember().getReplenishAlarms();
        this.pushAlarms = entity.getMember().getPushAlarms();
        this.workAlarms = entity.getMember().getWorkAlarms();
        this.medCount = entity.getMedicineBox().getCount();
    }

    @Builder
    public DateInfoResponseDto(Long id) {
        this.id = id;
    }
}