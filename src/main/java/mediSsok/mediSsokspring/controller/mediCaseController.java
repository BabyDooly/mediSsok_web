package mediSsok.mediSsokspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mediCaseController {
    @GetMapping("/myMediCase")
    public String myMediCase(){return "Medi_case/myMediCase";}
}
