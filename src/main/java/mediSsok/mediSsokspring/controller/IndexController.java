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
        return "/test/tes";
    }

    @GetMapping("/ttt")
    public String tt(){return "/test/ttt";}
}
