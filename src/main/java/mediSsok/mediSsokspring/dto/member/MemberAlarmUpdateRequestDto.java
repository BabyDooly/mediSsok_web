package mediSsok.mediSsokspring.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberAlarmUpdateRequestDto {
    private Boolean vibration;
    private Boolean pushAlarms;
    private Boolean locationAlarms;
    private Boolean replenishAlarms;

    @Builder
    public MemberAlarmUpdateRequestDto(Boolean vibration, Boolean pushAlarms, Boolean locationAlarms, Boolean replenishAlarms) {
        this.vibration = vibration;
        this.pushAlarms = pushAlarms;
        this.locationAlarms = locationAlarms;
        this.replenishAlarms = replenishAlarms;
    }
}
