package com.socnetw.socnetw.controller;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.exceptions.UserAlreadyExist;
import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.model.UserDTO;
import com.socnetw.socnetw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class UserViewController {

    private final UserService userService;
    private static final String USER_PROFILE_URL = "user/profile";
    private static final String USER_ALL_USERS_URL = "user/users";
    private static final String USER_FRIENDS_URL = "user/friends";

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

    @PostMapping("/user/save")
    public ModelAndView save(@ModelAttribute("userDTO") @Valid UserDTO userDTO,
                             BindingResult bindingResult, WebRequest request, Errors errors) {
        User registered = new User();
        if (!bindingResult.hasErrors()) {
            registered = createUserAccount(userDTO, bindingResult);
        }
        if (registered == null) {
            bindingResult.rejectValue("email", "message.regError");
        }
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> log.error(error.toString()));
            return new ModelAndView("authorization/registration", "error", bindingResult.getAllErrors());
        } else {
            return new ModelAndView("users", "user", userDTO);
        }
    }
    private User createUserAccount(UserDTO accountDto, BindingResult result) {
        User registered;
        try {
            registered = userService.saveUser(accountDto);
        } catch (UserAlreadyExist e) {
            return null;
        }
        return registered;
    }
}
