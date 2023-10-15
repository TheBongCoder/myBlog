package com.thebongcoder.blog.blogapp.dto;

import com.thebongcoder.blog.blogapp.enums.Roles;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpDTO {

    @NotEmpty
    @Size(min = 2, message = "Name can't be blank")
    private String name;

    @NotEmpty
    @Size(min = 2, message = "Username can't be blank")
    private String userName;

    @NotEmpty
    @Size(min = 2, message = "Email can't be blank")
    private String email;

    @NotEmpty
    @Size(min = 2, message = "password can't be blank")
    private String password;

    @NotEmpty
    @Size(min = 2, message = "password can't be blank")
    private String confirmPassword;

    @NotNull
    private Roles roles;
}
