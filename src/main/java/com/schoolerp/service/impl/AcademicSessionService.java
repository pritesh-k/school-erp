package com.schoolerp.service.impl;

import com.schoolerp.entity.AcademicSession;
import com.schoolerp.enums.SessionStatus;
import com.schoolerp.exception.DuplicateEntry;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.AcademicSessionMapper;
import com.schoolerp.newdtos.academic.AcademicSessionCreateDto;
import com.schoolerp.newdtos.academic.AcademicSessionResponseDto;
import com.schoolerp.newdtos.academic.AcademicSessionUpdateDto;
import com.schoolerp.repository.AcademicSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class AcademicSessionService {
    private final AcademicSessionRepository repo;
    private final AcademicSessionMapper mapper;

    public AcademicSessionService(AcademicSessionRepository repo, AcademicSessionMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    public Page<AcademicSessionResponseDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    public AcademicSessionResponseDto get(Long id) {
        return mapper.toDto(
                repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Session not found")));
    }

    @Transactional
    public AcademicSessionResponseDto create(AcademicSessionCreateDto dto) {
        if (repo.existsByName(dto.name())) {
            throw new DuplicateEntry("Session with name already exists");
        }

        AcademicSession session = mapper.toEntity(dto);
        session.setCurrent(false);
        session.setStatus(SessionStatus.UPCOMING);
        AcademicSession saved = repo.save(session);
        return mapper.toDto(saved);
    }

    @Transactional
    public AcademicSessionResponseDto activateSession(Long sessionId) {
        Optional<AcademicSession> session = repo.findById(sessionId);
        if (session.isEmpty()){
            throw new ResourceNotFoundException("Session not found");
        }
        //Deactivate old one
        Optional<AcademicSession> oldActiveObject = repo.findByIsCurrentTrue();
        if (oldActiveObject.isPresent()){
            AcademicSession oldActive = oldActiveObject.get();
            oldActive.setActive(false);
            oldActive.setStatus(SessionStatus.COMPLETED);
            oldActive.setUpdatedAt(Instant.now());
            //oldActive.setUpdatedBy();
            repo.save(oldActive);
        }
        AcademicSession academicSession = session.get();
        academicSession.setCurrent(true);
        academicSession.setStatus(SessionStatus.ACTIVE);
        academicSession.setUpdatedAt(Instant.now());
        AcademicSession saved = repo.save(academicSession);

        return mapper.toDto(saved);
    }

    @Transactional
    public AcademicSessionResponseDto update(Long id, AcademicSessionUpdateDto dto) {
        AcademicSession entity = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Session not found"));
        // Uniqueness check
        if (!entity.getName().equals(dto.name()) && repo.existsByName(dto.name()))
            throw new DuplicateEntry("Session with name already exists");
        mapper.updateEntityFromDto(dto, entity);

        AcademicSession updated = repo.save(entity);
        return mapper.toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        AcademicSession entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        repo.delete(entity);
    }

    public AcademicSessionResponseDto getActive() {
        Optional<AcademicSession> oldActiveObject = repo.findByIsCurrentTrue();
        if (!oldActiveObject.isPresent()){
            throw new ResourceNotFoundException("No active session found");
        }
        return mapper.toDto(oldActiveObject.get());
    }
}
