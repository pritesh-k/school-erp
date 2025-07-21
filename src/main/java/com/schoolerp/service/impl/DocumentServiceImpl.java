package com.schoolerp.service.impl;

import com.schoolerp.dto.response.DocumentResponseDto;
import com.schoolerp.entity.Document;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.User;
import com.schoolerp.exception.ResourceNotFoundException;
import com.schoolerp.mapper.DocumentMapper;
import com.schoolerp.repository.DocumentRepository;
import com.schoolerp.repository.StudentRepository;
import com.schoolerp.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repo;
    private final StudentRepository studentRepo;
    private final DocumentMapper mapper;

    @Value("${file.storage.root:uploads}")
    private String root;

    @Override
    @Transactional
    public DocumentResponseDto upload(MultipartFile file, Long studentId, String type) {
        Student st = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        try {
            Path dir = Paths.get(root, "documents");
            Files.createDirectories(dir);
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = dir.resolve(fileName);
            Files.copy(file.getInputStream(), path);

            User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Document doc = Document.builder()
                    .student(st)
                    .type(type)
                    .fileName(file.getOriginalFilename())
                    .fileUrl("/uploads/documents/" + fileName)
                    .uploadedBy(current)
                    .build();
            return mapper.toDto(repo.save(doc));
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    @Override
    public Page<DocumentResponseDto> listByStudent(Long studentId, Pageable pageable) {
        return repo.findByStudentId(studentId, pageable)
                .map(mapper::toDto);
    }

}