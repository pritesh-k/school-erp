package com.schoolerp.service.impl;

import com.schoolerp.dto.request.SectionSubjectAssignmentCreateDto;
import com.schoolerp.dto.response.SectionSubjectAssignmentResponseDto;
import com.schoolerp.entity.AcademicSession;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.SectionSubjectAssignment;
import com.schoolerp.entity.Subject;
import com.schoolerp.exception.DuplicateEntry;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.exception.ValidationException;
import com.schoolerp.mapper.AssignmentMapper;
import com.schoolerp.repository.AcademicSessionRepository;
import com.schoolerp.repository.SectionRepository;
import com.schoolerp.repository.SectionSubjectAssignmentRepository;
import com.schoolerp.repository.SubjectRepository;
import com.schoolerp.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SectionSubjectAssignmentService {

    @Autowired
    private SectionRepository sectionRepo;
    @Autowired
    private SubjectRepository subjectRepo;
    @Autowired
    private SectionSubjectAssignmentRepository repo;
    @Autowired
    private AssignmentMapper mapper;
    @Autowired
    AcademicSessionRepository academicSessionRepository;

    @Autowired
    TimetableService timetableService;

    @Transactional
    public void create(SectionSubjectAssignmentCreateDto dto, Long sectionId, String academicSessionName, Long userId) {

        Optional<AcademicSession> academicSession = academicSessionRepository.findByName(academicSessionName);
        if (academicSession.isEmpty()){
            throw new ResourceNotFoundException("Academic Session is not present");
        }

        if (repo.findBySection_IdAndSubject_Id(sectionId, dto.getSubjectId()).isPresent()) {
            throw new DuplicateEntry("This subject is already assigned to the section");
        }

        Section section = sectionRepo.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        Subject subject = subjectRepo.findById(dto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        boolean mandatory = Boolean.TRUE.equals(dto.getMandatory());
        SectionSubjectAssignment assignment = SectionSubjectAssignment.builder()
                .section(section)
                .subject(subject)
                .isMandatory(mandatory)
                .weeklyHours(dto.getWeeklyHours())
                .academicSession(academicSession.get())
                .build();
        assignment.setActive(true);
        assignment.setDeleted(false);
        assignment.setCreatedBy(userId);

        timetableService.createTimetableForSubject(userId, assignment.getId());
        repo.save(assignment);
    }

    public Page<SectionSubjectAssignmentResponseDto> listBySectionId(Long sectionId, Pageable pageable) {
        Page<SectionSubjectAssignment> paged =  repo.findBySectionId(sectionId, pageable);
        return paged.map(mapper::toDto);
    }

    @Transactional
    public void delete(Long assignmentId) {
        Optional<SectionSubjectAssignment> assignment = repo.findById(assignmentId);
        if (assignment.isEmpty()) {
            throw new ResourceNotFoundException("Assignment not found");
        }
        SectionSubjectAssignment sectionSubjectAssignment = assignment.get();
        sectionSubjectAssignment.setDeleted(true);
        sectionSubjectAssignment.setActive(false);
        repo.save(sectionSubjectAssignment);
    }
}
