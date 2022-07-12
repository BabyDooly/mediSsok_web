package mediSsok.mediSsokspring.dto.schedule;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.entity.schedule.DateInfo;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DateInfoSaveRequestDto {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date alarmDatetime;
    private Long memberId;
    private Long scheduleDateId;

    public DateInfo toEntity(){
        return DateInfo.builder()
                .alarmDatetime(alarmDatetime)
                .member(Member.builder().id(memberId).build())
                .scheduleDate(ScheduleDate.builder().id(scheduleDateId).build())
                .build();
    }
}