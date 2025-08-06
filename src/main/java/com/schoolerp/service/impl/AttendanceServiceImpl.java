package com.schoolerp.service.impl;

import com.schoolerp.dto.request.AttendanceCreateDto;
import com.schoolerp.dto.request.AttendanceUpdateDto;
import com.schoolerp.dto.response.AttendanceResponseDto;
import com.schoolerp.dto.response.attendance.AttendancePercentageDto;
import com.schoolerp.dto.response.attendance.AttendanceSummaryDto;
import com.schoolerp.dto.response.attendance.MonthlyAttendanceReportDto;
import com.schoolerp.dto.response.attendance.WeeklyAttendanceReportDto;
import com.schoolerp.entity.*;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.enums.AttendanceStatus;
import com.schoolerp.exception.DuplicateEntry;
import com.schoolerp.exception.NoChangesDetectedException;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.AttendanceMapper;
import com.schoolerp.repository.*;
import com.schoolerp.service.AttendanceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repo;
    private final StudentRepository studentRepo;
    private final SectionRepository sectionRepo;
    private final TeacherRepository teacherRepo;
    private final AttendanceMapper mapper;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    private final SchoolClassRepository classRepository;

    @Override
    @Transactional
    public AttendanceResponseDto markDateAttendance(Long studentId,
                                                     AttendanceStatus attendanceStatus,
                                                     String remarks,
                                                     Long teacherId, LocalDate date, Long userId) {
        log.info("Marking attendance for student ID: {} on date: {} with status: {}",
                studentId, date, attendanceStatus);

        // Validate student exists
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        // Get section from student
        Optional<StudentEnrollment> studentEnrollmentData = studentEnrollmentRepository.findByStudent_Id(studentId);
        if (studentEnrollmentData.isEmpty()) {
            throw new ResourceNotFoundException("Student has no enrollment record");
        }
        // Get teacher (use provided teacherId or get class teacher as default)
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherId));
        StudentEnrollment studentEnrollment = studentEnrollmentData.get();

        boolean alreadyRecorded = repo.existsByStudentEnrollment_IdAndDate(studentEnrollment.getId(), date);

        if (alreadyRecorded) {
            throw new DuplicateEntry("Attendance already marked for this student on " + date);
        }

        // Create and save attendance
        Attendance attendance = Attendance.builder()
                .studentEnrollment(studentEnrollment)
                .date(date)
                .status(attendanceStatus)
                .remarks(remarks != null ? remarks : "Marked for " + date)
                .recordedBy(teacher)
                .build();

        attendance.setCreatedAt(java.time.Instant.now());
        attendance.setCreatedBy(userId);
        // Save attendance
        Attendance saved = repo.save(attendance);
        log.info("Attendance marked successfully with ID: {} for student: {} on: {}",
                saved.getId(), studentId, date);

        return mapper.toDto(saved);
    }


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
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> byStudentId(Long studentId, Pageable pageable) {
        log.info("Fetching attendance for student ID: {}", studentId);

        if (!studentRepo.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }

        Page<Attendance> attendancePage = repo.findByStudentEnrollment_Student_IdOrderByDateDesc(studentId, pageable);
        return attendancePage.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> bySectionId(Long sectionId, Pageable pageable) {
        log.info("Fetching attendance for section ID: {}", sectionId);

        if (!sectionRepo.existsById(sectionId)) {
            throw new ResourceNotFoundException("Section not found with ID: " + sectionId);
        }

        Page<Attendance> attendancePage = repo.findByStudentEnrollment_Section_Id(sectionId, pageable);
        return attendancePage.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> byDate(LocalDate date, Pageable pageable) {
        log.info("Fetching attendance for date: {}", date);

        Page<Attendance> attendancePage = repo.findByDate(date, pageable);
        return attendancePage.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> byDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.info("Fetching attendance for date range: {} to {}", startDate, endDate);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        Page<Attendance> attendancePage = repo.findByDateBetween(startDate, endDate, pageable);
        return attendancePage.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> bySectionAndDate(Long sectionId, LocalDate date, Pageable pageable) {
        log.info("Fetching attendance for section ID: {} and date: {}", sectionId, date);

        if (!sectionRepo.existsById(sectionId)) {
            throw new ResourceNotFoundException("Section not found with ID: " + sectionId);
        }

        Page<Attendance> attendancePage = repo.findByStudentEnrollment_Section_IdAndDate(sectionId, date, pageable);
        return attendancePage.map(mapper::toDto);
    }

    @Override
    public AttendanceResponseDto update(Long attendanceId, AttendanceUpdateDto dto, Long entityId, Long userId) {
        log.info("Updating attendance with ID: {}", attendanceId);

        Attendance attendance = repo.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID: " + attendanceId));

        Teacher teacher = teacherRepo.findById(entityId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + entityId));

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

            Attendance updated = repo.save(attendance);
            log.info("Attendance updated successfully with ID: {}", updated.getId());
            return mapper.toDto(updated);
        }else {
            log.info("No changes detected for attendance ID: {}", attendanceId);
            throw new NoChangesDetectedException("No changes detected for attendance ID: " + attendanceId);
        }
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

}