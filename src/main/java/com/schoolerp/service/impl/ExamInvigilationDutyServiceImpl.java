package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ExamInvigilationDutyCreateDto;
import com.schoolerp.dto.request.ExamInvigilationDutyUpdateDto;
import com.schoolerp.dto.response.ExamInvigilationDutyResponseDto;
import com.schoolerp.entity.ExamInvigilationDuty;
import com.schoolerp.entity.Teacher;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.ExamInvigilationDutyMapper;
import com.schoolerp.repository.ExamInvigilationDutyRepository;
import com.schoolerp.repository.TeacherRepository;
import com.schoolerp.service.ExamInvigilationDutyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExamInvigilationDutyServiceImpl implements ExamInvigilationDutyService {
    private final ExamInvigilationDutyRepository repo;
    private final ExamInvigilationDutyMapper mapper;

    private final TeacherRepository teacherRepository;

    @Override
    @Transactional
    public ExamInvigilationDutyResponseDto create(ExamInvigilationDutyCreateDto dto) {
        ExamInvigilationDuty duty = mapper.toEntity(dto);
        return mapper.toDto(repo.save(duty));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamInvigilationDutyResponseDto getById(Long id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Duty not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamInvigilationDutyResponseDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ExamInvigilationDutyResponseDto updated(Long id, ExamInvigilationDutyUpdateDto dto) {
        ExamInvigilationDuty duty = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Duty not found"));
        if (dto.getRole() != null) {
            duty.setRole(dto.getRole());
        }
        if (dto.getTeacherId() != null && dto.getTeacherId() != 0 && !dto.getTeacherId().equals(duty.getTeacher().getId())) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
            duty.setTeacher(teacher);
        }
        return mapper.toDto(repo.save(duty));
    }
}

