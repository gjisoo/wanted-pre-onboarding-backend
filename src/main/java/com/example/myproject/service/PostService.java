package com.example.myproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.myproject.entity.Post;
import com.example.myproject.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public Page<Post> getPostsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postRepository.findAllByOrderByCreatedDtDesc(pageable);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public void updatePost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

}
