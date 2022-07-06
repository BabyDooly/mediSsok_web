package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.medicineBox.*;
import mediSsok.mediSsokspring.service.MedicineBoxService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MedicineBoxController {
    private final MedicineBoxService medicineBoxService;

    // 내 약통
    @GetMapping("/medi/medibox")
    public String dispMedicase(Model model, @AuthenticationPrincipal CustomUserDetails userDetails,
                               @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        // 페이징 리스트
        Page<MedicineBoxResponseDto> list = medicineBoxService.findByMemberId(userDetails.getMember().getId(), pageable);

        // 약통, 맴버이름
        model.addAttribute("member", userDetails.getMember().getNickname());
        model.addAttribute("mediBoxs", list);

        // 페이징 처리
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        return "/Medi_box/myMediBox";
    }

    // 약통 조회
    @PostMapping("/api/medi/get")
    @ResponseBody
    public MedicineBoxResponseDto findById(@RequestBody MedicineBoxRequestDto requestDto){
        return medicineBoxService.findById(requestDto.getMedicineBoxId());
    }

    // 약통 추가
    @PostMapping("/api/medi/add")
    @ResponseBody
    public Long save(@RequestBody MedicineBoxSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        System.out.println(requestDto);
//
//        for (MedicineList medicineList : requestDto.getMedicineLists()) {
//            System.out.println(medicineList);
//        }

        requestDto.setMemberId(userDetails.getMember().getId());
        return medicineBoxService.create(requestDto);
    }

    // 약통 수정
    @PostMapping("/api/medi/update/{id}")
    @ResponseBody
    public Long update(@PathVariable Long id, @RequestBody MedicineBoxUpdateRequestDto medicineBoxUpdateRequestDto){
        return medicineBoxService.update(id, medicineBoxUpdateRequestDto);
    }

    // 게시물 삭제
    @DeleteMapping("/api/medi/delete/{id}")
    @ResponseBody
    public Long delete(@PathVariable Long id){
        medicineBoxService.delete(id);
        return id;
    }
}
