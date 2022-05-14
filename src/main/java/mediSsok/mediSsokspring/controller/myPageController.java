package mediSsok.mediSsokspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class myPageController {
    @GetMapping("/myPage")
    public String myPage() {  return "myPage/myPage";  }
}

