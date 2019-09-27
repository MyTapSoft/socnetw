package com.socnetw.socnetw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping
    @RequestMapping({"/", "", "index.html", "index"})
    public String homePage() {
        return "index";
    }

    @GetMapping
    @RequestMapping("/registration")
    public String getRegistrationPage() {
        return "user/registration";
    }

    @GetMapping
    @RequestMapping("/login")
    public String getLoginPage() {
        return "user/login";
    }
}
