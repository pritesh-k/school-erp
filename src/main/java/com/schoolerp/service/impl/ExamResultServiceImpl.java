package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ExamResultCreateDto;
import com.schoolerp.dto.response.ExamResultResponseDto;
import com.schoolerp.entity.ExamResult;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.ExamResultMapper;
import com.schoolerp.repository.*;
import com.schoolerp.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamResultRepository repo;
    private final ExamResultMapper mapper;
    private final StudentRepository studentRepo;
    private final ExamRepository examRepo;
    private final SubjectRepository subjectRepo;

    @Override
    @Transactional
    public ExamResultResponseDto create(ExamResultCreateDto dto) {
        ExamResult er = ExamResult.builder()
                .student(studentRepo.getReferenceById(dto.studentId()))
                .exam(examRepo.getReferenceById(dto.examId()))
                .subject(subjectRepo.getReferenceById(dto.subjectId()))
                .score(dto.score())
                .build();
        return mapper.toDto(repo.save(er));
    }

    @Override
    public ExamResultResponseDto get(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }

    @Override
    public Page<ExamResultResponseDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional
    public ExamResultResponseDto update(Long id, ExamResultCreateDto dto) {
        ExamResult er = repo.findById(id).orElseThrow();
        er.setStudent(studentRepo.getReferenceById(dto.studentId()));
        er.setExam(examRepo.getReferenceById(dto.examId()));
        er.setSubject(subjectRepo.getReferenceById(dto.subjectId()));
        er.setScore(dto.score());
        return mapper.toDto(repo.save(er));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}