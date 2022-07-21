package mediSsok.mediSsokspring.dto.schedule;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleUpdateRequestDto {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime startday;
    private int cycle;
    private int week;
}