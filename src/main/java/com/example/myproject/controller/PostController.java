package com.example.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.entity.Post;
import com.example.myproject.entity.User;
import com.example.myproject.security.JwtUtil;
import com.example.myproject.service.PostService;
import com.example.myproject.service.UserService;

@RestController
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/write/post")
    public ResponseEntity<String> createPost(@RequestBody Post post, Authentication authentication) {
        
        String email = authentication.getName();

        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("유효하지 않은 사용자입니다.");
        }

        post.setAuthor(user);

        postService.createPost(post);

        return ResponseEntity.ok("게시글이 성공적으로 생성되었습니다.");
    }
}
