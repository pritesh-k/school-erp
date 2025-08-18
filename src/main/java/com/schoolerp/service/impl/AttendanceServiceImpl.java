package com.schoolerp.service.impl;

import com.schoolerp.dto.request.AttendanceCreateDto;
import com.schoolerp.dto.request.AttendanceUpdateDto;
import com.schoolerp.dto.response.AttendancePercentageReportDto;
import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.entity.*;
import com.schoolerp.enums.AttendanceStatus;
import com.schoolerp.exception.DuplicateEntry;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.AttendanceMapper;
import com.schoolerp.repository.*;
import com.schoolerp.service.AttendanceService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repo;
    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;
    private final AttendanceMapper mapper;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    private final AcademicSessionRepository academicSessionRepository;

    @Override
    public List<AttendanceResponseDto> markBulk(List<AttendanceCreateDto> dtos, Long entityId, Long userId) {
        log.info("Marking bulk attendance for {} records", dtos.size());

        List<Attendance> attendances = new ArrayList<>();

        for (AttendanceCreateDto dto : dtos) {
            // Validate entities exist
            if (!studentRepo.existsById(dto.getStudentId())) {
                throw new ResourceNotFoundException("Student not found with ID: " + dto.getStudentId());
            }

            // Get section from student
            Optional<StudentEnrollment> studentEnrollmentData = studentEnrollmentRepository.findByStudent_Id(dto.getStudentId());
            if (studentEnrollmentData.isEmpty()) {
                throw new ResourceNotFoundException("Student has no enrollment record");
            }
            // Get teacher (use provided teacherId or get class teacher as default)
            Teacher teacher = teacherRepo.findById(entityId)
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + entityId));

            StudentEnrollment studentEnrollment = studentEnrollmentData.get();

            boolean alreadyRecorded =
                    repo.existsByStudentEnrollment_IdAndDate(studentEnrollment.getId(), dto.getDate());

            if (alreadyRecorded) {
                throw new DuplicateEntry("Attendance already marked for this student on " + dto.getDate());
            }

            // Create and save attendance
            Attendance attendance = Attendance.builder()
                    .studentEnrollment(studentEnrollment)
                    .date(dto.getDate())
                    .status(dto.getStatus())
                    .remarks(dto.getRemarks() != null ? dto.getRemarks() : "Marked for " + dto.getDate())
                    .recordedBy(teacher)
                    .build();

            attendance.setCreatedAt(java.time.Instant.now());
            attendance.setCreatedBy(userId);

            attendances.add(attendance);
        }

        List<Attendance> savedAttendances = repo.saveAll(attendances);
        log.info("Bulk attendance marked successfully for {} records", savedAttendances.size());

        return savedAttendances.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceResponseDto get(Long id) {
        Attendance attendance = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance with id " + id + " not found"));
        return mapper.toDto(attendance);
    }
    
    @Override
    public Page<AttendanceResponseDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public AttendanceResponseDto update(Long attendanceId, AttendanceUpdateDto dto, Long entityId, Long userId) {
        log.info("Updating attendance with ID: {}", attendanceId);

        Attendance attendance = repo.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID: " + attendanceId));

        boolean hasChanges = false;
        // Update fields
        if (dto.getStatus() != null) {
            attendance.setStatus(dto.getStatus());
            hasChanges = true;
        }

        if (dto.getRemarks() != null && !dto.getRemarks().isEmpty()) {
            attendance.setRemarks(dto.getRemarks());
            hasChanges = true;
        }
        if (hasChanges){
            attendance.setUpdatedAt(java.time.Instant.now());
            attendance.setUpdatedBy(userId);

            repo.save(attendance);
            log.info("Attendance updated successfully with ID: {}", attendance.getId());
        }
        return mapper.toDto(attendance);
    }

    @Override
    public void delete(Long attendanceId, Long entityId, Long userId, String role){
        log.info("Deleting attendance with ID: {}", attendanceId);

        if (!repo.existsById(attendanceId)) {
            throw new ResourceNotFoundException("Attendance not found with ID: " + attendanceId);
        }
        repo.deleteById(attendanceId);
        log.info("Attendance deleted successfully with ID: {}", attendanceId);
    }

    @Override
    public Page<AttendanceResponseDto> search(String academicSessionName, Long studentId, Long sectionId, Long classId, AttendanceStatus status, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        // Ensure academic session exists
        AcademicSession session = academicSessionRepository.findByName(academicSessionName)
                .orElseThrow(() -> new ResourceNotFoundException("Academic session not found: " + academicSessionName));

        // default date range to session if not provided
        LocalDate from = (startDate != null) ? startDate : session.getStartDate();
        LocalDate to = (endDate != null) ? endDate : session.getEndDate();

        Specification<Attendance> spec = (root, query, cb) -> {
            // fetch joins to prevent lazy loading
            root.fetch("studentEnrollment").fetch("student");
            root.fetch("studentEnrollment").fetch("schoolClass");
            root.fetch("studentEnrollment").fetch("section");
            root.fetch("studentEnrollment").fetch("academicSession");
            root.fetch("recordedBy", JoinType.LEFT);
            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            // join to studentEnrollment -> academicSession
            Join<Attendance, StudentEnrollment> seJoin = root.join("studentEnrollment");
            Join<StudentEnrollment, AcademicSession> aJoin = seJoin.join("academicSession");

            predicates.add(cb.equal(aJoin.get("name"), academicSessionName));
            predicates.add(cb.between(root.get("date"), from, to));

            if (studentId != null) {
                predicates.add(cb.equal(seJoin.get("student").get("id"), studentId));
            }
            if (sectionId != null) {
                predicates.add(cb.equal(seJoin.get("section").get("id"), sectionId));
            }
            if (classId != null) {
                predicates.add(cb.equal(seJoin.get("schoolClass").get("id"), classId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Attendance> page = repo.findAll(spec, pageable);

        List<AttendanceResponseDto> content = new ArrayList<>();
        for (Attendance a : page.getContent()) {
            content.add(mapper.toDto(a));
        }
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public AttendanceResponseDto createAttendance(String academicSessionName,
                                                  AttendanceCreateDto req,
                                                  Long recordedByUserId) {

        // validate academic session
        AcademicSession session = academicSessionRepository.findByName(academicSessionName)
                .orElseThrow(() -> new ResourceNotFoundException("Academic session not found: " + academicSessionName));

        LocalDate date = req.getDate();
        if (date.isBefore(session.getStartDate()) || date.isAfter(session.getEndDate())) {
            throw new IllegalArgumentException("Attendance date must be within the academic session dates");
        }

        // find the teacher (if user is teacher) - if not found, it's okay for ADMIN to record without teacher reference,
        // but if caller should be teacher we expect a teacher record
        Optional<Teacher> maybeTeacher = teacherRepo.findByUserId(recordedByUserId);

        // create for single student
        if (req.getStudentId() != null) {
            StudentEnrollment se = studentEnrollmentRepository.findByStudent_IdAndAcademicSession_Name(req.getStudentId(), academicSessionName)
                    .orElseThrow(() -> new ResourceNotFoundException("Student enrollment not found for given session"));

            // if classId or sectionId provided, ensure they match
            if (req.getClassId() != null && !req.getClassId().equals(se.getSchoolClass().getId())) {
                throw new IllegalArgumentException("Provided classId does not match student's enrollment");
            }
            if (req.getSectionId() != null && !req.getSectionId().equals(se.getSection().getId())) {
                throw new IllegalArgumentException("Provided sectionId does not match student's enrollment");
            }

            // duplicate check
            if (repo.existsByStudentEnrollment_IdAndDate(se.getId(), date)) {
                throw new DuplicateEntry("Attendance already recorded for this student on " + date);
            }

            Attendance a = Attendance.builder()
                    .studentEnrollment(se)
                    .date(date)
                    .status(req.getStatus())
                    .remarks(req.getRemarks())
                    .recordedBy(maybeTeacher.orElse(null))
                    .build();

            Attendance saved = repo.save(a);
            return mapper.toDto(saved);
        }

        // else: create for entire class+section (bulk)
        if (req.getClassId() != null && req.getSectionId() != null) {
            List<StudentEnrollment> enrollments = studentEnrollmentRepository
                    .findBySchoolClass_IdAndSection_IdAndAcademicSession_NameAndActiveTrue(req.getClassId(), req.getSectionId(), academicSessionName);

            if (enrollments.isEmpty()) {
                throw new ResourceNotFoundException("No student enrollments found for class/section in the session");
            }

            List<Attendance> toSave = new ArrayList<>();
            for (StudentEnrollment se : enrollments) {
                // skip duplicates silently (alternatively add to report)
                if (repo.existsByStudentEnrollment_IdAndDate(se.getId(), date)) {
                    continue;
                }
                Attendance a = Attendance.builder()
                        .studentEnrollment(se)
                        .date(date)
                        .status(req.getStatus())
                        .remarks(req.getRemarks())
                        .recordedBy(maybeTeacher.orElse(null))
                        .build();
                toSave.add(a);
            }

            if (toSave.isEmpty()) {
                throw new DuplicateEntry("Attendance already recorded for all students in the selected class/section for date " + date);
            }

            List<Attendance> saved = repo.saveAll(toSave);
            // Return the first saved entry as sample; you may prefer to return a report summarizing saved count
            return mapper.toDto(saved.get(0));
        }
        throw new IllegalArgumentException("Either studentId or (classId and sectionId) must be provided");
    }

    @Override
    public AttendancePercentageReportDto getAttendancePercentageReport(String academicSessionName) {
        AcademicSession session = academicSessionRepository.findByName(academicSessionName)
                .orElseThrow(() -> new ResourceNotFoundException("Academic Session not found: " + academicSessionName));

        List<Object[]> results = repo.countByStatusForSession(session.getId());

        long total = results.stream().mapToLong(r -> (Long) r[1]).sum();

        Map<AttendanceStatus, Double> percentageByStatus = new EnumMap<>(AttendanceStatus.class);
        for (AttendanceStatus status : AttendanceStatus.values()) {
            long count = results.stream()
                    .filter(r -> r[0] == status)
                    .mapToLong(r -> (Long) r[1])
                    .findFirst()
                    .orElse(0L);
            double percentage = total > 0 ? (count * 100.0) / total : 0.0;
            percentageByStatus.put(status, Math.round(percentage * 100.0) / 100.0);
        }

        AttendancePercentageReportDto report = new AttendancePercentageReportDto();
        report.setAcademicSessionName(session.getName());
        report.setTotalRecords(total);
        report.setPercentageByStatus(percentageByStatus);

        return report;
    }

}