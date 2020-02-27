package com.socnetw.socnetw.controller;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.Message;
import com.socnetw.socnetw.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class MessageRestController {
    private final MessageService messageService;

    @Autowired
    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message/send")
    public ResponseEntity<Object> send(@ModelAttribute Message message) throws BadRequestException {
        Message returnedMessage = messageService.send(message);
        log.info("Message send to user: " + returnedMessage.getUserTo().getId() + " from user: " + returnedMessage.getUserFrom().getId());
        return new ResponseEntity<>(returnedMessage.getText(), HttpStatus.OK);
    }

    @PutMapping("/message/update")
    public ResponseEntity<Object> update(@ModelAttribute Message message) throws BadRequestException {
        Message returnedMessage = messageService.update(message);
        log.info("Message updated successfully. ID: " + returnedMessage.getId());
        return new ResponseEntity<>(returnedMessage.getText(), HttpStatus.OK);
    }

    @DeleteMapping("/message/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable String id) throws BadRequestException {
        messageService.delete(Long.valueOf(id));
        log.info("Message deleted successfully. ID: " + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) throws BadRequestException {
        Message returnedMessage = messageService.findById(Long.valueOf(id));
        log.info("Message was found successfully. ID: " + returnedMessage.getId());
        return new ResponseEntity<>(returnedMessage.getText(), HttpStatus.OK);
    }
}
