package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.repository.medicineBox.MedicineBoxRepository;
import mediSsok.mediSsokspring.domain.repository.medicineBox.MedicineListRepository;
import mediSsok.mediSsokspring.dto.medicineBox.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MedicineBoxService {
    private final MedicineBoxRepository medicineBoxRepository;
    private final MedicineListRepository medicineListRepository;

    /*---- 약통 ----*/
    // 저장
    @Transactional
    public Long save(MedicineBoxSaveResponseDto dto) {
        return medicineBoxRepository.save(dto.toEntity()).getId();
    }

    // 약통 리스트
    @Transactional(readOnly=true)
    public List<MedicineBoxMainResponseDto> findAll(){
        return medicineBoxRepository.findAll()
                .stream()
                .map(MedicineBoxMainResponseDto::new)
                .collect(Collectors.toList());
    }

    // 아이디 찾기
    @Transactional(readOnly=true)
    public MedicineBoxReadResponseDto findById(long num){
        return medicineBoxRepository
                .findById(num)
                .map(MedicineBoxReadResponseDto::new)
                .get();
    }

    // 페이지 카운터
    @Transactional(readOnly = true)
    public int pageCount(Pageable pageable) {
        return medicineBoxRepository.findAll(pageable).getTotalPages();
    }

    /*---- 약리스트 ----*/
    // 저장
//    public Long save(final MedicineListSaveResponseDto dto) {
//        return medicineListRepository.save(dto.toEntity()).getId();
//    }

//    // 약 리스트
//    @Transactional(readOnly = true)
//    public List<MedicineListReadResponseDto> findByid(final long medBoxId) {
//        return medicineListRepository.findAll()
//                .stream()
//                .filter(MedicineList -> (MedicineList.getMedicineBox().getId() == medBoxId))
//                .map(MedicineListReadResponseDto::new).collect(Collectors.toList());
//    }
}
