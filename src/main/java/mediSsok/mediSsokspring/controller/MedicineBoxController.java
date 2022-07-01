package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxSaveResponseDto;
import mediSsok.mediSsokspring.service.MedicineBoxService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MedicineBoxController {
    MedicineBoxService medicineBoxService;

    // 내 약통
    @GetMapping("/medi/medibox")
    public String dispMedicase() {
        return "/Medi_box/myMediBox";
    }

    @PostMapping("/api/medi/add")
    @ResponseBody
    public Long add(@RequestBody MedicineBoxSaveResponseDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        dto.setMember(userDetails.getUsername());

        return medicineBoxService.save(dto);
    }
}
