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
    public String dispBell(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime fromDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0,0, 0);
        System.out.println(fromDate);
        LocalDateTime toDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23,59, 0);
        System.out.println(toDate);

        List<MedicineBoxResponseDto> mediboxList = medicineBoxService.findByMemberId(userDetails.getMember().getId());
        List<DateInfoResponseDto> alarmList = scheduleDateService.findByMemberIdAndAlarmDatetimeBetween(userDetails.getMember().getId(), fromDate, toDate);

        // 맴버이름, 약통종류, 알람리스트
        model.addAttribute("member", userDetails.getMember().getNickname());
        model.addAttribute("mediBoxs", mediboxList);
        model.addAttribute("alarm", alarmList);

        return "/Medi_bell/mediBell";
    }

    // 스케줄 추가
    @PostMapping("/api/medi/bell/add")
    @ResponseBody
    public Long save(@RequestBody ScheduleSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMember().getId();
        requestDto.setMemberId(memberId);
        return scheduleDateService.create(requestDto, memberId);
    }

    // 스케줄 조회
    @PostMapping("/api/medi/schedule/get")
    @ResponseBody
    public ScheduleResponseDto findById(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleDateService.findById(requestDto.getScheduleId());
    }

    // 스케줄 수정
    @PostMapping("/api/medi/schedule/update/{id}")
    @ResponseBody
    public Long update(@PathVariable Long id, @RequestBody ScheduleUpdateRequestDto scheduleUpdateRequestDto){
        return scheduleDateService.update(id, scheduleUpdateRequestDto);
    }

    // 스케줄 삭제
    @DeleteMapping("/api/medi/schedule/delete/{id}")
    @ResponseBody
    public Long delete(@PathVariable Long id){
        scheduleDateService.delete(id);
        return id;
    }
}
