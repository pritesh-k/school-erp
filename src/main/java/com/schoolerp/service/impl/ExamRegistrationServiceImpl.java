//package com.schoolerp.service.impl;
//
//import com.schoolerp.dto.request.ExamRegistrationCreateDto;
//import com.schoolerp.dto.response.ExamRegistrationResponseDto;
//import com.schoolerp.entity.ExamRegistration;
//import com.schoolerp.exception.ResourceNotFoundException;
//import com.schoolerp.mapper.ExamRegistrationMapper;
//import com.schoolerp.repository.ExamRegistrationRepository;
//import com.schoolerp.service.ExamRegistrationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class ExamRegistrationServiceImpl implements ExamRegistrationService {
//    private final ExamRegistrationRepository repo;
//    private final ExamRegistrationMapper mapper;
//
//    @Override
//    @Transactional
//    public ExamRegistrationResponseDto create(ExamRegistrationCreateDto dto) {
//        ExamRegistration reg = mapper.toEntity(dto);
//        return mapper.toDto(repo.save(reg));
//    }
//
//    @Override
//    @Transactional
//    public void delete(Long id) {
//        repo.deleteById(id);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public ExamRegistrationResponseDto getById(Long id) {
//        return mapper.toDto(repo.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("ExamRegistration not found")));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Page<ExamRegistrationResponseDto> list(Pageable pageable) {
//        return repo.findAll(pageable).map(mapper::toDto);
//    }
//}
