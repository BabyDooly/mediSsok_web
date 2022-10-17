package mediSsok.mediSsokspring.domain.repository.medicineBox;

import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineListRepository extends JpaRepository<MedicineList, Long> {
    List<MedicineList> findByMedicineBox_Id(Long id);
}