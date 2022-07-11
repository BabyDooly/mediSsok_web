
package mediSsok.mediSsokspring.service;


import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;
import mediSsok.mediSsokspring.domain.repository.schedule.ScheduleDateRepository;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleRequestDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleResponseDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleSaveRequestDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleUpdateRequestDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    //final 필드 생성자 생성
public class ScheduleDateService {
    private final ScheduleDateRepository scheduleDateRepository;

    /*---- 스케줄 ----*/
    // 스케줄 생성
    @Transactional
    public Long create(ScheduleSaveRequestDto responseDto) {
        return scheduleDateRepository.save(responseDto.toEntity()).getId();
    }

    // 알림 리스트
    @Transactional(readOnly=true)
    public List<ScheduleResponseDto> findByMedicineBoxId (Long id){
        return scheduleDateRepository.findByMedicineBoxId(id).stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    // 스케줄 전체 조회
    @Transactional
    public List<ScheduleResponseDto> findAll (){
        return scheduleDateRepository.findAll(Sort.by(Sort.Direction.ASC, "startday")).stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    // 스케줄 아이디 조회
    @Transactional
    public ScheduleResponseDto findById (Long id){
        ScheduleDate entity = scheduleDateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("맴버 약통에 대한 스케줄이 없습니다. id = " + id));

        return new ScheduleResponseDto(entity);
    }

    // 스케줄 수정
    @Transactional
    public Long update(Long id, ScheduleUpdateRequestDto requestDto){
        ScheduleDate entity = scheduleDateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("스케줄이 없습니다. id = " + id));

        entity.update(requestDto.getStartday(), requestDto.getCycle(), requestDto.getWeek());

        return id;
    }

    // 스케줄 삭제
    @Transactional
    public void delete(Long id){
        ScheduleDate entity = scheduleDateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("스케줄이 없습니다. id = " + id));

        scheduleDateRepository.delete(entity);
    }
}
