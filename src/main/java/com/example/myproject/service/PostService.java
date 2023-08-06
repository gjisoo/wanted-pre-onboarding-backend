package com.example.myproject.service;

import org.springframework.beans.factory.annotation.Autowired;
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

}
