package com.schoolerp.service.impl;

import com.schoolerp.entity.User;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.repository.UserRepository;
import com.schoolerp.service.StudentService;
import com.schoolerp.service.TeacherService;
import com.schoolerp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TeacherService teacherService;
    private final StudentService studentService;
    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(existingUser);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> getUsersByRole(Role role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void validateUserAccess(Long userId, Long entityId, Role fromString){
        try {
            switch (fromString) {
                case TEACHER -> teacherService.existsByIdAndUser_Id(entityId, userId);
                case STUDENT -> studentService.existsByIdAndUser_Id(entityId, userId);
                default -> throw new UnauthorizedException("Invalid role for access validation: " + fromString);
            }
        } catch (Exception e) {
            throw new UnauthorizedException("Access validation failed for user ID: " + userId + " with entity ID: " + entityId, e);
        }
    }
}