package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.repository.medicineBox.MedicineBoxRepository;
import mediSsok.mediSsokspring.domain.repository.medicineBox.MedicineListRepository;
import mediSsok.mediSsokspring.dto.medicineBox.*;
import org.springframework.data.domain.Page;
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
    // 생성
    @Transactional
    public Long mediBoxCreate(MedicineBoxSaveRequestDto requestDto) {
        return medicineBoxRepository.save(requestDto.toEntity()).getId();
    }

    // 약통 리스트
    @Transactional(readOnly=true)
    public Page<MedicineBoxResponseDto> findByMemberId(Long memberId, Pageable pageable){
        return medicineBoxRepository.findByMemberId(memberId, pageable);
    }

    // 약통 리스트(페이징x)
    @Transactional(readOnly=true)
    public List<MedicineBoxResponseDto> findByMemberId (Long id){
        return medicineBoxRepository.findByMemberId(id).stream()
                .map(MedicineBoxResponseDto::new)
                .collect(Collectors.toList());
    }

    // 약통 조회
    public MedicineBoxResponseDto findById (Long id){
        MedicineBox entity = medicineBoxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 약통이 없습니다. id = " + id));

        return new MedicineBoxResponseDto(entity);
    }

    // 약통 수정
    @Transactional
    public Long mediBoxUpdate(Long id, MedicineBoxUpdateRequestDto requestDto){
        MedicineBox medicineBox = medicineBoxRepository.findById(id)
                // 아이디가 없을때
                .orElseThrow(() -> new IllegalArgumentException("해당 약통이 없습니다. id = " + id));

        medicineBox.update(requestDto.getName(), requestDto.getMemo(), requestDto.getColor(), requestDto.getCount());

        return id;
    }

    // 약통 삭제
    @Transactional
    public void mediBoxDelete(Long id){
        MedicineBox entity = medicineBoxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 약통이 없습니다. id = " + id));

        medicineBoxRepository.delete(entity);
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
