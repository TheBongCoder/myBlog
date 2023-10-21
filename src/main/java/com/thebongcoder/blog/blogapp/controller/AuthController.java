package com.thebongcoder.blog.blogapp.controller;

import com.thebongcoder.blog.blogapp.dto.JwtAuthResponseDTO;
import com.thebongcoder.blog.blogapp.dto.LoginDTO;
import com.thebongcoder.blog.blogapp.dto.SignUpDTO;
import com.thebongcoder.blog.blogapp.security.JwtTokenProvider;
import com.thebongcoder.blog.blogapp.service.AuthService;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ResponseHandler responseHandler;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("signIn")
    public ResponseEntity<Object> signInUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserNameOrEmail(), loginDTO.getPassword().toLowerCase()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get Token from tokenProvider

        String token = jwtTokenProvider.generateToken(authentication);
        JwtAuthResponseDTO jwtAuthResponseDTO = new JwtAuthResponseDTO();
        jwtAuthResponseDTO.setAccessToken(token);
        return responseHandler.generateResponse(jwtAuthResponseDTO, "User signIn successfully", true, HttpStatus.OK);
    }

    @PostMapping("signUp")
    public ResponseEntity<Object> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
//        match password
        boolean passwordMatched = authService.checkPasswordAndConfirmPassword(signUpDTO.getPassword(), signUpDTO.getConfirmPassword());
        if (passwordMatched) {
            return responseHandler.generateResponse("", "Password and confirm password are not identical", true, HttpStatus.NOT_ACCEPTABLE);
        }
        //user name
        boolean existsByUserName = authService.existsByUserName(signUpDTO);
        if (existsByUserName) {
            return responseHandler.generateResponse("", signUpDTO.getUserName() + " username already exists", true, HttpStatus.NOT_ACCEPTABLE);
        }
        //email
        boolean existsByEmail = authService.existsByEmail(signUpDTO);
        if (existsByEmail) {
            return responseHandler.generateResponse("", signUpDTO.getEmail() + " email already exists", true, HttpStatus.NOT_ACCEPTABLE);
        }
        String userAdded = authService.createUser(signUpDTO);
        return responseHandler.generateResponse("", "User added successfully", true, HttpStatus.CREATED);
    }
}
