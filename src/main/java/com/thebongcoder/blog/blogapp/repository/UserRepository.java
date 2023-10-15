package com.thebongcoder.blog.blogapp.repository;

import com.thebongcoder.blog.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUserNameOrEmail(String userName, String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String userName);
}
