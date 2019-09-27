package com.socnetw.socnetw.controller;

import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.model.Post;
import com.socnetw.socnetw.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostViewController {

    private final PostService postService;
    private final String POSTS_POST_URL = "posts/feed";
    private final String POSTS_ALLPOSTS_URL = "posts/allposts";


    @Autowired
    public PostViewController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @RequestMapping("/feed")
    public String getFeed(HttpSession session, Model model) throws UnauthorizedException {
        if (session.getAttribute("loginStatus") == null) throw new UnauthorizedException("You have to login first");
        Long userId = (Long) session.getAttribute("userId");
        model.addAttribute("posts", postService.feed(userId));
        System.out.println(postService.feed(userId));
        return POSTS_POST_URL;
    }

    @GetMapping
    @RequestMapping("/post/{postId}")
    public String findPostById(@PathVariable String postId, Model model) {
        Post post = postService.findById(Long.parseLong(postId));
        model.addAttribute("post", post);
        return POSTS_POST_URL;
    }

    @GetMapping
    @RequestMapping("/post/{userId}/basic")
    public String getUserAndFriendsPosts(@PathVariable String userId, Model model) {
        List<Post> posts = postService.findUserAndFriendsPosts(Long.valueOf(userId));
        model.addAttribute("posts", posts);
        return POSTS_ALLPOSTS_URL;
        //todo connect path variable on UI side
    }

    @GetMapping
    @RequestMapping("/post/all")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return POSTS_ALLPOSTS_URL;
    }

    @GetMapping
    @RequestMapping("/post/{userId}/all")
    public String getUserPosts(@PathVariable String userId, Model model) {
        List<Post> posts = postService.findByUserId(Long.valueOf(userId));
        model.addAttribute("posts", posts);
        return POSTS_ALLPOSTS_URL;
        //todo connect path variable on UI side
    }

    @GetMapping
    @RequestMapping("/post/{userId}/friends")
    public String getFriendsPosts(@PathVariable String userId, Model model) {
        List<Post> posts = postService.findFriendsPosts(Long.valueOf(userId));
        model.addAttribute("posts", posts);
        return POSTS_ALLPOSTS_URL;
        //todo connect path variable on UI side
    }
}
