package com.schoolerp.service.impl;

import com.schoolerp.dto.request.*;
import com.schoolerp.dto.response.ParentResponseDto;
import com.schoolerp.dto.response.StudentDetailedResponseDto;
import com.schoolerp.dto.response.StudentResponseDto;
import com.schoolerp.entity.*;
import com.schoolerp.enums.Gender;
import com.schoolerp.enums.Relation;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.DuplicateEntry;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.StudentMapper;
import com.schoolerp.repository.*;
import com.schoolerp.service.ParentService;
import com.schoolerp.service.StudentService;
import com.schoolerp.utils.BulkImportReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository repo;
    @Autowired
    private final StudentMapper mapper;
    @Autowired
    private final UserRepository userRepository;
    private final AuthServiceImpl authService;
    private final UserRepository userRepo;
    private final ParentService parentService;
    @Autowired
    private final ParentRepository parentRepository;

    @Override
    @Transactional
    public StudentResponseDto create(StudentCreateDto dto, Long userId) {
        if (repo.findByAdmissionNumber(dto.getAdmissionNumber()).isPresent())
            throw new DuplicateEntry("Admission number already exists");

        // 1. Create and save User first (using admission number as username)
        RegisterRequest req = new RegisterRequest(
                dto.getAdmissionNumber(),  // username = admission number
                dto.getEmail(),
                dto.getAdmissionNumber(),  // default password = admission number
                Role.STUDENT,
                dto.getFirstName(),
                dto.getLastName(),
                userId  // created by current user
        );

        authService.register(req);
        User savedUser = authService.getUserByUsername(dto.getAdmissionNumber()); // Fetch the saved user by username

                // 2. Create Parent first and save it, associate to student
        Parent parent = null;
        ParentCreateDto parentDto = dto.getParentCreateDto();
        if (parentDto != null) {
            ParentResponseDto parentResponseDto = parentService.create(parentDto, userId);
            parent = parentService.getReferenceById(parentResponseDto.getId()); // or fetch from DB
        } else {
            throw new ResourceNotFoundException("Parent information is required");
        }

        // 3. Create Student with saved User and set parent
        Student student = Student.builder()
                .user(savedUser)
                .admissionNumber(dto.getAdmissionNumber())
                .rollNumber(dto.getRollNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dob(dto.getDob())
                .gender(dto.getGender())
                .email(dto.getEmail())
                .parent(parent)  // Set parent here
                .build();
        student.setCreatedAt(java.time.Instant.now());
        student.setCreatedBy(userId);

        Student savedStudent = repo.save(student);

        savedUser.setEntityId(savedStudent.getId());
        userRepo.save(savedUser);
        return mapper.toDto(savedStudent);
    }

    @Override
    @Transactional
    public BulkImportReport bulkImport(MultipartFile file, Long userId) {
        List<BulkImportReport.RowError> errors = new ArrayList<>();
        int totalRows = 0;
        int successCount = 0;

        List<Student> studentsToSave = new ArrayList<>();
        List<User> usersToSave = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            totalRows = sheet.getPhysicalNumberOfRows() - 1; // minus header

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // start from row 1 (skip header)
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    // Extract DTO from row
                    StudentCreateDto dto = excelRowToDto(row);

                    // Check duplicate admission number
                    if (repo.findByAdmissionNumber(dto.getAdmissionNumber()).isPresent()) {
                        throw new DuplicateEntry("Admission number already exists");
                    }

                    // 1. Create User
                    RegisterRequest req = new RegisterRequest(
                            dto.getAdmissionNumber(),
                            dto.getEmail(),
                            dto.getAdmissionNumber(),
                            Role.STUDENT,
                            dto.getFirstName(),
                            dto.getLastName(),
                            userId
                    );
                    authService.register(req);
                    User savedUser = authService.getUserByUsername(dto.getAdmissionNumber());

                    // 2. Create Parent
                    if (dto.getParentCreateDto() == null) {
                        throw new ResourceNotFoundException("Parent information is required");
                    }
                    ParentResponseDto parentResponseDto = parentService.create(dto.getParentCreateDto(), userId);
                    Parent parent = parentService.getReferenceById(parentResponseDto.getId());

                    // 3. Prepare Student
                    Student student = Student.builder()
                            .user(savedUser)
                            .admissionNumber(dto.getAdmissionNumber())
                            .rollNumber(dto.getRollNumber())
                            .firstName(dto.getFirstName())
                            .lastName(dto.getLastName())
                            .dob(dto.getDob())
                            .gender(dto.getGender())
                            .email(dto.getEmail())
                            .parent(parent)
                            .build();
                    student.setCreatedAt(Instant.now());
                    student.setCreatedBy(userId);
                    studentsToSave.add(student);

                    // Link user to student later after save
                    savedUser.setEntityId(null); // will set after student is saved
                    usersToSave.add(savedUser);

                    successCount++;

                } catch (Exception e) {
                    errors.add(new BulkImportReport.RowError(i + 1, e.getMessage()));
                }
            }

            // Save all students in batch
            repo.saveAll(studentsToSave);

            // Update entity IDs for users & save them
            for (int j = 0; j < studentsToSave.size(); j++) {
                usersToSave.get(j).setEntityId(studentsToSave.get(j).getId());
            }
            userRepo.saveAll(usersToSave);

        } catch (IOException e) {
            throw new RuntimeException("Failed to process Excel file", e);
        }

        return new BulkImportReport(totalRows, successCount, errors.size(), errors);
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
    public StudentDetailedResponseDto getDetailed(Long id) {
        Student student = repo.findById(
                id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return mapper.toDetailedDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDto> searchStudentsByName(String name, Pageable pageable) {
        Page<Student> page = repo.searchByName(name, pageable);
        return page.map(mapper::toDto);
    }

    @Override
    @Transactional
    public StudentResponseDto update(Long id, StudentUpdateDto dto) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        boolean hasChanges = false;
        boolean nameChanged = false;

        User user = student.getUser();
        // --- Student & User fields ---
        if (isChanged(dto.getFirstName(), student.getFirstName())) {
            student.setFirstName(dto.getFirstName().trim());
            user.setFirstName(student.getFirstName());
            nameChanged = true;
            hasChanges = true;
        }

        if (isChanged(dto.getLastName(), student.getLastName())) {
            student.setLastName(dto.getLastName().trim());
            user.setLastName(student.getLastName());
            nameChanged = true;
            hasChanges = true;
        }

        if (dto.getDob() != null && !dto.getDob().equals(student.getDob())) {
            student.setDob(dto.getDob());
            hasChanges = true;
        }

        if (dto.getGender() != null && !dto.getGender().equals(student.getGender())) {
            student.setGender(dto.getGender());
            hasChanges = true;
        }

        if (isChanged(dto.getEmail(), student.getEmail())) {
            student.setEmail(dto.getEmail().trim());
            user.setEmail(student.getEmail());
            hasChanges = true;
        }

        // --- Parent fields ---
        if (dto.getParent() != null) {
            ParentUpdateDto pDto = dto.getParent();
            Parent parent = student.getParent();
            if (parent == null) {
                throw new ResourceNotFoundException("Parent not found for student");
            }
            parentService.update(parent.getId(), pDto);
        }

        if (hasChanges) {
            if (nameChanged) {
                user.createFullName();
            }
            userRepository.save(user);
            Student updatedStudent = repo.save(student);
            return mapper.toDto(updatedStudent);
        }

        return mapper.toDto(student); // No changes, return as is
    }

    // Helper method to check string changes
    private boolean isChanged(String newValue, String oldValue) {
        if (newValue == null || newValue.trim().isEmpty()) return false;
        return !newValue.trim().equals(oldValue);
    }


//    @Override
//    @Transactional
//    public void delete(Long id) {
//        log.info("Deleting student with ID: {}", id);
//
//        // Validate student exists
//        Student student = repo.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
//
//        // Check if already deleted
//        if (student.isDeleted()) {
//            log.warn("Attempted to delete already deleted student ID: {}", id);
//            return; // Idempotent operation
//        }
//
//        student.setDeleted(true);
//        repo.save(student);
//
//        // Disable user account
//        if (student.getUser() != null) {
//            User user = student.getUser();
//            user.setDeleted(true);
//            user.setActive(false);
//            userRepository.save(user);
//        }
//
//        log.info("Student deleted successfully: {}", id);
//    }


    @Override
    @Transactional
    public void delete(Long id){
        // Validate student exists
        Student student = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void bulkUpload(MultipartFile file) {
//        excelUtil.parseExcel(file, StudentCreateDto.class).forEach(this::create);
    }

    @Override
    public Student getByUserId(Long userId) {
        log.info("Fetching student by user ID: {}", userId);

        return repo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for user ID: " + userId));
    }

    public void existsByIdAndUser_Id(Long studentId, Long userId){
        if (!repo.existsByIdAndUser_Id(studentId, userId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId + " and User ID: " + userId);
        }
    }

    public Long getTotalStudentCount() {
        return repo.count(); // this returns the total number of rows
    }

    private StudentCreateDto excelRowToDto(Row row) {
        StudentCreateDto dto = new StudentCreateDto();
        dto.setAdmissionNumber(getCellValue(row.getCell(0)));
        dto.setRollNumber(getCellValue(row.getCell(1)));
        dto.setFirstName(getCellValue(row.getCell(2)));
        dto.setLastName(getCellValue(row.getCell(3)));
        dto.setDob(getDateCellValue(row.getCell(4))); // assuming ISO format yyyy-MM-dd
        dto.setGender(parseEnum(Gender.class, getCellValue(row.getCell(5)), "Invalid gender"));
        dto.setEmail(getCellValue(row.getCell(6)));

        ParentCreateDto parentDto = new ParentCreateDto();
        parentDto.setFirstName(getCellValue(row.getCell(7)));
        parentDto.setLastName(getCellValue(row.getCell(8)));
        parentDto.setPhone(getCellValue(row.getCell(9)));
        parentDto.setOccupation(getCellValue(row.getCell(10)));
        parentDto.setEmail(getCellValue(row.getCell(11)));
        parentDto.setRelation(parseEnum(Relation.class, getCellValue(row.getCell(12)), "Invalid relation"));

        dto.setParentCreateDto(parentDto);

        return dto;
    }

    private LocalDate getDateCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            // Excel numeric date
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }

        // Treat as text
        String value = cell.toString().trim();
        if (value.isEmpty()) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + value + ". Expected dd/MM/yyyy");
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private <E extends Enum<E>> E parseEnum(Class<E> enumType, String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage + ": value is blank");
        }
        try {
            return Enum.valueOf(enumType, value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(errorMessage + ": " + value);
        }
    }


}