
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

import java.time.DayOfWeek;
import java.time.LocalDateTime;
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
    public Long scheduleCreate(ScheduleSaveRequestDto responseDto, Long memberId) {
        Long scheduleDateId = scheduleDateRepository.save(responseDto.toEntity()).getId();

        int cycle = responseDto.getCycle();
        int checkCycle = cycle;
        int week = responseDto.getWeek();

        for (int i = 0; i < 7; i++) {
            // 날짜 받아오기
            LocalDateTime date = responseDto.getStartday();
            date = date.plusDays(i);

            // 요일 계산
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            int weekNumber = dayOfWeek.getValue();

            // DateInfoSaveDTO 생성
            DateInfo dto = DateInfo.builder()
                    .alarmDatetime(date)
                    .member(Member.builder().id(memberId).build())
                    .scheduleDate(ScheduleDate.builder().id(scheduleDateId).build())
                    .build();

            // 특정요일
            if (cycle == 0){
                if ((((week>>0)&1) == 1) && weekNumber == 1)
                    dateInfoRepository.save(dto);
                if ((((week>>1)&1) == 1) && weekNumber == 2)
                    dateInfoRepository.save(dto);
                if ((((week>>2)&1) == 1) && weekNumber == 3)
                    dateInfoRepository.save(dto);
                if ((((week>>3)&1) == 1) && weekNumber == 4)
                    dateInfoRepository.save(dto);
                if ((((week>>4)&1) == 1) && weekNumber == 5)
                    dateInfoRepository.save(dto);
                if ((((week>>5)&1) == 1) && weekNumber == 6)
                    dateInfoRepository.save(dto);
                if ((((week>>6)&1) == 1) && weekNumber == 7)
                    dateInfoRepository.save(dto);
            }
            // 매일
            else if (cycle == 1){
                dateInfoRepository.save(dto);
            }
            // 일간격
            else{
                if (cycle == checkCycle){
                    dateInfoRepository.save(dto);
                }
                checkCycle--;

                if (checkCycle == 0)
                    checkCycle = cycle;
            }
        }

        return scheduleDateId;
    }

    // 약통별 스케줄 리스트
    @Transactional(readOnly=true)
    public List<ScheduleResponseDto> scheduleList (Long id){
        return scheduleDateRepository.findByMedicineBoxId(id, Sort.by(Sort.Direction.ASC, "startday")).stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    // 스케줄 아이디 조회
    @Transactional
    public ScheduleResponseDto scheduleFindById (Long id){
        ScheduleDate entity = scheduleDateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("맴버 약통에 대한 스케줄이 없습니다. id = " + id));

        return new ScheduleResponseDto(entity);
    }

    // 스케줄 수정
    @Transactional
    public Long scheduleUpdate(Long id, ScheduleUpdateRequestDto requestDto){
        ScheduleDate entity = scheduleDateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("스케줄이 없습니다. id = " + id));

        entity.update(requestDto.getStartday(), requestDto.getCycle(), requestDto.getWeek());
        return id;
    }

    // 스케줄 삭제
    @Transactional
    public void scheduleDelete(Long id){
        ScheduleDate entity = scheduleDateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("스케줄이 없습니다. id = " + id));

        scheduleDateRepository.delete(entity);
    }

    // 날짜별 알람 리스트
    @Transactional(readOnly=true)
    public List<DateInfoResponseDto> alarmList (Long memberId, LocalDateTime fromDate, LocalDateTime toDate){
        return dateInfoRepository.findByMemberIdAndAlarmDatetimeBetween(memberId, fromDate, toDate, Sort.by(Sort.Direction.ASC, "alarmDatetime")).stream()
                .map(DateInfoResponseDto::new)
                .collect(Collectors.toList());
    }

    // 알람 아이디 조회
    @Transactional
    public DateInfoResponseDto alarmFindById (Long id){
        DateInfo entity = dateInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("맴버 약통에 대한 스케줄이 없습니다. id = " + id));

        return new DateInfoResponseDto(entity);
    }

    // 알람 수정
    @Transactional
    public Long alarmUpdate(Long id, DateInfoUpdateRequestDto requestDto){
        DateInfo entity = dateInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("알람이 없습니다. id = " + id));

        entity.timeUpdate(requestDto.getAlarmDatetime());
        return id;
    }

    // 알람 삭제
    @Transactional
    public void alarmDelete(Long id){
        DateInfo entity = dateInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("알람이 없습니다. id = " + id));

        dateInfoRepository.delete(entity);
    }
    
    //  복용 여부 변경
    @Transactional
    public Long eatCheck(Long id, EatCheckRequestDto requestDto){
        DateInfo entity = dateInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("알람이 없습니다. id = " + id));

        entity.eatUpdate(requestDto.getEatCheck());
        return id;
    }

    // 알람 여부 변경
    @Transactional
    public Long alarmCheck(Long id, AlarmCheckRequestDto requestDto){
        DateInfo entity = dateInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("알람이 없습니다. id = " + id));

        entity.alarmUpdate(requestDto.getAlarmCheck());
        return id;
    }
}
