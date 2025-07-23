package com.schoolerp.service.impl;

import com.schoolerp.dto.request.RegisterRequest;
import com.schoolerp.dto.request.StudentCreateDto;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.entity.*;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.StudentMapper;
import com.schoolerp.repository.*;
import com.schoolerp.service.StudentService;
import com.schoolerp.utils.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository repo;
    @Autowired
    private final StudentMapper mapper;
    @Autowired
    private final SchoolClassRepository classRepo;
    @Autowired
    private final SectionRepository sectionRepo;
    @Autowired
    private final AttendanceRepository attendanceRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private final ExamResultRepository examResultRepo;
    @Autowired
    private final FeeRecordRepository feeRecordRepo;
    private final ExcelUtil excelUtil;

    private final AuthServiceImpl authService;

    private final UserRepository userRepo;
    @Override
    @Transactional
    public StudentResponseDto create(StudentCreateDto dto) {
        if (repo.findByAdmissionNumber(dto.getAdmissionNumber()).isPresent())
            throw new IllegalArgumentException("Admission number already exists");

        // 1. Create and save User first (using admission number as username)
        RegisterRequest req = new RegisterRequest(
                dto.getAdmissionNumber(),  // username = admission number
                dto.getEmail(),
                dto.getAdmissionNumber(),  // default password = admission number
                Role.STUDENT,
                dto.getFirstName(),
                dto.getLastName()
        );

        User savedUser = authService.register(req);

        // 2. Create Student with saved User
        Student student = Student.builder()
                .user(savedUser)  // User with proper ID
                .admissionNumber(dto.getAdmissionNumber())
                .rollNumber(dto.getRollNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dob(dto.getDob())
                .gender(dto.getGender())
                .email(dto.getEmail())
                .schoolClass(classRepo.getReferenceById(dto.getSchoolClassId()))
                .section(sectionRepo.getReferenceById(dto.getSectionId()))
                .build();

        Student savedStudent = repo.save(student);

        // 3. Update User with Student's ID
        savedUser.setEntityId(savedStudent.getId());
        userRepo.save(savedUser);

        return mapper.toDto(savedStudent);
    }


    @Override
    public Page<StudentResponseDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public StudentResponseDto get(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found")));
    }

    @Override
    @Transactional
    public StudentResponseDto update(Long id, StudentCreateDto dto) {
        Student s = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        boolean nameChanged = false, emailChanged = false;
        User user = s.getUser();
        // Update first name only if not null/empty
        if (dto.getFirstName() != null && !dto.getFirstName().trim().isEmpty()) {
            s.setFirstName(dto.getFirstName().trim());
            user.setFirstName(s.getFirstName());
            nameChanged = true; // Track if name changed
        }

        // Update last name only if not null/empty
        if (dto.getLastName() != null && !dto.getLastName().trim().isEmpty()) {
            s.setLastName(dto.getLastName().trim());
            user.setLastName(s.getLastName());
            nameChanged = true; // Track if name changed
        }

        // Update date of birth only if not null
        if (dto.getDob() != null) {
            s.setDob(dto.getDob());
        }

        // Update gender only if not null
        if (dto.getGender() != null) {
            s.setGender(dto.getGender());
        }

        // Update school class only if not null
        if (dto.getSchoolClassId() != null) {
            s.setSchoolClass(classRepo.getReferenceById(dto.getSchoolClassId()));
        }

        // Update section only if not null
        if (dto.getSectionId() != null) {
            s.setSection(sectionRepo.getReferenceById(dto.getSectionId()));
        }

        if (dto.getEmail() != null){
            s.setEmail(dto.getEmail().trim());
            user.setEmail(s.getEmail());
        }

        Student updatedStudent = repo.save(s);
        // Update User table if name changed (for faster access)
        if (nameChanged) {
            user.createFullName();
            userRepository.save(user);
        }

        return mapper.toDto(updatedStudent);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting student with ID: {}", id);

        // Validate student exists
        Student student = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        // Check if already deleted
        if (student.isDeleted()) {
            log.warn("Attempted to delete already deleted student ID: {}", id);
            return; // Idempotent operation
        }

        // Check for critical dependencies
        if (hasActiveRecords(id)) {
            throw new IllegalStateException("Cannot delete student with active records");
        }

        // Soft delete student
        student.setDeleted(true);
        repo.save(student);

        // Disable user account
        if (student.getUser() != null) {
            User user = student.getUser();
            user.setDeleted(true);
            user.setActive(false);
            userRepository.save(user);
        }

        log.info("Student deleted successfully: {}", id);
    }

    private boolean hasActiveRecords(Long studentId) {
        return attendanceRepo.existsByStudentId(studentId) ||
                examResultRepo.existsByStudentId(studentId) ||
                feeRecordRepo.existsByStudentFeeAssignment_Student_Id(studentId);
    }


    @Override
    @Transactional
    public void bulkUpload(MultipartFile file) {
        excelUtil.parseExcel(file, StudentCreateDto.class).forEach(this::create);
    }

    @Override
    public Student getByUserId(Long userId) {
        log.info("Fetching student by user ID: {}", userId);

        return repo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for user ID: " + userId));
    }

}