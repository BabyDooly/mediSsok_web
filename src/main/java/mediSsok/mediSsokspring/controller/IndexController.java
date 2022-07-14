package mediSsok.mediSsokspring.controller;

import lombok.RequiredArgsConstructor;
import mediSsok.mediSsokspring.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor    //final 필드 생성자 생성
@Controller
public class IndexController {
    private final MemberService memberService;

    // test 알람설정 만드는중
    @GetMapping("/test")
    public String test(){
        return "/login/test";
    }

    @GetMapping("/tt")
    public String tt(){return "/test/tt";}

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

    // 비밀번호 찾기 페이지
    @GetMapping("/user/forgot")
    public String dispForgot() {
        return "/login/forgot-password";
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


}
