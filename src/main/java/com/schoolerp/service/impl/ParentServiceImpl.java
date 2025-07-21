package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ParentCreateDto;
import com.schoolerp.dto.response.ParentResponseDto;
import com.schoolerp.entity.Parent;
import com.schoolerp.entity.User;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.ParentMapper;
import com.schoolerp.repository.ParentRepository;
import com.schoolerp.repository.UserRepository;
import com.schoolerp.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepo;
    private final UserRepository userRepo;
    private final ParentMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public ParentResponseDto create(ParentCreateDto dto) {
        if (userRepo.existsByUsername(dto.username()))
            throw new IllegalArgumentException("Username exists");
        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .password(encoder.encode(dto.password()))
                .role(Role.PARENT)
                .build();
        user = userRepo.save(user);

        Parent parent = Parent.builder()
                .user(user)
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .phone(dto.phone())
                .email(dto.email())
                .occupation(dto.occupation())
                .relation(dto.relation())
                .build();
        return mapper.toDto(parentRepo.save(parent));
    }

    @Override
    public ParentResponseDto get(Long id) {
        return mapper.toDto(parentRepo.findById(id).orElseThrow());
    }

    @Override
    public Page<ParentResponseDto> list(Pageable pageable) {
        return parentRepo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional
    public ParentResponseDto update(Long id, ParentCreateDto dto) {
        Parent p = parentRepo.findById(id).orElseThrow();
        p.setFirstName(dto.firstName());
        p.setLastName(dto.lastName());
        p.setPhone(dto.phone());
        p.setEmail(dto.email());
        p.setOccupation(dto.occupation());
        p.setRelation(dto.relation());
        return mapper.toDto(parentRepo.save(p));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        parentRepo.deleteById(id);
    }

    @Override
    public Parent getByUserId(Long id) {
        //parentRepo.getByUserId
        return null;
    }
}