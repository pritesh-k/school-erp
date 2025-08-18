package com.schoolerp.service.impl;

import com.schoolerp.dto.request.RegisterRequest;
import com.schoolerp.dto.request.TeacherCreateDto;
import com.schoolerp.dto.request.TeacherUpdateDto;
import com.schoolerp.dto.response.TeacherDetailsResponseDto;
import com.schoolerp.dto.response.TeacherResponseDto;
import com.schoolerp.entity.Teacher;
import com.schoolerp.entity.User;
import com.schoolerp.enums.Role;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.TeacherMapper;
import com.schoolerp.repository.TeacherRepository;
import com.schoolerp.repository.UserRepository;
import com.schoolerp.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepo;
    private final UserRepository userRepo;
    private final TeacherMapper mapper;

    private final AuthServiceImpl authService;


    @Override
    @Transactional
    public TeacherResponseDto create(TeacherCreateDto dto, Long userId) {
        if (userRepo.existsByUsername(dto.username()))
            throw new IllegalArgumentException("Username exists");

        RegisterRequest req = new RegisterRequest(
                dto.username(),
                dto.email(),
                dto.password(),
                Role.TEACHER,
                dto.firstName(),
                dto.lastName(),
                userId
        );

        authService.register(req);
        User savedUser = authService.getUserByUsername(dto.username());

        // 1. Create Teacher with saved User
        Teacher teacher = Teacher.builder()
                .user(savedUser) //User with proper ID
                .employeeCode(dto.employeeCode())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .phone(dto.phone())
                .email(dto.email())
                .joiningDate(dto.joiningDate())
                .build();
        teacher.setCreatedAt(java.time.Instant.now());
        teacher.setActive(true);
        teacher.setDeleted(false);
        teacher.setCreatedBy(userId);
        teacher.createFullName();
        Teacher savedTeacher = teacherRepo.save(teacher); // Will work

        // 2. Update User with Teacher's ID
        savedUser.setEntityId(savedTeacher.getId());
        userRepo.save(savedUser);

        return mapper.toDto(savedTeacher);
    }


    @Override
    public TeacherResponseDto getByTeacherId(Long id) {
        return mapper.toDto(teacherRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher not found")));
    }
    @Override
    public Page<TeacherResponseDto> list(Pageable pageable) {
        return teacherRepo.findAll(pageable).map(mapper::toDto);
    }
    public Page<TeacherResponseDto> getTeachersWithoutAssignment(Long sectionSubjectAssignmentId, Pageable pageable) {
        if (sectionSubjectAssignmentId == null) {
            throw new IllegalArgumentException("Assignment ID cannot be null");
        }
        Page<Teacher> teachers = teacherRepo.findTeachersWithoutAssignment(sectionSubjectAssignmentId, pageable);
        return teachers.map(mapper::toDto);
    }
    @Override
    @Transactional
    public TeacherResponseDto update(Long teacherId, TeacherUpdateDto dto, Long userId) {
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherId));

        boolean hasChanges = false;

        // Update firstName only if provided and different
        if (dto.firstName() != null && !dto.firstName().equals(teacher.getFirstName())) {
            teacher.setFirstName(dto.firstName());
            hasChanges = true;
        }

        // Update lastName only if provided and different
        if (dto.lastName() != null && !dto.lastName().equals(teacher.getLastName())) {
            teacher.setLastName(dto.lastName());
            hasChanges = true;
        }

        // Update phone only if provided and different
        if (dto.phone() != null && !dto.phone().equals(teacher.getPhone())) {
            teacher.setPhone(dto.phone());
            hasChanges = true;
        }

        // Update email only if provided and different
        if (dto.email() != null && !dto.email().equals(teacher.getEmail())) {
            teacher.setEmail(dto.email());
            hasChanges = true;
        }

        // Update joining date only if provided and different
        if (dto.joiningDate() != null && !dto.joiningDate().equals(teacher.getJoiningDate())) {
            teacher.setJoiningDate(dto.joiningDate());
            hasChanges = true;
        }

        // Update employee code only if provided and different
        if (dto.employeeCode() != null && !dto.employeeCode().equals(teacher.getEmployeeCode())) {
            teacher.setEmployeeCode(dto.employeeCode());
            hasChanges = true;
        }

        // Update associated User if needed
        if (teacher.getUser() != null) {
            hasChanges = updateAssociatedUser(teacher.getUser(), dto) || hasChanges;
        }

        // Save only if there are changes
        if (hasChanges) {
            return mapper.toDto(teacherRepo.save(teacher));
        } else {
            return mapper.toDto(teacher);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));

        // Soft delete the teacher
        teacher.setDeleted(true);
        teacher.setActive(false);
        teacherRepo.save(teacher);

        // Also soft delete the associated user
        if (teacher.getUser() != null) {
            User user = teacher.getUser();
            user.setDeleted(true);
            user.setActive(false);
            userRepo.save(user);
        }
    }

    private boolean updateAssociatedUser(User user, TeacherUpdateDto dto) {
        boolean userChanged = false;

        // Update user's first name if different
        if (dto.firstName() != null && !dto.firstName().equals(user.getFirstName())) {
            user.setFirstName(dto.firstName());
            userChanged = true;
        }

        // Update user's last name if different
        if (dto.lastName() != null && !dto.lastName().equals(user.getLastName())) {
            user.setLastName(dto.lastName());
            userChanged = true;
        }

        // Update user's email if different
        if (dto.email() != null && !dto.email().equals(user.getEmail())) {
            user.setEmail(dto.email());
            userChanged = true;
        }

        // Recreate full name if name changed
        if (userChanged) {
            user.createFullName();
            user.setUpdatedAt(java.time.Instant.now());

            userRepo.save(user);
        }

        return userChanged;
    }

    public void existsByIdAndUser_Id(Long teacherId, Long userId){
        if (!teacherRepo.existsByIdAndUser_Id(teacherId, userId)) {
            throw new ResourceNotFoundException("Teacher not found with ID: " + teacherId + " and User ID: " + userId);
        }
    }

    public Long getTotalCount() {
        return teacherRepo.count(); // this returns the total number of rows
    }
}