package com.thebongcoder.blog.blogapp.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BlogAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;
}
