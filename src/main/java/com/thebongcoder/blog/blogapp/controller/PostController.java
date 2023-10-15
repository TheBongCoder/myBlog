package com.thebongcoder.blog.blogapp.controller;

import com.thebongcoder.blog.blogapp.dto.PostDTO;
import com.thebongcoder.blog.blogapp.dto.PostResponse;
import com.thebongcoder.blog.blogapp.service.PostService;
import com.thebongcoder.blog.blogapp.utils.AppConstants;
import com.thebongcoder.blog.blogapp.utils.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    private final ResponseHandler responseHandler;
    private final PostService postService;

    public PostController(PostService postService, ResponseHandler responseHandler) {
        this.postService = postService;
        this.responseHandler = responseHandler;
    }

    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO postResponse = postService.createPost(postDTO);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllPost(@RequestParam(value = AppConstants.PAGE_NO, defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                             @RequestParam(value = AppConstants.PAGE_SIZE, defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                             @RequestParam(value = AppConstants.PAGE_SORT_BY, defaultValue = AppConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy,
                                             @RequestParam(value = AppConstants.PAGE_SORT_DIR, defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String sortDir) {
        PostResponse postDTOList = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Object> getPostById(@PathVariable(value = AppConstants.POST_ID) long id) {

        PostDTO post = postService.getPostById(id);
        if (post != null) {
            return responseHandler.generateResponse(post, "Post Details fetch successfully", false, HttpStatus.OK);
        }
        return responseHandler.generateResponse(post, "PostId not found", false, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(value = AppConstants.POST_ID) long id) {
        PostDTO updatePost = postService.updatePost(postDTO, id);
        if (updatePost != null) {
            return new ResponseEntity<>(updatePost, HttpStatus.OK);
        }
        return new ResponseEntity<>(updatePost, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = AppConstants.POST_ID) long id) {
        boolean deletePost = postService.deletePost(id);
        if (deletePost) {
            return new ResponseEntity<>(deletePost, HttpStatus.OK);
        }
        return new ResponseEntity<>(deletePost, HttpStatus.NOT_FOUND);

    }

}
