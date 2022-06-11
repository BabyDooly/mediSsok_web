package mediSsok.mediSsokspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {
    @GetMapping("/login")
    public String login(){return "login/login";}

    @GetMapping("/forgot-password")
    public String forgotPassword(){return "login/forgot-password";}

    @GetMapping("/register")
    public String register(){return "login/register";}
}