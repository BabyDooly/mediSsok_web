package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.domain.entity.medicineBox.MedicineBox;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxSaveRequestDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleSaveRequestDto;
import mediSsok.mediSsokspring.service.MedicineBoxService;
import mediSsok.mediSsokspring.service.MemberService;
import mediSsok.mediSsokspring.service.ScheduleDateService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class ScheduleDateController {
    private final MedicineBoxService medicineBoxService;
    private final ScheduleDateService scheduleDateService;

    // 오늘 알람
    @GetMapping("/medi/bell")
    public String dispBell(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MedicineBoxResponseDto> list = medicineBoxService.findByMemberId(userDetails.getMember().getId());


        // 약통, 맴버이름
        model.addAttribute("member", userDetails.getMember().getNickname());
        model.addAttribute("mediBoxs", list);


        return "/Medi_bell/mediBell";
    }

    // 알람 추가
    @PostMapping("/api/medi/bell/add")
    @ResponseBody
    public Long save(@RequestBody ScheduleSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDto.setMemberId(userDetails.getMember().getId());
        System.out.println("테스트 : " + requestDto);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Date date = sdf.parse(strDate);

        return scheduleDateService.create(requestDto);
    }

}
