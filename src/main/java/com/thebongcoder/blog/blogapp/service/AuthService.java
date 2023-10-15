package com.thebongcoder.blog.blogapp.service;

import com.thebongcoder.blog.blogapp.dto.SignUpDTO;
import com.thebongcoder.blog.blogapp.entity.Role;
import com.thebongcoder.blog.blogapp.entity.User;
import com.thebongcoder.blog.blogapp.repository.RoleRepository;
import com.thebongcoder.blog.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean checkPasswordAndConfirmPassword(String password , String confirmPassword) {
        boolean passwordMatched = password.equals(confirmPassword);
        return passwordMatched;
    }

    public boolean existsByUserName(SignUpDTO signUpDTO) {
        boolean existsByUserName = userRepository.existsByUserName(signUpDTO.getUserName());
        return existsByUserName;
    }

    public boolean existsByEmail(SignUpDTO signUpDTO) {
        boolean existsByEmail = userRepository.existsByEmail(signUpDTO.getEmail().toLowerCase());
        return existsByEmail;
    }

    public String createUser(SignUpDTO signUpDTO) {
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setUserName(signUpDTO.getUserName());
        user.setEmail(signUpDTO.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role role = roleRepository.findByName(signUpDTO.getRoles());
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return "success";
    }

}
