package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediSsok.mediSsokspring.dto.medicines.MedicinesInfoResponseDto;
import mediSsok.mediSsokspring.dto.medicines.MedicinesResponseDto;
import mediSsok.mediSsokspring.dto.medicines.MedicinesSearchRequestDto;
import mediSsok.mediSsokspring.service.Crawling;
import mediSsok.mediSsokspring.service.MedicinesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        return "Medi_search/mediSearch";
    }

    @GetMapping("/medi/searchDetail")
    public String searchDetail(@PageableDefault(size = 20) Pageable pageable, Model model, MedicinesSearchRequestDto dto) {
        // 리스트 생성
        Page<MedicinesResponseDto> list = medicinesService.findByMedicines(dto, pageable);
        long size = list.getTotalElements();
        int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
        int endPage = Math.min(list.getPageable().getPageNumber()+4, list.getTotalPages());

        // 약 리스트
        model.addAttribute("size", size);
        model.addAttribute("medicines", list);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "Medi_search/mediSearch";
    }

    @GetMapping("/medi/info")
    public String mediINfo(@RequestParam(defaultValue="0") Long id, Model model){
        MedicinesResponseDto medicine = medicinesService.findById(id);
        
        // 크롤링 데이터
        Crawling crawling = new Crawling();
        MedicinesInfoResponseDto dto = crawling.process(id);

        model.addAttribute("medicine", medicine);
        model.addAttribute("crawling", dto);
        return "Medi_search/mediInfo";
    }
}
