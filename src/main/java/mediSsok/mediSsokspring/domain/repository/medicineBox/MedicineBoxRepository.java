package mediSsok.mediSsokspring.domain.repository.medicineBox;

import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineBoxRepository extends JpaRepository<MedicineBox, Long> {

    Page<MedicineBoxResponseDto> findByMemberId(Long userEmail, Pageable pageable);
    List<MedicineBox> findByMemberId(Long userEmail);

}