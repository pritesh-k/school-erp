package com.schoolerp.service.impl;

import com.schoolerp.dto.request.StudentEnrollmentCreateDTO;
import com.schoolerp.dto.request.StudentEnrollmentDTO;
import com.schoolerp.dto.request.StudentEnrollmentUpdateDTO;
import com.schoolerp.dto.response.enrollments.StudentEnrollmentResDto;
import com.schoolerp.entity.*;
import com.schoolerp.exception.DuplicateEntry;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.StudentEnrollmentMapper;
import com.schoolerp.repository.*;
import com.schoolerp.service.StudentEnrollmentService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public StudentEnrollmentDTO getByStudentIdAndAcademicSession(Long studentId, String academicSession) {
        if (!academicSessionRepository.existsByName(academicSession)){
            throw new ResourceNotFoundException("Academic session doesn't exist");
        }
        StudentEnrollment entity = repo.findByStudent_IdAndAcademicSession_Name(studentId, academicSession)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        return mapper.toDto(entity);
    }

    @Override
    public StudentEnrollmentDTO create(StudentEnrollmentCreateDTO dto, Long createdByUserId) {

        if (repo.existsByStudent_IdAndAcademicSession_Name(dto.getStudentId(), dto.getAcademicSessionName())){
            throw new DuplicateEntry("Enrollment already exists in selected academic session");
        }

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
        AcademicSession academicSession = academicSessionRepository.findByName(dto.getAcademicSessionName())
                .orElseThrow(() -> new ResourceNotFoundException("Academic Session not found"));

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
        studentEnrollment.setActive(true);
        studentEnrollment.setStudent(student.get());
        studentEnrollment.setCreatedBy(createdByUserId);
        studentEnrollment.setCreatedAt(Instant.now());

        return mapper.toDto(repo.save(studentEnrollment));
    }

    @Override
    public StudentEnrollmentDTO update(StudentEnrollmentUpdateDTO dto, Long updatedByUserId, String academicSessionName) {

        StudentEnrollment existing = repo.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

        // Validate Academic Session
        Optional<AcademicSession> academicSession = academicSessionRepository.findByName(academicSessionName);
        if (academicSession.isEmpty()){
            throw new ResourceNotFoundException("Academic session not found");
        }
        boolean hasChanges = false;

        // Update Class
        if (dto.getSchoolClassId() != null &&
                dto.getSchoolClassId() > 0 &&
                !Objects.equals(dto.getSchoolClassId(), existing.getSchoolClass().getId())) {

            SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("School Class not found"));

            existing.setSchoolClass(schoolClass);
            hasChanges = true;
        }

        // Update Section
        if (dto.getSectionId() != null &&
                dto.getSectionId() > 0 &&
                !Objects.equals(dto.getSectionId(), existing.getSection().getId())) {

            Section section = sectionRepository.findById(dto.getSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

            if (!section.getSchoolClass().getId().equals(dto.getSchoolClassId())) {
                throw new ResourceNotFoundException("Section does not belong to the specified School Class");
            }

            existing.setSection(section);
            hasChanges = true;
        }

        if (hasChanges) {
            existing.setUpdatedAt(Instant.now());
            existing.setUpdatedBy(updatedByUserId);
            existing = repo.save(existing);
        }

        return mapper.toDto(existing);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Long getTotalStudentCount(String academicSessionName, Long sectionId, Long classId, Long studentId) {
        return repo.count((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Join academic session and filter by name
            Join<StudentEnrollment, AcademicSession> sessionJoin = root.join("academicSession");
            predicates.add(cb.equal(sessionJoin.get("name"), academicSessionName));

            if (sectionId != null) {
                predicates.add(cb.equal(root.get("section").get("id"), sectionId));
            }
            if (classId != null) {
                predicates.add(cb.equal(root.get("schoolClass").get("id"), classId));
            }
            if (studentId != null) {
                predicates.add(cb.equal(root.get("student").get("id"), studentId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    public Page<StudentEnrollmentResDto> getEnrollments(
            String academicSessionName,
            Long sectionId,
            Long classId,
            Long studentId,
            Pageable pageable) {

        Specification<StudentEnrollment> spec = (root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            // Academic Session filter
            if (hasText(academicSessionName)) {
                Join<StudentEnrollment, AcademicSession> sessionJoin = root.join("academicSession");
                filters.add(cb.equal(sessionJoin.get("name"), academicSessionName));
            } else {
                throw new IllegalArgumentException("Academic session is required");
            }

            // Section filter
            if (sectionId != null) {
                filters.add(cb.equal(root.get("section").get("id"), sectionId));
            }

            // Class filter
            if (classId != null) {
                filters.add(cb.equal(root.get("schoolClass").get("id"), classId));
            }

            // Student filter
            if (studentId != null) {
                filters.add(cb.equal(root.get("student").get("id"), studentId));
            }

            Predicate finalPredicate = cb.and(filters.toArray(new Predicate[0]));
            return finalPredicate;
        };

        Page<StudentEnrollment> pageResult = repo.findAll(spec, pageable);

        return pageResult.map(mapper::toListEnroll);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

