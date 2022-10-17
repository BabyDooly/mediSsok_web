package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.member.LinkInfo;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.repository.medicineBox.MedicineBoxRepository;
import mediSsok.mediSsokspring.domain.repository.medicineBox.MedicineListRepository;
import mediSsok.mediSsokspring.dto.medicineBox.*;
import mediSsok.mediSsokspring.dto.member.LinkInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MedicineBoxService {
    private final MedicineBoxRepository medicineBoxRepository;

    private final MedicineListRepository medicineListRepository;


    /*---- 약통 ----*/
    // 약통 리스트(페이징o)
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

    // 약통 생성
    @Transactional
    public Long mediBoxCreate(MedicineBoxSaveRequestDto requestDto) {
        Long boxId = medicineBoxRepository.save(requestDto.toEntity()).getId();

        MedicineBox entity = medicineBoxRepository.findById(boxId)
                .orElseThrow(() -> new IllegalArgumentException("해당 약통이 없습니다. id = " + boxId));

        List<String> listDto = requestDto.getMedicineLists();

        for (String list: listDto) {
            MedicineList dto = MedicineList.builder()
                    .name(list)
                    .medicineBox(entity)
                    .build();

            medicineListRepository.save(dto);
        }

        return boxId;
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

        List<MedicineList> medicineList = medicineListRepository.findByMedicineBox_Id(id);


        for (MedicineList list: medicineList) {
            medicineListRepository.delete(list);
        }

        medicineBox.update(requestDto.getName(), requestDto.getMemo(), requestDto.getColor(), requestDto.getCount());

        List<String> listDto = requestDto.getMedicineLists();

        for (String list: listDto) {
            MedicineList dto = MedicineList.builder()
                    .name(list)
                    .medicineBox(medicineBox)
                    .build();

            medicineListRepository.save(dto);
        }

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
}
