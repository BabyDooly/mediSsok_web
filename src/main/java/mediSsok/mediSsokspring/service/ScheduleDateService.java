
package mediSsok.mediSsokspring.service;


import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.domain.entity.member.Member;
import mediSsok.mediSsokspring.domain.entity.schedule.DateInfo;
import mediSsok.mediSsokspring.domain.entity.schedule.ScheduleDate;
import mediSsok.mediSsokspring.domain.repository.schedule.DateInfoRepository;
import mediSsok.mediSsokspring.domain.repository.schedule.ScheduleDateRepository;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.schedule.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    //final 필드 생성자 생성
public class ScheduleDateService {
    private final ScheduleDateRepository scheduleDateRepository;

    private final DateInfoRepository dateInfoRepository;

    /*---- 스케줄 ----*/
    // 스케줄 생성
    @Transactional
    public Long create(ScheduleSaveRequestDto responseDto, Long memberId) {
        Long scheduleDateId = scheduleDateRepository.save(responseDto.toEntity()).getId();

//        int cycle = responseDto.getCycle();
//        int week = responseDto.getWeek();
//
//        for (int i = 0; i < 100; i++) {
//            D today = responseDto.getStartday();
//            Calendar cal=Calendar.getInstance();
//            cal.setTime(today);
//
//
//            if (cycle == 0){
//
//            }
//            else if (cycle == 1){
//                cal.add(Calendar.DATE, i);
//                Date date = cal.getTime();
//            }
//            else{
//
//            }
//            cal.setTime(today);
//            cal.add(Calendar.DATE, i);
//            Date date = cal.getTime();
//
//            DateInfo dto = DateInfo.builder()
//                    .alarmDatetime(date)
//                    .member(Member.builder().id(memberId).build())
//                    .scheduleDate(ScheduleDate.builder().id(scheduleDateId).build())
//                    .build();
//            dateInfoRepository.save(dto);
//        }

        return scheduleDateId;
    }

    // 알림 리스트
    @Transactional(readOnly=true)
    public List<ScheduleResponseDto> findByMedicineBoxId (Long id){
        return scheduleDateRepository.findByMedicineBoxId(id, Sort.by(Sort.Direction.ASC, "startday")).stream()
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
