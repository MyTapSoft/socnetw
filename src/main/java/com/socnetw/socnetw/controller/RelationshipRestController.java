package com.socnetw.socnetw.controller;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.model.RelationshipStatus;
import com.socnetw.socnetw.service.RelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class RelationshipRestController {
    private final RelationshipService service;

    @Autowired
    public RelationshipRestController(RelationshipService service) {
        this.service = service;
    }

    @PostMapping("/relationship/new")
    public ResponseEntity<String> save(@RequestParam String userIdTo, @RequestParam String desiredStatus, HttpSession session) throws UnauthorizedException, BadRequestException {
        if (RelationshipStatus.valueOf(desiredStatus) != RelationshipStatus.PENDING) {
            return update(session, userIdTo, desiredStatus);
        }
        isUserLogin(session);
        String userIdFrom = String.valueOf(session.getAttribute("userId"));
        service.save(userIdFrom, userIdTo);
        log.info("New relationship added successfully. From user: " + userIdFrom + ". To user: " + userIdTo);
        return new ResponseEntity<>("Request Send", HttpStatus.OK);
    }

    @PutMapping("/relationship/update")
    public ResponseEntity<String> update(HttpSession session,
                                         @RequestParam String userIdTo,
                                         @RequestParam String desiredStatus) throws UnauthorizedException, BadRequestException {
        isUserLogin(session);
        String userIdFrom = String.valueOf(session.getAttribute("userId"));
        service.update(userIdFrom, userIdTo, RelationshipStatus.valueOf(desiredStatus));
        log.info("Relationship updated successfully. From user: " + userIdFrom + ". To user: " + userIdTo);

        return new ResponseEntity<>("Request Send", HttpStatus.OK);

    }

    private void isUserLogin(HttpSession session) throws UnauthorizedException {
        if (session.getAttribute("loginStatus") == null) throw new UnauthorizedException("You have to login first");
    }
}
