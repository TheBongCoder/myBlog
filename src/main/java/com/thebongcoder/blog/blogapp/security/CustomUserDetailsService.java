package com.thebongcoder.blog.blogapp.security;

import com.thebongcoder.blog.blogapp.entity.Role;
import com.thebongcoder.blog.blogapp.entity.User;
import com.thebongcoder.blog.blogapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userName or email: " + userNameOrEmail);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthority(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName().name());
            authorities.add(simpleGrantedAuthority);
        }
        return authorities;
    }

}
