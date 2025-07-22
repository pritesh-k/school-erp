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
        Section section = student.getSection();
        if (section == null) {
            throw new ResourceNotFoundException("Student has no section assigned");
        }

        // Get teacher (use provided teacherId or get class teacher as default)
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherId));
        // Check if attendance already exists for today
        boolean alreadyRecorded = repo.existsByStudentIdAndSectionIdAndDate(studentId, section.getId(), date);

        if (alreadyRecorded) {
            throw new DuplicateEntry("Attendance already marked for this student on " + date);
        }

        // Create and save attendance
        Attendance attendance = Attendance.builder()
                .student(student)
                .section(section)
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
            Student student = studentRepo.findById(dto.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + dto.getStudentId()));

            // Get section from student
            Section section = student.getSection();
            if (section == null) {
                throw new ResourceNotFoundException("Student has no section assigned");
            }

            Teacher teacher = teacherRepo.findById(entityId)
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + entityId));

            // Check if attendance already exists
            Optional<Attendance> existingAttendance = repo
                    .findByStudentIdAndSectionIdAndDate(student.getId(), section.getId(), dto.getDate());

            if (existingAttendance.isPresent()) {
                log.warn("Attendance already exists for student {} on {}", dto.getStudentId(), dto.getDate());
                continue;
            }

            Attendance attendance = Attendance.builder()
                    .student(student)
                    .section(section)
                    .date(dto.getDate())
                    .status(dto.getStatus())
                    .remarks(dto.getRemarks())
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

        Page<Attendance> attendancePage = repo.findByStudentIdOrderByDateDesc(studentId, pageable);
        return attendancePage.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> bySectionId(Long sectionId, Pageable pageable) {
        log.info("Fetching attendance for section ID: {}", sectionId);

        if (!sectionRepo.existsById(sectionId)) {
            throw new ResourceNotFoundException("Section not found with ID: " + sectionId);
        }

        Page<Attendance> attendancePage = repo.findBySectionId(sectionId, pageable);
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

        Page<Attendance> attendancePage = repo.findBySectionIdAndDate(sectionId, date, pageable);
        return attendancePage.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceSummaryDto getStudentSummary(Long studentId, LocalDate startDate, LocalDate endDate) {
        log.info("Generating attendance summary for student ID: {}", studentId);

        if (!studentRepo.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }

        List<Attendance> attendances = repo
                .findByStudentIdAndDateBetween(studentId, startDate, endDate);

        return calculateSummary(attendances, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceSummaryDto getSectionSummary(Long sectionId, LocalDate startDate, LocalDate endDate) {
        log.info("Generating attendance summary for section ID: {}", sectionId);

        if (!sectionRepo.existsById(sectionId)) {
            throw new ResourceNotFoundException("Section not found with ID: " + sectionId);
        }

        List<Attendance> attendances = repo
                .findBySectionIdAndDateBetween(sectionId, startDate, endDate);

        return calculateSummary(attendances, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendancePercentageDto getStudentPercentage(Long studentId, LocalDate startDate, LocalDate endDate) {
        log.info("Calculating attendance percentage for student ID: {}", studentId);

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        List<Attendance> attendances = repo
                .findByStudentIdAndDateBetween(studentId, startDate, endDate);

        long totalDays = attendances.size();
        long presentDays = attendances.stream()
                .mapToLong(a -> a.getStatus() == AttendanceStatus.PRESENT ? 1 : 0)
                .sum();
        long absentDays = attendances.stream()
                .mapToLong(a -> a.getStatus() == AttendanceStatus.ABSENT ? 1 : 0)
                .sum();
        long lateDays = attendances.stream()
                .mapToLong(a -> a.getStatus() == AttendanceStatus.LATE ? 1 : 0)
                .sum();

        double percentage = totalDays > 0 ? (double) presentDays / totalDays * 100 : 0.0;

        return AttendancePercentageDto.builder()
                .studentId(studentId)
                .studentName(student.getFirstName() + " " + student.getLastName())
                .percentage(Math.round(percentage * 100.0) / 100.0)
                .totalDays(totalDays)
                .presentDays(presentDays)
                .absentDays(absentDays)
                .lateDays(lateDays)
                .build();
    }

    @Override
    public AttendanceResponseDto update(Long attendanceId, AttendanceUpdateDto dto, Long entityId, Long userId) {
        log.info("Updating attendance with ID: {}", attendanceId);

        Attendance attendance = repo.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID: " + attendanceId));

        Teacher teacher = teacherRepo.findById(entityId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + entityId));
        attendance.setRecordedBy(teacher);

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
            attendance.setRecordedBy(teacher);

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

    private AttendanceSummaryDto calculateSummary(List<Attendance> attendances, LocalDate startDate, LocalDate endDate) {
        long totalDays = attendances.size();
        long presentDays = attendances.stream()
                .mapToLong(a -> a.getStatus() == AttendanceStatus.PRESENT ? 1 : 0)
                .sum();
        long absentDays = attendances.stream()
                .mapToLong(a -> a.getStatus() == AttendanceStatus.ABSENT ? 1 : 0)
                .sum();
        long lateDays = attendances.stream()
                .mapToLong(a -> a.getStatus() == AttendanceStatus.LATE ? 1 : 0)
                .sum();

        double percentage = totalDays > 0 ? (double) presentDays / totalDays * 100 : 0.0;

        return AttendanceSummaryDto.builder()
                .totalDays(totalDays)
                .presentDays(presentDays)
                .absentDays(absentDays)
                .lateDays(lateDays)
                .attendancePercentage(Math.round(percentage * 100.0) / 100.0)
                .fromDate(startDate)
                .toDate(endDate)
                .build();
    }

    @Override
    public List<AttendanceResponseDto> markAllPresent(Long classId, LocalDate date, Long teacherId) {
        log.info("Marking all students present for class ID: {} on date: {}", classId, date);

        // Validate section exists
        Section section = sectionRepo.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with ID: " + classId));

        // Get teacher
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherId));

        // Check for existing attendance
        List<Attendance> existingAttendances = repo
                .findBySectionIdAndDate(classId, date);

        if (!existingAttendances.isEmpty()) {
            throw new DuplicateEntry(
                    String.format("Attendance already exists for section %s on %s", section.getName(), date));
        }

        // Bulk insert using custom query
        int recordsCreated = repo.markAllStudentsWithStatus(
                classId, date, AttendanceStatus.PRESENT, "Marked all present", teacher);

        log.info("Successfully marked {} students present for class ID: {} on date: {}",
                recordsCreated, classId, date);

        // Fetch and return the created records
        List<Attendance> createdAttendances = repo
                .findBySectionIdAndDate(classId, date);

        return createdAttendances.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> byClassId(Long classId, Pageable pageable) {

        if (!classRepository.existsById(classId))
            throw new EntityNotFoundException("Class not found: " + classId);

        Page<Attendance> page =
                repo.findBySection_SchoolClass_Id(classId, pageable);

        return page.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> byClassAndDate(Long classId, LocalDate date) {

        if (!classRepository.existsById(classId))
            throw new EntityNotFoundException("Class not found: " + classId);

        List<Attendance> list =
                repo.findBySection_SchoolClass_IdAndDate(classId, date);

        return list.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /* ---------------------------------------------------------
       2.  Attendance % for one student (name kept as requested)
       --------------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public AttendancePercentageDto getAttendancePercentageByStudent(Long studentId,
                                                                    LocalDate start,
                                                                    LocalDate end) {

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found: " + studentId));

        List<Attendance> list =
                repo.findByStudentIdAndDateBetween(studentId, start, end);

        long total   = list.size();
        long present = list.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        long absent  = list.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT ).count();
        long late    = list.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE   ).count();

        double pct = total == 0 ? 0 : (present * 100.0) / total;

        return AttendancePercentageDto.builder()
                .studentId(studentId)
                .studentName(student.getFirstName() + " " + student.getLastName())
                .percentage(Math.round(pct * 100) / 100.0)
                .totalDays(total)
                .presentDays(present)
                .absentDays(absent)
                .lateDays(late)
                .build();
    }

    /* ---------------------------------------------------------
       3.  Flexible SEARCH (Specification API)
       --------------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> search(Long studentId,
                                              Long sectionId,
                                              Long classId,
                                              AttendanceStatus status,
                                              LocalDate startDate,
                                              LocalDate endDate,
                                              Pageable pageable) {

        Specification<Attendance> spec = Specification.where(null);

        if (studentId != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("student").get("id"), studentId));

        if (sectionId != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("section").get("id"), sectionId));

        if (classId != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("section")
                    .get("schoolClass")
                    .get("id"), classId));

        if (status != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("status"), status));

        if (startDate != null && endDate != null)
            spec = spec.and((r, q, cb) -> cb.between(r.get("date"), startDate, endDate));
        else if (startDate != null)
            spec = spec.and((r, q, cb) -> cb.greaterThanOrEqualTo(r.get("date"), startDate));
        else if (endDate != null)
            spec = spec.and((r, q, cb) -> cb.lessThanOrEqualTo(r.get("date"), endDate));

        Page<Attendance> page = repo.findAll(spec, pageable);
        return page.map(mapper::toDto);
    }

    /* ---------------------------------------------------------
       4.  Today's attendance
       --------------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> getTodayAttendance(Long classId, Pageable pageable) {
        LocalDate today = LocalDate.now();

        if (classId != null) {
            return repo.findByDateAndSection_SchoolClass_Id(today, classId, pageable)
                    .map(mapper::toDto);
        }

        return repo.findByDate(today, pageable)
                .map(mapper::toDto);
    }


    /* ---------------------------------------------------------
       5.  MONTHLY report
       --------------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public MonthlyAttendanceReportDto getMonthlyReport(int year,
                                                       int month,
                                                       Long classId,
                                                       Long studentId) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end   = start.withDayOfMonth(start.lengthOfMonth());

        List<Attendance> list;

        if (studentId != null) {
            list = repo.findByStudentIdAndDateBetween(studentId, start, end);
        } else if (classId != null) {
            list = repo
                    .findBySection_SchoolClass_IdAndDateBetween(classId, start, end);
        } else {
            list = repo.findByDateBetween(start, end);
        }

        long total   = list.size();
        long present = list.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        long absent  = list.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT ).count();
        long late    = list.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE   ).count();
        double pct   = total == 0 ? 0 : (present * 100.0) / total;

        MonthlyAttendanceReportDto.MonthlyAttendanceReportDtoBuilder builder =
                MonthlyAttendanceReportDto.builder()
                        .year(year)
                        .month(month)
                        .totalRecords(total)
                        .presentDays(present)
                        .absentDays(absent)
                        .lateDays(late)
                        .percentage(Math.round(pct * 100) / 100.0);

        if (classId != null) {
            SchoolClass sc = classRepository.findById(classId)
                    .orElseThrow(() -> new EntityNotFoundException("Class not found: " + classId));
            builder.classId(classId).className(sc.getName());
        }

        if (studentId != null) {
            Student st = studentRepo.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found: " + studentId));
            builder.studentId(studentId)
                    .studentName(st.getFirstName() + " " + st.getLastName());
        }

        return builder.build();
    }

    /* ---------------------------------------------------------
       6.  WEEKLY report
       --------------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public WeeklyAttendanceReportDto getWeeklyReport(LocalDate weekStart,
                                                     Long classId,
                                                     Long studentId) {

        LocalDate weekEnd = weekStart.plusDays(6);

        List<Attendance> list;
        if (studentId != null) {
            list = repo.findByStudentIdAndDateBetween(studentId,
                    weekStart, weekEnd);
        } else if (classId != null) {
            list = repo
                    .findBySection_SchoolClass_IdAndDateBetween(classId,
                            weekStart, weekEnd);
        } else {
            list = repo.findByDateBetween(weekStart, weekEnd);
        }

        long total   = list.size();
        long present = list.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        long absent  = list.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT ).count();
        long late    = list.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE   ).count();
        double pct   = total == 0 ? 0 : (present * 100.0) / total;

        WeeklyAttendanceReportDto.WeeklyAttendanceReportDtoBuilder builder =
                WeeklyAttendanceReportDto.builder()
                        .weekStart(weekStart)
                        .weekEnd(weekEnd)
                        .totalRecords(total)
                        .presentDays(present)
                        .absentDays(absent)
                        .lateDays(late)
                        .percentage(Math.round(pct * 100) / 100.0);

        if (classId != null) {
            SchoolClass sc = classRepository.findById(classId)
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found: " + classId));
            builder.classId(classId).className(sc.getName());
        }

        if (studentId != null) {
            Student st = studentRepo.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
            builder.studentId(studentId)
                    .studentName(st.getFirstName() + " " + st.getLastName());
        }

        return builder.build();
    }
}