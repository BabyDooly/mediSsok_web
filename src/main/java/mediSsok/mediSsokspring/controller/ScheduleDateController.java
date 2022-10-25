package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.member.LinkInfoResponseDto;
import mediSsok.mediSsokspring.dto.member.MemberResponseDto;
import mediSsok.mediSsokspring.dto.schedule.*;
import mediSsok.mediSsokspring.service.MedicineBoxService;
import mediSsok.mediSsokspring.service.MemberService;
import mediSsok.mediSsokspring.service.ScheduleDateService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class ScheduleDateController {
    private final MemberService memberService;
    private final MedicineBoxService medicineBoxService;
    private final ScheduleDateService scheduleDateService;

    /*---- 스케줄 ----*/

    // 오늘 알람 페이지(GET)
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

        MemberResponseDto memberDto = memberService.findByEmail(userDetails.getUsername());
        List<MedicineBoxResponseDto> mediboxList = medicineBoxService.findByMemberId(userDetails.getMember().getId());
        List<DateInfoResponseDto> alarmList = scheduleDateService.alarmList(userDetails.getMember().getId(), fromDate, toDate);

        // 맴버이름, 약통종류, 알람리스트
        model.addAttribute("member", memberDto.getNickname());
        model.addAttribute("mediBoxs", mediboxList);
        model.addAttribute("alarm", alarmList);

        return "Medi_bell/mediBell";
    }

    // 스케줄 추가(POST)
    @PostMapping("/api/medi/schedule/add")
    @ResponseBody
    public Long scheduleCreate(@RequestBody ScheduleSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        requestDto.setMemberId(memberId);
        return scheduleDateService.scheduleCreate(requestDto, memberId);
    }

    // 스케줄 조회(POST)
    @PostMapping("/api/medi/schedule/get")
    @ResponseBody
    public ScheduleResponseDto scheduleFind(@RequestBody ScheduleSearchRequestDto requestDto) {
        return scheduleDateService.scheduleFindById(requestDto.getScheduleId());
    }

    // 스케줄 수정(POST)
    @PostMapping("/api/medi/schedule/update/{id}")
    @ResponseBody
    public Long scheduleUpdate(@PathVariable Long id, @RequestBody ScheduleUpdateRequestDto requestDto){
        return scheduleDateService.scheduleUpdate(id, requestDto);
    }

    // 스케줄 삭제(DELETE)
    @DeleteMapping("/api/medi/schedule/delete/{id}")
    @ResponseBody
    public Long scheduleDelete(@PathVariable Long id){
        scheduleDateService.scheduleDelete(id);
        return id;
    }

    /*---- 알람 ----*/

    // 알람 조회(POST)
    @PostMapping("/api/medi/alarm/get")
    @ResponseBody
    public DateInfoResponseDto alarmFind(@RequestBody DateInfoSearchRequestDto requestDto) {
        return scheduleDateService.alarmFindById(requestDto.getDateInfoId());
    }

    // 가까운 알람 조회(GET)
    @GetMapping("/api/alarm/get")
    @ResponseBody
    public DateInfoResponseDto findNearAlarm(@AuthenticationPrincipal CustomUserDetails userDetails) {
        LocalDateTime now = LocalDateTime.now();

        System.out.println("현재: " + now);


        // 알람이 없을때
        if(scheduleDateService.alarm(userDetails.getMember().getId(), now) == null){
            DateInfoResponseDto dto = DateInfoResponseDto.builder()
                    .id(0L)
                    .build();
            return dto;
        }
        else{
            return scheduleDateService.alarm(userDetails.getMember().getId(), now);
        }
    }

    // 알람 수정(POST)
    @PostMapping("/api/medi/alarm/update/{id}")
    @ResponseBody
    public Long alarmUpdate(@PathVariable Long id, @RequestBody DateInfoUpdateRequestDto requestDto){
        return scheduleDateService.alarmUpdate(id, requestDto);
    }

    // 알람 삭제(DELETE)
    @DeleteMapping("/api/medi/alarm/delete/{id}")
    @ResponseBody
    public Long alarmDelete(@PathVariable Long id){
        scheduleDateService.alarmDelete(id);
        return id;
    }

    // 알람 여부 변경(POST)
    @PostMapping("/api/medi/alarm/check/{id}")
    @ResponseBody
    public Long alarmCheck(@PathVariable Long id, @RequestBody AlarmCheckRequestDto requestDto){
        return scheduleDateService.alarmCheck(id, requestDto);
    }

    // 복용 여부 변경(POST)
    @PostMapping("/api/medi/eat/check/{id}")
    @ResponseBody
    public Long eatCheck(@PathVariable Long id, @RequestBody EatCheckRequestDto requestDto){
        return scheduleDateService.eatCheck(id, requestDto);
    }
}
