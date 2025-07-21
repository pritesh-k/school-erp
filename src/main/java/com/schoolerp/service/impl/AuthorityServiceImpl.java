//package com.schoolerp.service.impl;
//
//import com.schoolerp.dto.request.AuthorityCreateDto;
//import com.schoolerp.entity.Authority;
//import com.schoolerp.entity.User;
//import com.schoolerp.entity.UserAuthority;
//import com.schoolerp.exception.ResourceNotFoundException;
//import com.schoolerp.repository.AuthorityRepository;
//import com.schoolerp.repository.UserAuthorityRepository;
//import com.schoolerp.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class AuthorityServiceImpl {
//
//    private final UserAuthorityRepository userAuthorityRepository;
//    private final AuthorityRepository authorityRepository;
//    private final UserRepository userRepository;
//
//    public void grantAuthority(Long userId, String authorityName, Long grantedBy) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        Authority authority = authorityRepository.findByName(authorityName)
//                .orElseThrow(() -> new ResourceNotFoundException("Authority not found"));
//
//        // Check if already granted
//        if (userAuthorityRepository.existsByUserIdAndAuthorityName(userId, authorityName)) {
//            throw new IllegalArgumentException("Authority already granted");
//        }
//
//        UserAuthority userAuthority = new UserAuthority();
//        userAuthority.setUser(user);
//        userAuthority.setAuthority(authority);
//        userAuthority.setGrantedBy(grantedBy);
//        userAuthority.setGrantedAt(LocalDateTime.now());
//
//        userAuthorityRepository.save(userAuthority);
//    }
//
//    public void revokeAuthority(Long userId, String authorityName) {
//        userAuthorityRepository.deleteByUserIdAndAuthorityName(userId, authorityName);
//    }
//
//    public List<String> getUserAuthorities(Long userId) {
//        return userAuthorityRepository.findByUserId(userId)
//                .stream()
//                .map(ua -> ua.getAuthority().getName())
//                .collect(Collectors.toList());
//    }
//
//    public void grantBulkAuthorities(Long userId, List<String> authorityNames, Long grantedBy) {
//        for (String authorityName : authorityNames) {
//            try {
//                grantAuthority(userId, authorityName, grantedBy);
//            } catch (Exception e) {
//                log.warn("Failed to grant authority {} to user {}", authorityName, userId);
//            }
//        }
//    }
//
//    public Authority create(AuthorityCreateDto dto) {
//        Authority authority = new Authority();
//        authority.setName(dto.getName());
//        authority.setDescription(dto.getDescription());
//        authority.setCategory(dto.getCategory());
//        return authorityRepository.save(authority);
//    }
//
//    public List<Authority> getAll() {
//        return authorityRepository.findAll();
//    }
//
//    public Authority update(Long id, AuthorityCreateDto dto) {
//        Authority authority = authorityRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Authority not found"));
//
//        authority.setName(dto.getName());
//        authority.setDescription(dto.getDescription());
//        authority.setCategory(dto.getCategory());
//
//        return authorityRepository.save(authority);
//    }
//
//    public void delete(Long id) {
//        authorityRepository.deleteById(id);
//    }
//}
//
