package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxUpdateRequestDto;
import mediSsok.mediSsokspring.dto.schedule.*;
import mediSsok.mediSsokspring.service.MedicineBoxService;
import mediSsok.mediSsokspring.service.ScheduleDateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class CalendarController {
    private final ScheduleDateService scheduleDateService;

    /*---- 스케줄 ----*/

    // 캘린더
    @GetMapping("/status")
    public String dispStatus() {
        return "/Medi_status/mediStat";
    }

    // 캘린더 리스트 전송(GET)
    @GetMapping("/api/status/list")
    @ResponseBody
    public List<CalendarResponseDto> calendarList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return scheduleDateService.calendarList(userDetails.getMember().getId());
    }

    // 캘린더 일정 수정(POST)
    @PostMapping("/api/status/update/{id}")
    @ResponseBody
    public Long calendarUpdate(@PathVariable Long id, @RequestBody CalendarUpdateRequestDto requestDto){
        return scheduleDateService.calendarUpdate(id, requestDto);
    }
}
