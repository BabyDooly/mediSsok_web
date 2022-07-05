package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxRequestDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxSaveResponseDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxUpdateResponseDto;
import mediSsok.mediSsokspring.service.MedicineBoxService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MedicineBoxController {
    private final MedicineBoxService medicineBoxService;

    // 내 약통
    @GetMapping("/medi/medibox")
    public String dispMedicase(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("mediBoxs", medicineBoxService.findByMemberId(userDetails.getMember().getId()));
        model.addAttribute("member", userDetails.getMember().getNickname());
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
    public Long save(@RequestBody MedicineBoxSaveResponseDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDto.setMember(userDetails.getMember());
        return medicineBoxService.create(requestDto);
    }

    // 약통 수정
    @PostMapping("/api/medi/update/{id}")
    @ResponseBody
    public Long update(@PathVariable Long id, @RequestBody MedicineBoxUpdateResponseDto medicineBoxUpdateResponseDto){
        return medicineBoxService.update(id, medicineBoxUpdateResponseDto);
    }

    // 게시물 삭제
    @DeleteMapping("/api/medi/delete/{id}")
    @ResponseBody
    public Long delete(@PathVariable Long id){
        medicineBoxService.delete(id);
        return id;
    }
}
