package com.thebongcoder.blog.blogapp.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PostDTO {

    private Long id;

    private String title;

    private String description;

    private String content;
}
