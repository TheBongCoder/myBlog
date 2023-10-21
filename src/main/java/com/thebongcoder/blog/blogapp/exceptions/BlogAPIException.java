package com.thebongcoder.blog.blogapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class BlogAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;
}
