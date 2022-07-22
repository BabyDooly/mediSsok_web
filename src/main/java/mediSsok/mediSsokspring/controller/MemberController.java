package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.Validation.CheckEmailValidator;
import mediSsok.mediSsokspring.Validation.CheckNicknameValidator;
import mediSsok.mediSsokspring.config.CustomUserDetails;
import mediSsok.mediSsokspring.dto.medicineBox.MedicineBoxResponseDto;
import mediSsok.mediSsokspring.dto.member.*;
import mediSsok.mediSsokspring.dto.schedule.ScheduleRequestDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleResponseDto;
import mediSsok.mediSsokspring.dto.schedule.ScheduleUpdateRequestDto;
import mediSsok.mediSsokspring.service.MemberService;
import mediSsok.mediSsokspring.service.SendEmailService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor    //final 필드 생성자 생성
public class MemberController {
    private final MemberService memberService;

    // 회원가입시 중복및 유효성 검사 부분
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckEmailValidator checkEmailValidator;
    private final SendEmailService sendEmailService;

    /*---- 개인정보 ----*/

    // 회원가입 페이지(GET)
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "/login/register";
    }

    // 로그인 페이지(GET)
    @GetMapping("/user/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/login/login";
    }

    // 회원가입 로직검사(POST)
    @PostMapping("/user/signupProc")
    public String Signup(@Valid MemberSaveRequestDto memberDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            // 회원가입 페이지로 다시 리턴
            return "/login/register";
        }
        if (!memberDto.getPassword().equals(memberDto.getConfirm_Password())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            // 회원가입 페이지로 다시 리턴
            return "/login/register";
        } else {
            memberService.memberCreate(memberDto);
        }
        return "redirect:/user/login";
    }

    /* 커스텀 유효성 검증을 위해 추가 */
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
//        binder.addValidators(checkNicknameValidator);
        binder.addValidators(checkEmailValidator);
    }

    // 비밀번호 찾기 페이지(GET)
    @GetMapping("/user/forgot")
    public String dispForgot() {
        return "/login/forgot-password";
    }

    // Email이 DB에 존재하는지 체크하는 부분(POST)
    @PostMapping("/api/member/findEmail")
    @ResponseBody
    public boolean findEmail(@RequestBody MemberRequestDto requestDto) {
        return memberService.userEmailCheck(requestDto.getMemberEmail());
    }

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러(POST)
    @PostMapping("/api/member/sendEmail")
    @ResponseBody
    public void sendEmail(@RequestBody MemberRequestDto requestDto){
        MailDTO dto = sendEmailService.createMailAndChangePassword(requestDto.getMemberEmail());
        sendEmailService.mailSend(dto);
    }

    // 마이페이지(GET)
    @GetMapping("/user/mypage")
    public String dispMypage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        // 맴버 정보 조회
        MemberResponseDto memberDto = memberService.findByEmail(userDetails.getUsername());
        // 링크 사용자 조회
        List<LinkInfoResponseDto> linkListDto = memberService.linkFind(userDetails.getMember().getId());

        model.addAttribute("member", memberDto);
        model.addAttribute("linkList", linkListDto);

        return "/myPage/myPage";
    }


    // 회원 조회(GET,JSON)
    @GetMapping("/api/member")
    @ResponseBody
    public MemberResponseDto findById(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return memberService.findByEmail(userDetails.getUsername());
    }

    // 회원 수정(POST)
    @PostMapping("/api/member/user")
    @ResponseBody
    public Long userUpdate(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MemberUserUpdateRequestDto requestDto) {
        return memberService.userUpdate(userDetails.getUsername(), requestDto);
    }

    // 회원 비밀번호 수정(POST)
    @PostMapping("/api/member/password")
    @ResponseBody
    public Long userPassWordUpdate(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MemberPasswordUpdateRequestDto requestDto) {
        return memberService.passwordUpdate(userDetails.getUsername(), requestDto);
    }

    // 알람 수정(POST)
    @PostMapping("/api/member/alarm")
    @ResponseBody
    public Long alarmUpdate(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MemberAlarmUpdateRequestDto requestDto) {
        return memberService.alarmUpdate(userDetails.getUsername(), requestDto);
    }

    /*---- 연동 ----*/
    // 연동 신청(POST)
    @PostMapping("/api/link/add")
    @ResponseBody
    public Long linkCreate(@RequestBody LinkInfoSaveRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDto.setMemberId(userDetails.getMember().getId());

        boolean check = memberService.linkEmailCheck(requestDto.getUserEmail(), userDetails.getMember().getId());

        // 중복이 있으면 0번 반환
        if (check)
            return 0L;
        else
            return memberService.linkCreate(requestDto);
    }

    // 연동 조회(POST)
    @PostMapping("/api/link/get")
    @ResponseBody
    public LinkInfoResponseDto linkFind(@RequestBody LinkInfoRequestDto requestDto) {
        return memberService.linkFindById(requestDto.getLinkId());
    }


    // 연동 수정(POST)
    @PostMapping("/api/link/update/{id}")
    @ResponseBody
    public Long linkUpdate(@PathVariable Long id, @RequestBody LinkInfoUpdateRequestDto requestDto){
        return memberService.linkUpdate(id, requestDto);
    }

    // 약통 삭제(DELETE)
    @DeleteMapping("/api/link/delete/{id}")
    @ResponseBody
    public Long linkDelete(@PathVariable Long id){
        memberService.linkDelete(id);
        return id;
    }
}