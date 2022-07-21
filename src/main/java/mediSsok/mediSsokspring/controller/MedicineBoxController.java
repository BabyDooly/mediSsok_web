package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.medicineBox.*;
import mediSsok.mediSsokspring.dto.schedule.ScheduleResponseDto;
import mediSsok.mediSsokspring.service.MedicineBoxService;
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
public class MedicineBoxController {
    private final MedicineBoxService medicineBoxService;

    private final ScheduleDateService scheduleDateService;

    /*---- 약통 ----*/

    // 내 약통 페이지(GET)
    @GetMapping("/medi/medibox")
    public String dispMediBox(Model model, @AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        // 페이징 리스트
        Page<MedicineBoxResponseDto> pageList = medicineBoxService.findByMemberId(userDetails.getMember().getId(), pageable);

        // 약통, 맴버이름
        model.addAttribute("member", userDetails.getMember().getNickname());
        model.addAttribute("mediBoxs", pageList);

        // 페이징 처리
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", pageList.hasNext());
        model.addAttribute("hasPrev", pageList.hasPrevious());
        return "/Medi_box/myMediBox";
    }

    // 약통 상세정보 페이지(GET)
    @GetMapping("/medi/box/{id}")
    public String mediBoxFind(@PathVariable Long id, Model model) {
        MedicineBoxResponseDto dto =  medicineBoxService.findById(id);
        List<ScheduleResponseDto> scheduleList = scheduleDateService.scheduleList(id);

        model.addAttribute("box", dto);
        model.addAttribute("scheList", scheduleList);
        return "/Medi_box/BoxInformation";
    }

    // 약통 추가(POST)
    @PostMapping("/api/medi/add")
    @ResponseBody
    public Long mediBoxCreate(@RequestBody MedicineBoxSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        System.out.println(requestDto);
//
//        for (MedicineList medicineList : requestDto.getMedicineLists()) {
//            System.out.println(medicineList);
//        }

        requestDto.setMemberId(userDetails.getMember().getId());
        return medicineBoxService.mediBoxCreate(requestDto);
    }

    // 약통 수정(POST)
    @PostMapping("/api/medi/update/{id}")
    @ResponseBody
    public Long mediBoxUpdate(@PathVariable Long id, @RequestBody MedicineBoxUpdateRequestDto requestDto){
        return medicineBoxService.mediBoxUpdate(id, requestDto);
    }

    // 약통 삭제(DELETE)
    @DeleteMapping("/api/medi/delete/{id}")
    @ResponseBody
    public Long mediBoxDelete(@PathVariable Long id){
        medicineBoxService.mediBoxDelete(id);
        return id;
    }
}
