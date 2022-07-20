package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxRequestDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxSaveRequestDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxUpdateRequestDto;
import mediSsok.mediSsokspring.dto.schedule.*;
import mediSsok.mediSsokspring.service.MedicineBoxService;
import mediSsok.mediSsokspring.service.MemberService;
import mediSsok.mediSsokspring.service.ScheduleDateService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class ScheduleDateController {
    private final MedicineBoxService medicineBoxService;
    private final ScheduleDateService scheduleDateService;

    // 오늘 알람
    @GetMapping("/medi/bell")
    public String dispBell(Model model, @AuthenticationPrincipal CustomUserDetails userDetails,
                           @RequestParam(defaultValue="0") int year, @RequestParam(defaultValue="0") int month, @RequestParam(defaultValue="0") int day) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime fromDate;
        LocalDateTime toDate;

        if(year == 0 || month == 0 || day == 0){
            fromDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0,0, 0);
            toDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23,59, 0);
        }
        else {
            fromDate = LocalDateTime.of(year, month, day, 0,0, 0);
            toDate = LocalDateTime.of(year, month, day, 23,59, 0);
        }


        List<MedicineBoxResponseDto> mediboxList = medicineBoxService.findByMemberId(userDetails.getMember().getId());
        List<DateInfoResponseDto> alarmList = scheduleDateService.alarmList(userDetails.getMember().getId(), fromDate, toDate);

        // 맴버이름, 약통종류, 알람리스트
        model.addAttribute("member", userDetails.getMember().getNickname());
        model.addAttribute("mediBoxs", mediboxList);
        model.addAttribute("alarm", alarmList);

        return "/Medi_bell/mediBell";
    }

    // 스케줄 추가
    @PostMapping("/api/medi/schedule/add")
    @ResponseBody
    public Long scheduleSave(@RequestBody ScheduleSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        requestDto.setMemberId(memberId);
        return scheduleDateService.scheduleCreate(requestDto, memberId);
    }

    // 스케줄 조회
    @PostMapping("/api/medi/schedule/get")
    @ResponseBody
    public ScheduleResponseDto findById(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleDateService.scheduleFindById(requestDto.getScheduleId());
    }

    // 스케줄 수정
    @PostMapping("/api/medi/schedule/update/{id}")
    @ResponseBody
    public Long scheduleUpdate(@PathVariable Long id, @RequestBody ScheduleUpdateRequestDto requestDto){
        return scheduleDateService.scheduleUpdate(id, requestDto);
    }

    // 스케줄 삭제
    @DeleteMapping("/api/medi/schedule/delete/{id}")
    @ResponseBody
    public Long scheduleDelete(@PathVariable Long id){
        scheduleDateService.scheduleDelete(id);
        return id;
    }

    // 알람 수정
    @PostMapping("/api/medi/alarm/update/{id}")
    @ResponseBody
    public Long alarmUpdate(@PathVariable Long id, @RequestBody DateInfoUpdateRequestDto requestDto){
        return scheduleDateService.alarmUpdate(id, requestDto);
    }

    // 알람 삭제
    @DeleteMapping("/api/medi/alarm/delete/{id}")
    @ResponseBody
    public Long alarmDelete(@PathVariable Long id){
        scheduleDateService.alarmDelete(id);
        return id;
    }

    // 알람 여부 변경
    @PostMapping("/api/medi/alarm/check/{id}")
    @ResponseBody
    public Long alarmCheck(@PathVariable Long id, @RequestBody AlarmCheckRequestDto requestDto){
        return scheduleDateService.alarmCheck(id, requestDto);
    }

    // 복용 여부 변경
    @PostMapping("/api/medi/eat/check/{id}")
    @ResponseBody
    public Long eatCheck(@PathVariable Long id, @RequestBody EatCheckRequestDto requestDto){
        return scheduleDateService.eatCheck(id, requestDto);
    }
}
