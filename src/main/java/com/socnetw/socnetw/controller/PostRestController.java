package com.socnetw.socnetw.controller;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.Post;
import com.socnetw.socnetw.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class PostRestController {
    private final PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @RequestMapping("/post/new")
    public ResponseEntity<Object> save(@ModelAttribute Post post) throws BadRequestException {
        Post result = postService.save(post);
        log.info("New post added successfully. Post ID: " + result.getId() + ". User ID: " + result.getUserPosted().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping("/post/{postId}/update")
    public ResponseEntity<Object> update(@ModelAttribute Post post, @PathVariable String postId) throws BadRequestException {
        Post result = postService.update(post);
        log.info("Post updated successfully. Post ID: " + result.getId() + ". User ID: " + result.getUserPosted().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
        //todo wrong method logic
    }

    @DeleteMapping
    @RequestMapping("/post/{postId}/delete")
    public ResponseEntity<Object> deleteById(@PathVariable String postId) throws BadRequestException {
        postService.delete(Long.parseLong(postId));
        log.info("Post deleted successfully. Post ID: " + postId);
        return new ResponseEntity<>("Post with ID" + postId + " has been deleted", HttpStatus.OK);
        //todo path variable connect with UI

    }
}
