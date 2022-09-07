package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxSaveRequestDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxUpdateRequestDto;
import mediSsok.mediSsokspring.dto.medicines.MedicinesResponseDto;
import mediSsok.mediSsokspring.dto.medicines.MedicinesSearchRequestDto;
import mediSsok.mediSsokspring.dto.member.MemberResponseDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleResponseDto;
import mediSsok.mediSsokspring.service.MedicineBoxService;
import mediSsok.mediSsokspring.service.MedicinesService;
import mediSsok.mediSsokspring.service.MemberService;
import mediSsok.mediSsokspring.service.ScheduleDateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MedicinesController {
    private final MedicinesService medicinesService;

    /*---- 알약 검색 ----*/

    // 약 검색 페이지(GET)
    @GetMapping("/medi/search")
    public String dispSearch(Model model) {

        MedicinesSearchRequestDto dto = new MedicinesSearchRequestDto();

        // 리스트
        List<MedicinesResponseDto> list = medicinesService.findByMedicines(dto);

        // 약 리스트
        model.addAttribute("medicines", list);

        return "/Medi_search/mediSearch";
    }
}
