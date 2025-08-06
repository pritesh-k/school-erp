package com.schoolerp.service.impl;

import com.schoolerp.dto.request.StudentEnrollmentDTO;
import com.schoolerp.entity.*;
import com.schoolerp.exception.DuplicateEntry;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.StudentEnrollmentMapper;
import com.schoolerp.repository.*;
import com.schoolerp.service.ClassService;
import com.schoolerp.service.StudentEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentEnrollmentServiceImpl implements StudentEnrollmentService {

    private final StudentEnrollmentRepository repo;
    private final StudentEnrollmentMapper mapper;
    private final SectionRepository sectionRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    private final AcademicSessionRepository academicSessionRepository;

    @Override
    public Page<StudentEnrollmentDTO> getAll(Long sessionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return repo.findAllByAcademicSession_Id(sessionId, pageable)
                .map(mapper::toDto);
    }

    @Override
    public StudentEnrollmentDTO getById(Long id) {
        StudentEnrollment entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        return mapper.toDto(entity);
    }

    @Override
    public StudentEnrollmentDTO create(StudentEnrollmentDTO dto) {

        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(dto.getSectionId());
        if (schoolClass.isEmpty()) {
            throw new ResourceNotFoundException("School Class not found");
        }
        SchoolClass schoolClass1 = schoolClass.get();

        Optional<Section> section = sectionRepository.findById(dto.getSectionId());
        if (section.isEmpty()) {
            throw new ResourceNotFoundException("Section not found");
        }
        Section section1 = section.get();
        if (!section1.getSchoolClass().getId().equals(dto.getSchoolClassId())) {
            throw new ResourceNotFoundException("Section does not belong to the specified School Class");
        }

        StudentEnrollment studentEnrollment = new StudentEnrollment();
        AcademicSession academicSession = academicSessionRepository.findById(dto.getAcademicSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Academic Session not found"));

        if (repo.existsByStudent_IdAndAcademicSession_Id(dto.getStudentId(), dto.getAcademicSessionId())) {
            throw new DuplicateEntry("Student is already enrolled in this academic session");
        }
        if (!academicSession.isActive()){
            throw new ResourceNotFoundException("Academic Session is not active");
        }
        Optional<Student> student = studentRepository.findById(dto.getStudentId());
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Student not found");
        }

        studentEnrollment.setAcademicSession(academicSession);
        studentEnrollment.setSection(section1);
        studentEnrollment.setSchoolClass(schoolClass1);
        studentEnrollment.setRollNumber(dto.getRollNumber());
        studentEnrollment.setActive(dto.isActive());
        studentEnrollment.setStudent(student.get());
        // More fields as needed
        return mapper.toDto(repo.save(studentEnrollment));
    }

    @Override
    public StudentEnrollmentDTO update(Long id, StudentEnrollmentDTO dto) {
        StudentEnrollment existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(dto.getSectionId());
        if (schoolClass.isEmpty()) {
            throw new ResourceNotFoundException("School Class not found");
        }
        SchoolClass schoolClass1 = schoolClass.get();
        if (!schoolClass1.getId().equals(dto.getSchoolClassId())) {
            throw new ResourceNotFoundException("School Class ID mismatch");
        }
        Optional<Section> section = sectionRepository.findById(dto.getSectionId());
        if (section.isEmpty()) {
            throw new ResourceNotFoundException("Section not found");
        }
        Section section1 = section.get();
        if (!section1.getSchoolClass().getId().equals(dto.getSchoolClassId())) {
            throw new ResourceNotFoundException("Section does not belong to the specified School Class");
        }
        if (academicSessionRepository.existsById(dto.getAcademicSessionId())) {
            existing.setAcademicSession(academicSessionRepository.findById(dto.getAcademicSessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Academic Session not found")));
        } else {
            throw new ResourceNotFoundException("Academic Session not found");
        }
        existing.setSection(section1);
        existing.setSchoolClass(schoolClass1);
        existing.setRollNumber(dto.getRollNumber());
        existing.setActive(dto.isActive());
        return mapper.toDto(repo.save(existing));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}

