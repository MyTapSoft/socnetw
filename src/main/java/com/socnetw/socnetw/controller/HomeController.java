package com.socnetw.socnetw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping({"", "index.html", "index"})
    public String homePage() {
        return "index";
    }

    @GetMapping("registration")
    public String getRegistrationPage() {
        return "user/registration";
    }

    @GetMapping("login")
    public String getLoginPage() {
        return "user/login";
    }
}
