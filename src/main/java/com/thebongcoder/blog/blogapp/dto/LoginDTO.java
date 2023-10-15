package com.thebongcoder.blog.blogapp.dto;

import lombok.Data;

@Data
public class LoginDTO {


    private String userNameOrEmail;

    private String password;
}
