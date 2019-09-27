package com.socnetw.socnetw.controller;


import com.socnetw.socnetw.exceptions.NotFoundException;
import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/user/new")
    public ResponseEntity<String> save(@ModelAttribute User user) {
        userService.saveUser(user);
        log.info("New user added successfully. ID " + user.getId());
        return new ResponseEntity<>("User Saved Successfully", HttpStatus.CREATED);
    }

    @PutMapping
    @RequestMapping("/user/update")
    public ResponseEntity<Object> update(@ModelAttribute User user, HttpSession session) throws UnauthorizedException {
        userService.updateUser(user, session);
        log.info("User update data successfully. ID " + user.getId());
        return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
    }


    @DeleteMapping
    @RequestMapping("/user/delete")
    public ResponseEntity<Object> delete(HttpSession session) throws UnauthorizedException {
        Long userId = (Long) session.getAttribute("userId");
        userService.deleteUser(userId, session);
        logout(session);
        log.info("User deleted successfully. ID " + userId);
        return new ResponseEntity<>("User With ID: " + userId + " Deleted Successfully", HttpStatus.OK);
    }


    @PostMapping
    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<Object> login(HttpSession session,
                                        @RequestParam(value = "username") String username,
                                        @RequestParam(value = "password") String password) {

        if (session.getAttribute("loginStatus") != null)
            return new ResponseEntity<>("User Already Logged in!", HttpStatus.BAD_REQUEST);

        User user = userService.login(username, password);
        if (user == null) throw new NotFoundException("Wrong Login Or Password");
        session.setAttribute("loginStatus", "true");
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("realName", user.getRealName());
        session.setAttribute("birthDate", user.getBirthDate());
        session.setAttribute("userPosts", user.getPosts());
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session) throws UnauthorizedException {
        if (session.getAttribute("loginStatus") == null) {
            throw new UnauthorizedException("User Already Logged Out");
        }
        session.removeAttribute("loginStatus");
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        session.removeAttribute("realName");
        session.removeAttribute("birthDate");
        session.removeAttribute("userPosts");
        return new ResponseEntity<>("User Logout Successfully", HttpStatus.OK);
    }
}
