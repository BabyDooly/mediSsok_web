package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.dto.MemberResponseDto;
import mediSsok.mediSsokspring.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor    //final 필드 생성자 생성
@Controller
public class IndexController {
    private final MemberService memberService;

    // test 알람설정 만드는중
    @GetMapping("/test")
    public String test(){
        return "/test/tes";
    }

    // 메인 페이지
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    // 메인 페이지2
    @GetMapping("/main")
    public String main() {
        return "/index";
    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "/login/register";
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/user/forgot")
    public String dispForgot() {
        return "/login/forgot-password";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "/login/login";
    }

    // 마이페이지
    @GetMapping("/user/mypage/{id}")
    public String dispMypage(@PathVariable Long id, Model model) {
        MemberResponseDto dto = memberService.findById(id);
        model.addAttribute("member", dto);
        return "/myPage/myPage";
    }

    // 약 검색
    @GetMapping("/medi/search")
    public String dispSearch() {
        return "/Medi_search/mediSearch";
    }

    // 캘린더
    @GetMapping("/medi/calendar")
    public String dispCalendar() {
        return "/calendar/calendar";
    }

    // 복용현황
    @GetMapping("/medi/status")
    public String dispStatus() {
        return "/Medi_status/mediStat";
    }

    // 알람 설정
    @GetMapping("/medi/bell")
    public String dispBell() { return "/Medi_bell/mediBell";}

    // 내 약통
    @GetMapping("/medi/medicase")
    public String dispMedicase() {
        return "/Medi_case/myMediCase";
    }

    /*
    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "/denied";
    }


    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/admin";
    }
    */
}
