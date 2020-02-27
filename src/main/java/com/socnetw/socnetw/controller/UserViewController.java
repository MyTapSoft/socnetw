package com.socnetw.socnetw.controller;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserViewController {

    private final UserService userService;
    private final String USER_PROFILE_URL = "user/profile";
    private final String USER_ALL_USERS_URL = "user/allusers";
    private final String USER_FRIENDS_URL = "user/friends";

    @Autowired
    public UserViewController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/{userId}")
    public String findUserById(Model model, @PathVariable String userId) throws BadRequestException {
        User user = userService.getUser(Long.parseLong(userId));
        model.addAttribute("user", user);
        return USER_PROFILE_URL;
    }


    @GetMapping("/user/all")
    public String getAllUsers(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return USER_ALL_USERS_URL;
    }

    @GetMapping("/friends")
    public String getUserFriends(Model model, HttpSession session) throws BadRequestException {
        if (session.getAttribute("loginStatus") == null) throw new BadRequestException("You have to login first");
        Long userId = (Long) session.getAttribute("userId");
        model.addAttribute("user", userService.getUserFriends(userId));
        return USER_FRIENDS_URL;
    }


    @GetMapping("/requests/income")
    public String getIncomeRequests(HttpSession session, Model model) throws UnauthorizedException {
        String userId = String.valueOf(session.getAttribute("userId"));
        model.addAttribute("user", userService.getIncomeRequests(userId, session));
        return USER_FRIENDS_URL;

    }

    @GetMapping("/requests/outcome")
    public String getOutcomeRequests(HttpSession session, Model model) throws UnauthorizedException {
        String userId = String.valueOf(session.getAttribute("userId"));
        List<User> usersList = userService.getOutcomeRequests(userId, session);
        model.addAttribute("user", usersList);
        return USER_FRIENDS_URL;
    }
}
