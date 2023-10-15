package com.thebongcoder.blog.blogapp.controller;

import com.thebongcoder.blog.blogapp.dto.LoginDTO;
import com.thebongcoder.blog.blogapp.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ResponseHandler responseHandler;

    @PostMapping("signIn")
    public ResponseEntity<Object> signInUser(@RequestBody LoginDTO loginDTO) {
        Authentication authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserNameOrEmail(), loginDTO.getPassword().toLowerCase()));
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        return responseHandler.generateResponse("", "User signIn successfully", true, HttpStatus.OK);
    }
}
