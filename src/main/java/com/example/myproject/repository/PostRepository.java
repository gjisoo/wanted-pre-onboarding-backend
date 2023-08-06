package com.example.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 필요에 따라 추가적인 쿼리 메서드를 선언할 수 있습니다.
    // ...
}