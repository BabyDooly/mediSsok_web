package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.dto.member.MemberAlarmUpdateRequestDto;
import mediSsok.mediSsokspring.dto.member.MemberResponseDto;
import mediSsok.mediSsokspring.dto.member.MemberSaveResponseDto;
import mediSsok.mediSsokspring.dto.member.MemberUserUpdateRequestDto;
import mediSsok.mediSsokspring.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MemberApiController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/user/signup")
    public String Signup(MemberSaveResponseDto memberDto) {
        memberService.save(memberDto);

        return "redirect:/user/login";
    }

    // 마이페이지
    @GetMapping("/user/mypage")
    public String dispMypage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        MemberResponseDto dto = memberService.findByEmail(userDetails.getUsername());
        model.addAttribute("member", dto);
        return "/myPage/myPage";
    }

    // 회원 조회
    @GetMapping("/api/member")
    public MemberResponseDto findById(@AuthenticationPrincipal UserDetails userDetails){
        return memberService.findByEmail(userDetails.getUsername());
    }

    // 회원 수정(JSON)
    @PostMapping("/api/member/user")
    @ResponseBody
    public Long update(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberUserUpdateRequestDto requestDto){
        return memberService.userUpdate(userDetails.getUsername(), requestDto);
    }

    // 알람 수정(JSON)
    @PostMapping("/api/member/alarm")
    @ResponseBody
    public Long update(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberAlarmUpdateRequestDto requestDto){
        return memberService.alarmUpdate(userDetails.getUsername(), requestDto);
    }
}