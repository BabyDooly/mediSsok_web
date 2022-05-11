package mediSsok.mediSsokspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mediStatusController {
    @GetMapping("/myMedi_stat")
    public String mediStatus(){return "Medi_status/myMedi_stat";}
}