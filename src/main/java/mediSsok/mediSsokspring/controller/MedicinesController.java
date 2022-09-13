package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediSsok.mediSsokspring.dto.medicines.MedicinesResponseDto;
import mediSsok.mediSsokspring.dto.medicines.MedicinesSearchRequestDto;
import mediSsok.mediSsokspring.service.MedicinesService;
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
    public String dispSearch() {
        return "/Medi_search/mediSearch";
    }

    @GetMapping("/medi/searchDetail")
    public String Search(Model model, MedicinesSearchRequestDto dto) {

        // 리스트 생성
        List<MedicinesResponseDto> list = medicinesService.findByMedicines(dto);
        int size = list.size();


        // 약 리스트
        model.addAttribute("size", size);
        model.addAttribute("medicines", list);

        return "/Medi_search/mediSearch";
    }
}
