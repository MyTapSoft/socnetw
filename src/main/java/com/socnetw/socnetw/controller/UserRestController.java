package com.socnetw.socnetw.controller;


import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.exceptions.UserAlreadyExist;
import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.model.UserDTO;
import com.socnetw.socnetw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/user/")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }



    @PutMapping("update")
    public ResponseEntity<Object> update(@ModelAttribute User user, HttpSession session) throws UnauthorizedException {
        userService.updateUser(user, session);
        log.info("User update data successfully. ID " + user.getId());
        return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
    }


    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(HttpSession session) throws UnauthorizedException {
        Long userId = (Long) session.getAttribute("userId");
        userService.deleteUser(userId, session);
        log.info("User deleted successfully. ID " + userId);
        return new ResponseEntity<>("User With ID: " + userId + " Deleted Successfully", HttpStatus.OK);
    }


}
