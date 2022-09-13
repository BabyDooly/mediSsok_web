package mediSsok.mediSsokspring.service;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineList;
import mediSsok.mediSsokspring.domain.entity.medicines.Medicines;
import mediSsok.mediSsokspring.domain.repository.medicineBox.MedicineBoxRepository;
import mediSsok.mediSsokspring.domain.repository.medicineBox.MedicineListRepository;
import mediSsok.mediSsokspring.domain.repository.medicines.MedicinesRepository;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxSaveRequestDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxUpdateRequestDto;
import mediSsok.mediSsokspring.dto.medicines.MedicinesResponseDto;
import mediSsok.mediSsokspring.dto.medicines.MedicinesSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MedicinesService {
    private final MedicinesRepository medicinesRepository;

    /*---- 알약 ----*/
    // 알약 리스트
    @Transactional(readOnly=true)
    public List<MedicinesResponseDto> findByMedicines (MedicinesSearchRequestDto dto){
        // 분할선 '없음', 모양 '기타'
        if (dto.getShape().equals("기타") && dto.getLine().equals("없음"))
            return medicinesRepository.searchNotShapeLine(dto.getName(), dto.getCompanyName(), dto.getMark1(), dto.getMark2(),
                            dto.getLine(),dto.getFormulation(), "원형", "타원형", "장방형", "반원형", "삼각형",
                            "사각형", "마름모형", "오각형","육각형", "팔각형", dto.getColor()).stream()
                    .map(MedicinesResponseDto::new)
                    .collect(Collectors.toList());
        // 분할선 '없음'
        else if (dto.getLine().equals("없음"))
            return medicinesRepository.searchNotLine(dto.getName(), dto.getCompanyName(), dto.getMark1(), dto.getMark2(),
                            "분할선", dto.getFormulation(), dto.getShape(), dto.getColor()).stream()
                    .map(MedicinesResponseDto::new)
                    .collect(Collectors.toList());
        // 모양 '기타'
        else if (dto.getShape().equals("기타"))
            return medicinesRepository.searchNotShape(dto.getName(), dto.getCompanyName(), dto.getMark1(), dto.getMark2(),
                            dto.getLine(),dto.getFormulation(), "원형", "타원형", "장방형", "반원형", "삼각형",
                            "사각형", "마름모형", "오각형","육각형", "팔각형", dto.getColor()).stream()
                    .map(MedicinesResponseDto::new)
                    .collect(Collectors.toList());
        else
            return medicinesRepository.searchByMedicines(dto.getName(), dto.getCompanyName(), dto.getMark1(), dto.getMark2(),
                        dto.getLine(), dto.getFormulation(), dto.getShape(), dto.getColor()).stream()
                .map(MedicinesResponseDto::new)
                .collect(Collectors.toList());
    }

//    // 알약 조회
//    public MedicineBoxResponseDto findById (Long id){
//        MedicineBox entity = medicinesRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 약통이 없습니다. id = " + id));
//
//        return new MedicineBoxResponseDto(entity);
//    }
}
