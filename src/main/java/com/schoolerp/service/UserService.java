package com.schoolerp.service;

import com.schoolerp.entity.User;
import com.schoolerp.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    User getUserById(Long id);
    Page<User> getAllUsers(Pageable pageable);
    Page<User> getUsersByRole(Role role, Pageable pageable);
    User getUserByUsername(String username);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    PasswordEncoder getPasswordEncoder();
}