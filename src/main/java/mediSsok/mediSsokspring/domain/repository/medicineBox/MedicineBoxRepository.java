package mediSsok.mediSsokspring.domain.repository.medicineBox;

import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineBoxRepository extends JpaRepository<MedicineBox, Long> {

    List<MedicineBox> findByMemberId(long userId);

}