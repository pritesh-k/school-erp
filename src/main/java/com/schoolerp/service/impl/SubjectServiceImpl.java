package com.schoolerp.service.impl;

import com.schoolerp.dto.request.SubjectCreateDto;
import com.schoolerp.dto.request.SubjectUpdateDto;
import com.schoolerp.dto.response.SubjectResponseDto;
import com.schoolerp.entity.Subject;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.SubjectMapper;
import com.schoolerp.repository.SubjectRepository;
import com.schoolerp.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository repo;
    private final SubjectMapper mapper;

    @Override
    @Transactional
    public SubjectResponseDto create(SubjectCreateDto dto, Long userId) {
        Subject s = Subject.builder().code(dto.code()).category(dto.category()).build();
        s.setCreatedBy(userId);
        s.setCreatedAt(Instant.now());
        s.setActive(true);
        return mapper.toDto(repo.save(s));
    }

    @Override
    public SubjectResponseDto get(Long id) {
        return mapper.toDto(
                repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID " + id ))
        );
    }

    @Override
    public Page<SubjectResponseDto> list(Pageable pageable) {
        // Get a page of Subject entities from the repository
        Page<Subject> subjects = repo.findAll(pageable);

        // Convert each Subject entity to a SubjectResponseDto
        Page<SubjectResponseDto> dtoPage = subjects.map(mapper::toDto);

        // Return the page of DTOs
        return dtoPage;
    }


    @Override
    @Transactional
    public SubjectResponseDto update(Long id, SubjectUpdateDto dto, Long userId) {
        Subject subject = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with ID " + id + " not found"));

        if (dto.code() != null && !subject.getCode().equals(dto.code())) {
            boolean codeExists = repo.existsByCode(dto.code());
            if (codeExists) {
                throw new IllegalArgumentException("Subject code '" + dto.code() + "' is already in use.");
            }
            subject.setCode(dto.code());
        }

        if (dto.category() != null) {
            subject.setCategory(dto.category());
        }
        subject.setUpdatedAt(Instant.now());
        subject.setUpdatedBy(userId);

        return mapper.toDto(repo.save(subject));
    }



    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Subject with ID " + id + " not found.");
        }
        repo.deleteById(id);
    }

}