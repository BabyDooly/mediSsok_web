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

    @GetMapping("/ttt")
    public String tt(){return "/test/ttt";}


    // 약 검색
    @GetMapping("/medi/search")
    public String dispSearch() {
        return "/Medi_search/mediSearch";
    }


    @GetMapping("/medi/info")
    public String mediINfo(){ return "/Medi_search/mediInfo"; }
}
