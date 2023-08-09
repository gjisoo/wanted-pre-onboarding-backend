package com.example.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.dto.PostEditRequest;
import com.example.myproject.entity.Post;
import com.example.myproject.entity.User;
import com.example.myproject.service.PostService;
import com.example.myproject.service.UserService;

@RestController
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/write/post")
    public ResponseEntity<String> createPost(@RequestBody Post post, @AuthenticationPrincipal UserDetails userDetails) {
        
        System.out.println(userDetails);
        if (userDetails == null) {
            return ResponseEntity.badRequest().body("인증되지 않은 사용자입니다.");
        }

        String email = userDetails.getUsername();
        User user = userService.getUserByEmail(email);
        
        if (user == null) {
            return ResponseEntity.badRequest().body("유효하지 않은 사용자입니다.");
        }

        post.setAuthor(user);

        try {
            postService.createPost(post);
            return ResponseEntity.ok("게시글이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("게시글 생성 중 문제가 발생했습니다.");
        }
    }

    @GetMapping("/view/posts")
    public ResponseEntity<Page<Post>> getPosts(@RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size) {

        Page<Post> posts = postService.getPostsByPage(page-1, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/view/post/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @PutMapping("/edit/post/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId, 
    @RequestBody PostEditRequest editRequest, 
    @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        User author = post.getAuthor();
        String email = userDetails.getUsername();
        if (!author.getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 작성자만 수정할 수 있습니다.");
        }

        post.setTitle(editRequest.getTitle());
        post.setContent(editRequest.getContent());
        postService.updatePost(post);
        
        return ResponseEntity.ok("게시글이 수정되었습니다.");
    }

    @DeleteMapping("/delete/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        User author = post.getAuthor();
        String email = userDetails.getUsername();
        if (!author.getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 작성자만 삭제할 수 있습니다.");
        }

        postService.deletePost(postId);

        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }

}