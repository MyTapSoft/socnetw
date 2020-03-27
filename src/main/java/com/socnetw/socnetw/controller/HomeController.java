package com.socnetw.socnetw.controller;

import com.socnetw.socnetw.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping({"", "index.html", "index"})
    public String homePage() {
        return "index";
    }

    @GetMapping("registration")
    public ModelAndView getRegistrationPage() {
        return new ModelAndView("authorization/registration", "userDTO", new UserDTO());
    }

    @GetMapping("login")
    public String getLoginPage() {
        return "authorization/login";

    }
}
