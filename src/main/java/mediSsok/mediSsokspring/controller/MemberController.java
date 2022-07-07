package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.Validation.CheckEmailValidator;
import mediSsok.mediSsokspring.Validation.CheckNicknameValidator;
import mediSsok.mediSsokspring.domain.repository.member.MemberRepository;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.member.*;
import mediSsok.mediSsokspring.service.MemberService;
import org.aspectj.bridge.Message;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Member;
import java.util.Map;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MemberController {
    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입시 중복및 유효성 검사 부분
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckEmailValidator checkEmailValidator;


    // 회원가입 페이지(GET)
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "/login/register";
    }

    // 회원가입 로직검사(POST)
    @PostMapping("/user/signupProc")
    public String Signup(@Valid MemberSaveResponseDto memberDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            // 회원가입 페이지로 다시 리턴
            return "/login/register";
        }
        System.out.println(memberDto);

        String pw = memberDto.getPassword();
        String pwconfirm = memberDto.getConfirm_Password();


        System.out.println("password: " + pw);
        System.out.println("PasswordConfirm: " + pwconfirm);


        if(!pw.equals(pwconfirm)){
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            // 회원가입 페이지로 다시 리턴
            return "/login/register";
        } else {
            memberService.save(memberDto);
        }
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
    public Long userUpdate(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberUserUpdateRequestDto requestDto){
        return memberService.userUpdate(userDetails.getUsername(), requestDto);
    }

    // 알람 수정(JSON)
    @PostMapping("/api/member/alarm")
    @ResponseBody
    public Long alarmUpdate(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberAlarmUpdateRequestDto requestDto){
        return memberService.alarmUpdate(userDetails.getUsername(), requestDto);
    }

    /* 커스텀 유효성 검증을 위해 추가 */
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkNicknameValidator);
        binder.addValidators(checkEmailValidator);
    }
}