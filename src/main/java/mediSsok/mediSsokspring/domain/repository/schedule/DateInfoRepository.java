package mediSsok.mediSsokspring.domain.repository.schedule;

import mediSsok.mediSsokspring.domain.entity.schedule.DateInfo;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DateInfoRepository extends JpaRepository<DateInfo, Long> {
}