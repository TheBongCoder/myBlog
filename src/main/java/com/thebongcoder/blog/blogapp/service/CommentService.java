package com.thebongcoder.blog.blogapp.service;

import com.thebongcoder.blog.blogapp.dto.CommentDTO;
import com.thebongcoder.blog.blogapp.entity.Comment;
import com.thebongcoder.blog.blogapp.entity.Post;
import com.thebongcoder.blog.blogapp.repository.CommentRepository;
import com.thebongcoder.blog.blogapp.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        log.info("create comment request received for : {}", commentDTO.getId());
        Comment comment = mapToEntity(commentDTO);
        Post post = postRepository.findById(postId).orElse(null);
        comment.setPost(post);
        comment = commentRepository.save(comment);
        return mapToDTO(comment);
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
