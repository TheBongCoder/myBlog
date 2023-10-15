package com.thebongcoder.blog.blogapp.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title must have atleast 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description must have atleast 2 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDTO> commentDTOs;
}
