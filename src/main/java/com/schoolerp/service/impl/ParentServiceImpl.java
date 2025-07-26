package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ParentCreateDto;
import com.schoolerp.dto.response.ParentResponseDto;
import com.schoolerp.entity.Parent;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.User;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.DuplicateEntry;
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
    public ParentResponseDto create(ParentCreateDto dto, Long createdById, User savedUser) {
        Parent parent = Parent.builder()
                .user(savedUser)  // or fill from dto, but NOT from student.getUser(), since student not created yet
                .firstName(dto.getFatherFirstName())
                .lastName(dto.getFatherLastName())
                .phone(dto.getFatherPhone())
                .email(dto.getEmail())
                .occupation(dto.getFatherOccupation())
                .relation(dto.getRelation())
                .build();
        parent.setCreatedAt(java.time.Instant.now());
        parent.setCreatedBy(createdById);

        Parent saved = parentRepo.save(parent);
        return mapper.toDto(saved);
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
        Parent p = parentRepo.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Parent not found with id: " + id));
        p.setFirstName(dto.getFatherFirstName());
        p.setLastName(dto.getFatherLastName());
        p.setPhone(dto.getFatherPhone());
        p.setEmail(dto.getEmail());
        p.setOccupation(dto.getFatherOccupation());
        p.setRelation(dto.getRelation());
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

    public Parent getReferenceById(Long id){
        return parentRepo.getReferenceById(id);
    }

}