package mediSsok.mediSsokspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class calendarController {
    @GetMapping("/calendar")
    public String calendar(){ return "calendar/calendar"; }

}
