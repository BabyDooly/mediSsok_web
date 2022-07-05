package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.member.*;
import mediSsok.mediSsokspring.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MemberController {
    private final MemberService memberService;

    // 회원가입 페이지(GET)
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "/login/register";
    }

    // 회원가입 로직검사(POST)
    @PostMapping("/user/signupProc")
    public String Signup(@Valid MemberSaveResponseDto memberDto, Errors errors , Model model) {

//
//        if (errors.hasErrors()) {
//            // 회원가입 실패시 입력 데이터 값을 유지
//            model.addAttribute("memberDto", memberDto);
//
//            // 유효성 통과 못한 필드와 메시지를 핸들링
//            Map<String, String> validatorResult = memberService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }
//
//            // 회원가입 페이지로 다시 리턴
//            return "/user/signup";
//        }
        // 성공하면 로그인페이지로
        System.out.println(memberDto);
        memberService.save(memberDto);
        return "redirect:/user/login";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String login(@RequestParam(value = "error", required = false)String error,
                        @RequestParam(value = "exception", required = false)String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/login/login";
    }

    // 마이페이지
    @GetMapping("/user/mypage")
    public String dispMypage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        MemberResponseDto dto = memberService.findByEmail(userDetails.getUsername());
        model.addAttribute("member", dto);
        return "/myPage/myPage";
    }

    // 회원 조회
    @GetMapping("/api/member")
    public MemberResponseDto findById(@AuthenticationPrincipal CustomUserDetails userDetails){
        return memberService.findByEmail(userDetails.getUsername());
    }

    // 회원 수정(JSON)
    @PostMapping("/api/member/user")
    @ResponseBody
    public Long userUpdate(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MemberUserUpdateRequestDto requestDto){
        return memberService.userUpdate(userDetails.getUsername(), requestDto);
    }

    // 알람 수정(JSON)
    @PostMapping("/api/member/alarm")
    @ResponseBody
    public Long alarmUpdate(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MemberAlarmUpdateRequestDto requestDto){
        return memberService.alarmUpdate(userDetails.getUsername(), requestDto);
    }
}