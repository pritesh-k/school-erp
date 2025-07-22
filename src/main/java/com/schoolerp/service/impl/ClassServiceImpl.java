package com.schoolerp.service.impl;

import com.schoolerp.dto.request.ClassCreateDto;
import com.schoolerp.dto.request.ClassUpdateDto;
import com.schoolerp.dto.request.SectionCreateDto;
import com.schoolerp.dto.request.SectionUpdateDto;
import com.schoolerp.dto.response.ClassResponseDto;
import com.schoolerp.dto.response.SectionResponseDto;
import com.schoolerp.entity.SchoolClass;
import com.schoolerp.entity.Section;
import com.schoolerp.entity.Teacher;
import com.schoolerp.enums.ClassStandard;
import com.schoolerp.enums.SectionName;
import com.schoolerp.exception.DuplicateEntry;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.ClassMapper;
import com.schoolerp.mapper.SectionMapper;
import com.schoolerp.repository.SchoolClassRepository;
import com.schoolerp.repository.SectionRepository;
import com.schoolerp.repository.TeacherRepository;
import com.schoolerp.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassServiceImpl implements ClassService {

    private final SchoolClassRepository classRepo;
    private final SectionRepository sectionRepo;
    private final TeacherRepository teacherRepo;
    private final ClassMapper classMapper;
    private final SectionMapper sectionMapper;


    @Override
    @Transactional
    public ClassResponseDto create(ClassCreateDto dto, Long userId) {
        SchoolClass sc = SchoolClass.builder()
                .name(dto.getName()).build();
        sc.setCreatedBy(userId);
        sc.setUpdatedBy(userId);
        sc.setCreatedAt(java.time.Instant.now());
        sc.setActive(true);
        sc.setDeleted(false);
        sc.setUpdatedAt(java.time.Instant.now());
        return classMapper.toDto(classRepo.save(sc));
    }

    @Override
    public ClassResponseDto get(Long id) {
        return classMapper.toDto(classRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
    }

    @Override
    public Page<ClassResponseDto> list(Pageable pageable) {
        return classRepo.findAll(pageable).map(classMapper::toDto);
    }

    @Override
    @Transactional
    public ClassResponseDto updateClassOnly(Long id, ClassCreateDto dto, Long userId) {
        log.info("Updating school class with ID: {}", id);

        // Validate input
        validateUpdateInput(id, dto);

        // Find existing class
        SchoolClass existingClass = classRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School class not found with ID: " + id));

        // Store original name for logging
        ClassStandard originalName = existingClass.getName();
        boolean nameChanged = false;

        // Update name if provided and different
        if (dto.getName() != null && !dto.getName().equals(existingClass.getName())) {
            validateUniqueClassName(dto.getName(), id);
            existingClass.setName(dto.getName());
            existingClass.setUpdatedBy(userId);
            existingClass.setUpdatedAt(java.time.Instant.now());
            nameChanged = true;
        }

        // Save and return
        SchoolClass updatedClass = classRepo.save(existingClass);

        if (nameChanged) {
            log.info("School class name updated from '{}' to '{}' for ID: {}", originalName, dto.getName(), id);
        } else {
            log.debug("No changes made to school class ID: {}", id);
        }

        return classMapper.toDto(updatedClass);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        classRepo.deleteById(id);
    }

    @Override
    @Transactional
    public SectionResponseDto addSection(Long classId, SectionCreateDto dto, Long userId) {
        // Validate associated SchoolClass
        SchoolClass schoolClass = classRepo.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class with id " + classId + " not found"));

        boolean sectionExists = sectionRepo.existsBySchoolClassIdAndName(classId, dto.getName());
        if (sectionExists) {
            throw new DuplicateEntry("Section with name " + dto.getName() + " already exists for class " + schoolClass.getName());
        }

        // Optional class teacher
        Teacher teacher = null;
        if (dto.getClassTeacherId() != null && dto.getClassTeacherId() > 0) {
            teacher = teacherRepo.findById(dto.getClassTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher with id " + dto.getClassTeacherId() + " not found"));
        }
        // Build Section only with valid fields
        Section.SectionBuilder sectionBuilder = Section.builder()
                .schoolClass(schoolClass);

        if (dto.getName() != null) {
            sectionBuilder.name(dto.getName());
        }

        if (dto.getRoomNo() != null && !dto.getRoomNo().trim().isEmpty()) {
            sectionBuilder.roomNo(dto.getRoomNo().trim());
        }

        if (dto.getCapacity() != null) {
            sectionBuilder.capacity(dto.getCapacity());
        }

        if (teacher != null) {
            sectionBuilder.classTeacherId(teacher.getId());
        }

        Section section = sectionBuilder.build();

        section.setUpdatedBy(userId);
        section.setUpdatedAt(Instant.now());
        section.setCreatedBy(userId);
        section.setCreatedAt(Instant.now());
        section.setActive(true);
        section.setDeleted(false);

        return sectionMapper.toDto(sectionRepo.save(section));
    }


    @Override
    @Transactional(readOnly = true)
    public List<SectionResponseDto> sectionsByClass(Long classId) {

        List<Section> sections = sectionRepo.findBySchoolClassId(classId);

        if (sections.isEmpty()) {
            log.debug("No sections found for class ID: {}", classId);
            throw new ResourceNotFoundException("No sections found for class with ID: " + classId);
        }

        return sections.stream()
                .map(sectionMapper::toDto)
                .toList();
    }


    @Override
    @Transactional
    public SectionResponseDto updateSectionsOnly(Long classId, SectionUpdateDto dto, Long userId, Long sectionId) {
        // 1. Lookup section
        Section section = sectionRepo.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with ID: " + sectionId));

        // 2. Check that section belongs to the specified class
        if (!section.getSchoolClass().getId().equals(classId)) {
            throw new IllegalArgumentException("Section does not belong to the specified class");
        }

        // 3. Update supplied fields
        if (dto.getName() != null && !dto.getName().equals(section.getName())) {
            if (sectionRepo.existsByNameAndSchoolClassId(dto.getName(), classId)) {
                throw new DuplicateEntry("Section name '" + dto.getName() + "' already exists in class with ID: " + classId);
            }
            section.setName(dto.getName());
        }
        if (dto.getRoomNo() != null && !dto.getRoomNo().trim().isEmpty())
            section.setRoomNo(dto.getRoomNo());
        if (dto.getCapacity() != null && dto.getCapacity() > 0)
            section.setCapacity(dto.getCapacity());

        // 4. Update class teacher, if specified
        if (dto.getClassTeacherId() != null && dto.getClassTeacherId() > 0
                && !dto.getClassTeacherId().equals(section.getClassTeacherId() != null ? section.getClassTeacherId() : null)) {
            Teacher teacher = teacherRepo.findById(dto.getClassTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + dto.getClassTeacherId()));
            section.setClassTeacherId(teacher.getId());
        }

        // 5. Set audit fields
        section.setUpdatedAt(Instant.now());
        section.setUpdatedBy(userId);

        // 6. Save changes
        sectionRepo.save(section);

        return sectionMapper.toDto(section);
    }

    private void validateUpdateInput(Long id, ClassCreateDto dto) {
        if (id == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }
        if (dto == null) {
            throw new IllegalArgumentException("Update data cannot be null");
        }
    }

    private void validateUniqueClassName(ClassStandard name, Long excludeId) {
        if (classRepo.existsByNameAndIdNot(name, excludeId)) {
            throw new DuplicateEntry("Class name '" + name + "' already exists");
        }
    }
}