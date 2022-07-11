package mediSsok.mediSsokspring.domain.repository.schedule;

import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleDateRepository extends JpaRepository<ScheduleDate, Long> {
    List<ScheduleDate> findByMedicineBoxId(Long medicineBoxID);
}