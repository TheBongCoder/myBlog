package com.thebongcoder.blog.blogapp.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {

    @NotEmpty
    @Size(min = 2, message = "Email or Title can't be blank")
    private String userNameOrEmail;

    @NotEmpty
    @Size(min = 2, message = "password can't be blank")
    private String password;
}
