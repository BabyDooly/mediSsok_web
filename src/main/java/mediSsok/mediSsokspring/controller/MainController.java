package mediSsok.mediSsokspring.controller;


import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.member.LinkInfoResponseDto;
import mediSsok.mediSsokspring.dto.member.LinkInfoSaveRequestDto;
import mediSsok.mediSsokspring.dto.member.LinkInfoUpdateRequestDto;
import mediSsok.mediSsokspring.dto.member.MemberResponseDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleUpdateRequestDto;
import mediSsok.mediSsokspring.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor    //final 필드 생성자 생성
@Controller
public class MainController {
    private final MemberService memberService;

    // 메인 페이지(GET)
    @GetMapping(value = {"/", "/index", "/main"})
    public String index(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null){
            // 링크 신청 조회
            List<LinkInfoResponseDto> linkCheckDto = memberService.linkPermitFind(userDetails.getMember().getEmail());

            model.addAttribute("linkList", linkCheckDto);
        }

        return "/index";
    }

    // 연동 신청 체크(GET)
    @GetMapping("/api/check")
    @ResponseBody
    public List<LinkInfoResponseDto> test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return memberService.linkPermitFind(userDetails.getMember().getEmail());
    }

    // 연동 신청(POST)
    @PostMapping("/api/link/permit")
    @ResponseBody
    public Long linkUpdate(@RequestBody LinkInfoSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDto.setMember(userDetails.getMember());

        return memberService.linkCreate(requestDto);
    }

}
