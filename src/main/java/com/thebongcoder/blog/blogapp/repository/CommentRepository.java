package com.thebongcoder.blog.blogapp.repository;

import com.thebongcoder.blog.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
