package mediSsok.mediSsokspring.domain.repository.schedule;

import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.schedule.DateInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DateInfoRepository extends JpaRepository<DateInfo, Long> {
    List<DateInfo> findByMemberIdAndAlarmDatetimeBetween(Long memberId, LocalDateTime fromDate, LocalDateTime toDate, Sort sort);

    List<DateInfo> findByMemberIdAndAlarmCheckAndAlarmDatetimeGreaterThanEqual(Long memberId, Boolean alarmCheck, LocalDateTime date, Sort sort);

    List<DateInfo> findByMemberId(Long userid);
}