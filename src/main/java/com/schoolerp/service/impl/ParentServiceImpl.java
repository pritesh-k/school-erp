package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ParentCreateDto;
import com.schoolerp.dto.request.ParentUpdateDto;
import com.schoolerp.dto.request.RegisterRequest;
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

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepo;
    private final ParentMapper mapper;
    private final AuthServiceImpl authService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ParentResponseDto create(ParentCreateDto dto, Long createdById) {
        Optional<Parent> parentExists = parentRepo.findByPhoneOrEmail(dto.getPhone(), dto.getEmail());
        Parent savedParent = null;
        if (parentExists.isEmpty()) {
            RegisterRequest req = new RegisterRequest(
                    dto.getPhone(),  // username = phone number
                    dto.getEmail(),
                    dto.getPhone(),  // default password = phone number
                    Role.PARENT,
                    dto.getFirstName(),
                    dto.getLastName(),
                    createdById  // created by current user
            );

            authService.register(req);
            User savedUser = authService.getUserByUsername(dto.getPhone()); // Fetch the saved user by username

            Parent parent = Parent.builder()
                    .user(savedUser)  // or fill from dto, but NOT from student.getUser(), since student not created yet
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .occupation(dto.getOccupation())
                    .relation(dto.getRelation())
                    .build();
            parent.setCreatedAt(java.time.Instant.now());
            parent.setCreatedBy(createdById);
            savedParent = parentRepo.save(parent);

            savedUser.setEntityId(savedParent.getId());
            userRepository.save(savedUser);
        } else {
            savedParent = parentExists.get();
        }
        return mapper.toDto(savedParent);
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
    public ParentResponseDto update(Long id, ParentUpdateDto pDto) {
        Parent parent = parentRepo.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Parent not found with id: " + id));
        boolean hasChanges = false;
        if (isChanged(pDto.getFirstName(), parent.getFirstName())) {
            parent.setFirstName(pDto.getFirstName().trim());
            hasChanges = true;
        }

        if (isChanged(pDto.getLastName(), parent.getLastName())) {
            parent.setLastName(pDto.getLastName().trim());
            hasChanges = true;
        }

        if (isChanged(pDto.getPhone(), parent.getPhone())) {
            parent.setPhone(pDto.getPhone().trim());
            hasChanges = true;
        }

        if (isChanged(pDto.getOccupation(), parent.getOccupation())) {
            parent.setOccupation(pDto.getOccupation().trim());
            hasChanges = true;
        }

        if (isChanged(pDto.getEmail(), parent.getEmail())) {
            parent.setEmail(pDto.getEmail().trim());
            hasChanges = true;
        }

        if (pDto.getRelation() != null && !pDto.getRelation().equals(parent.getRelation())) {
            parent.setRelation(pDto.getRelation());
            hasChanges = true;
        }
        if(hasChanges){
            parentRepo.save(parent);
        }

        return mapper.toDto(parentRepo.save(parent));
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

    @Override
    public Parent getReferenceById(Long id){
        return parentRepo.getReferenceById(id);
    }

    private boolean isChanged(String newValue, String oldValue) {
        if (newValue == null || newValue.trim().isEmpty()) return false;
        return !newValue.trim().equals(oldValue);
    }
}