package mediSsok.mediSsokspring.dto.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
public class MemberAlarmUpdateRequestDto {
    private Boolean vibration;
    private Boolean pushAlarms;
    private Boolean locationAlarms;
    private Boolean replenishAlarms;
    @JsonFormat(pattern="HH:mm")
    private LocalTime workAlarms;
}
