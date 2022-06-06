package mediSsok.mediSsokspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mediSearchController {
    @GetMapping("/mediSearch")
    public String mediSearch(){return "Medi_search/mediSearch";}
}
