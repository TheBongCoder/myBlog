package com.thebongcoder.blog.blogapp.controller;

import com.thebongcoder.blog.blogapp.dto.CommentDTO;
import com.thebongcoder.blog.blogapp.service.CommentService;
import com.thebongcoder.blog.blogapp.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentService commentService;

    private final ResponseHandler responseHandler;

    public CommentController(CommentService commentService, ResponseHandler responseHandler) {
        this.commentService = commentService;
        this.responseHandler = responseHandler;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> createComment(@PathVariable long postId, @RequestBody CommentDTO commentDTO) {
        try {
            log.info("create comment request received for postId : {}", postId);
            CommentDTO comment = commentService.createComment(postId, commentDTO);
            if (comment != null) {
                return responseHandler.generateResponse(comment, "Comment created successfully", true, HttpStatus.CREATED);
            }
            return responseHandler.generateResponse(comment, "Post Id not found", false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error generating while creating comment", e);
        }
        return responseHandler.generateResponse("", "Error generating while creating comment", false, HttpStatus.BAD_REQUEST);

    }
}
