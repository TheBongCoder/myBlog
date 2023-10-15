package com.thebongcoder.blog.blogapp.repository;

import com.thebongcoder.blog.blogapp.entity.Role;
import com.thebongcoder.blog.blogapp.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(Roles role);
}
