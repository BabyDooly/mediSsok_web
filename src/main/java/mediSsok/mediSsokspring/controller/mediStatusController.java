package mediSsok.mediSsokspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mediStatusController {
    @GetMapping("/mediStat")
    public String mediStatus(){return "Medi_status/mediStat";}
}