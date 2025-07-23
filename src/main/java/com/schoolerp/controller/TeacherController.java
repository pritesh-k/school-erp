package com.schoolerp.controller;

import com.schoolerp.dto.request.TeacherAssignmentDto;
import com.schoolerp.dto.request.TeacherCreateDto;
import com.schoolerp.dto.response.ApiResponse;
import com.schoolerp.dto.response.SectionResponseDto;
import com.schoolerp.dto.response.TeacherResponseDto;
import com.schoolerp.dto.response.TeachersAssignedDto;
import com.schoolerp.entity.UserTypeInfo;
import com.schoolerp.exception.UnauthorizedException;
import com.schoolerp.service.RequestContextService;
import com.schoolerp.service.TeacherService;
import com.schoolerp.service.impl.TeacherAssignmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService service;
    private final RequestContextService requestContextService;

    private final TeacherAssignmentService assignmentService;

    /** * Creates a new teacher.
     * Only accessible by ADMIN role.
     *
     * @param dto the data transfer object containing teacher details
     * @param request the HTTP request containing user context
     * @return ApiResponse with the created TeacherResponseDto
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ApiResponse<TeacherResponseDto> create(@RequestBody TeacherCreateDto dto, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
        Long userId = userTypeInfo.getUserId();
        return ApiResponse.ok(service.create(dto, userId));
    }
    /** * Lists all teachers with pagination.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param page the page number to retrieve
     * @param size the number of items per page
     * @return ApiResponse with a list of TeacherResponseDto
     */

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('PRINCIPAL')")
    @GetMapping
    public ApiResponse<List<TeacherResponseDto>> list(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(100) Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(service.list(pageable));
    }

    /** * Retrieves a teacher by their ID.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param id the ID of the teacher to retrieve
     * @return ApiResponse with the teacher's details
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('PRINCIPAL')")
    @GetMapping("/{id}")
    public ApiResponse<TeacherResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getByTeacherId(id));
    }

    // Assignment operations - with path variables in mapping
    /** * Assigns a teacher to a section.
     * Only accessible by ADMIN role.
     *
     * @param teacherId the ID of the teacher to assign
     * @param sectionId the ID of the section to assign the teacher to
     * @return ApiResponse with success message
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{teacherId}/assignments/section/{sectionId}")
    public ApiResponse<String> assignSection(
            @PathVariable Long teacherId,
            @PathVariable Long sectionId, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();

        assignmentService.assignSection(teacherId, sectionId, userId);
        return ApiResponse.ok("Section assigned successfully");
    }

    /** * Removes a teacher from a section.
     * Only accessible by ADMIN role.
     *
     * @param teacherId the ID of the teacher to remove
     * @param sectionId the ID of the section to remove the teacher from
     * @return ApiResponse with success message
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{teacherId}/assignments/section/{sectionId}")
    public ApiResponse<String> removeSection(
            @PathVariable Long teacherId,
            @PathVariable Long sectionId, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();

        assignmentService.removeTeacherFromSection(teacherId, sectionId, userId);
        return ApiResponse.ok("Section assignment removed successfully");
    }

    /** * Assigns a subject to a teacher.
     * Only accessible by ADMIN role.
     *
     * @param teacherId the ID of the teacher to assign
     * @param subjectId the ID of the subject to assign to the teacher
     * @return ApiResponse with success message
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{teacherId}/assignments/subject/{subjectId}")
    public ApiResponse<String> assignSubject(
            @PathVariable Long teacherId,
            @PathVariable Long subjectId, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();

        assignmentService.assignSubject(teacherId, subjectId, userId);
        return ApiResponse.ok("Subject assigned successfully");
    }

    /** * Assigns a class teacher to a section.
     * Only accessible by ADMIN role.
     *
     * @param teacherId the ID of the teacher to assign
     * @param sectionId the ID of the section to assign the teacher to
     * @return ApiResponse with success message
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{teacherId}/assignments/section/{sectionId}/class-teacher")
    public ApiResponse<String> assignClassTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long sectionId, HttpServletRequest request) {

        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();

        assignmentService.assignClassTeacherToSection(teacherId, sectionId, userId);
        return ApiResponse.ok("Class teacher assigned successfully");
    }

    /**
     * Updates the class teacher for a section.
     * @param teacherId
     * @param sectionId
     * @return
     */
    //I think not needed as we can follow simply assign and remove assign, can re-assign
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PutMapping("/{teacherId}/assignments/section/{sectionId}/class-teacher")
//    public ApiResponse<String> updateClassTeacher(
//            @PathVariable Long teacherId,
//            @PathVariable Long sectionId, HttpServletRequest request) {
//
//        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);
//        String token = userTypeInfo.getToken();
//        if (token != null) {
//            Long userId = userTypeInfo.getUserId();
//            if (userId == null) {
//                throw new UnauthorizedException("Invalid token");
//            }
//            assignmentService.updateClassTeacherToSection(teacherId, sectionId, userId);
//            return ApiResponse.ok("Class teacher updated successfully");
//        }
//        throw new UnauthorizedException("No valid token found");
//    }

    /** * Removes a subject assignment from a teacher.
     * Only accessible by ADMIN role.
     *
     * @param teacherId the ID of the teacher to remove the subject from
     * @param subjectId the ID of the subject to remove from the teacher
     * @return ApiResponse with success message
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{teacherId}/assignments/subject/{subjectId}")
    public ApiResponse<String> removeSubject(
            @PathVariable Long teacherId,
            @PathVariable Long subjectId, HttpServletRequest request) {
        UserTypeInfo userTypeInfo = requestContextService.getCurrentUserContext(request);

        Long userId = userTypeInfo.getUserId();

        assignmentService.removeSubject(teacherId, subjectId, userId);
        return ApiResponse.ok("Subject assignment removed successfully");
    }

    /** * Retrieves sections assignments for a specific teacher.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param teacherId the ID of the teacher to retrieve assignments for
     * @return ApiResponse with TeacherAssignmentDto containing assignments
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('PRINCIPAL')")
    @GetMapping("/{teacherId}/SectionAssignments")
    public ApiResponse<TeacherAssignmentDto> getAssignments(@PathVariable Long teacherId) {
        return ApiResponse.ok(assignmentService.getTeacherAssignments(teacherId, false, true));
    }

    /** * Retrieves all subject assignments for a specific teacher.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param teacherId the ID of the teacher to retrieve subject assignments for
     * @return ApiResponse with TeacherAssignmentDto containing subject assignments
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('PRINCIPAL')")
    @GetMapping("/{teacherId}/SubjectAssignments")
    public ApiResponse<TeacherAssignmentDto> getSubjectAssignments(@PathVariable Long teacherId) {
        return ApiResponse.ok(assignmentService.getTeacherAssignments(teacherId, true, false));
    }


    /** * Retrieves all teachers assigned to a specific subject.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param subjectId the ID of the subject to retrieve assigned teachers for
     * @param page the page number for pagination
     * @param size the number of items per page
     * @return ApiResponse with a paginated list of TeachersAssignedDto
     */
    @GetMapping("/{subjectId}/teachers-assigned")
    public ApiResponse<List<TeachersAssignedDto>> getTeachersAssignedToSubject(
            @PathVariable Long subjectId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10")@Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TeachersAssignedDto> result = assignmentService.getAssignedTeachers(subjectId, pageable);
        return ApiResponse.paged(result);
    }

    /** * Retrieves all teachers assigned to a specific section.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param sectionId the ID of the section to retrieve assigned teachers for
     * @param page the page number for pagination
     * @param size the number of items per page
     * @return ApiResponse with a paginated list of TeachersAssignedDto
     */
    @GetMapping("/by-section")
    public ApiResponse<List<TeachersAssignedDto>> getTeachersBySection(
            @RequestParam Long sectionId,
            @RequestParam(defaultValue = "0")@Min(0) Integer page,
            @RequestParam(defaultValue = "10")@Min(1) @Max(100) Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.paged(assignmentService.getTeachersBySectionId(sectionId, pageable));
    }

    /** * Retrieves all teachers assigned to a specific subject.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param subjectId the ID of the subject to retrieve assigned teachers for
     * @param page the page number for pagination
     * @param size the number of items per page
     * @return ApiResponse with a paginated list of Subjects assigned to the teacher
     */
    @GetMapping("/by-subject")
    public ApiResponse<List<TeachersAssignedDto>> getTeachersBySubject(
            @RequestParam Long subjectId,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10")@Min(1) @Max(100) Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TeachersAssignedDto> teachers = assignmentService.getTeachersBySubjectId(subjectId, pageable);
        return ApiResponse.paged(teachers);
    }


    /** * Retrieves all sections taught by a specific class teacher.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param classTeacherId the ID of the class teacher to retrieve sections for
     * @param page the page number for pagination
     * @param size the number of items per page
     * @return ApiResponse with a paginated list of SectionResponseDto
     */
    @GetMapping("/sections-by-class-teacher")
    public ApiResponse<List<SectionResponseDto>> getSectionsByClassTeacher(
            @RequestParam Long classTeacherId,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SectionResponseDto> sections = assignmentService.findSectionsByClassTeacherId(classTeacherId, pageable);
        return ApiResponse.paged(sections);
    }
}