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
        String token = userTypeInfo.getToken();
        if (token != null) {
            Long userId = userTypeInfo.getUserId();
            if (userId == null) {
                throw new UnauthorizedException("Invalid token");
            }
            return ApiResponse.ok(service.create(dto, userId));
        }
        throw new UnauthorizedException("No valid token found");
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
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

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
    @PostMapping("/{teacherId}/assignments/section/{sectionId}")
    public ApiResponse<String> assignSection(
            @PathVariable Long teacherId,
            @PathVariable Long sectionId) {
        assignmentService.assignSection(teacherId, sectionId);
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
            @PathVariable Long sectionId) {
        assignmentService.removeTeacherFromSection(teacherId, sectionId);
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
    @PostMapping("/{teacherId}/assignments/subject/{subjectId}")
    public ApiResponse<String> assignSubject(
            @PathVariable Long teacherId,
            @PathVariable Long subjectId) {
        assignmentService.assignSubject(teacherId, subjectId);
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
            @PathVariable Long sectionId) {
        assignmentService.assignClassTeacherToSection(teacherId, sectionId);
        return ApiResponse.ok("Class teacher assigned successfully");
    }

    /**
     * Updates the class teacher for a section.
     * @param teacherId
     * @param sectionId
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{teacherId}/assignments/section/{sectionId}/class-teacher")
    public ApiResponse<String> updateClassTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long sectionId) {
        assignmentService.updateClassTeacherToSection(teacherId, sectionId);
        return ApiResponse.ok("Class teacher updated successfully");
    }

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
            @PathVariable Long subjectId) {
        assignmentService.removeSubject(teacherId, subjectId);
        return ApiResponse.ok("Subject assignment removed successfully");
    }

    /** * Retrieves all assignments for a specific teacher.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param teacherId the ID of the teacher to retrieve assignments for
     * @return ApiResponse with TeacherAssignmentDto containing assignments
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('PRINCIPAL')")
    @GetMapping("/{teacherId}/assignments")
    public ApiResponse<TeacherAssignmentDto> getAssignments(@PathVariable Long teacherId) {
        return ApiResponse.ok(assignmentService.getTeacherAssignments(teacherId));
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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
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
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

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
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TeachersAssignedDto> teachers = assignmentService.getTeachersBySubjectId(subjectId, pageable);
        return ApiResponse.paged(teachers);
    }


    /** * Retrieves all sections taught by a specific class teacher.
     * Accessible by ADMIN, TEACHER, and PRINCIPAL roles.
     *
     * @param teacherId the ID of the class teacher to retrieve sections for
     * @param page the page number for pagination
     * @param size the number of items per page
     * @return ApiResponse with a paginated list of SectionResponseDto
     */
    @GetMapping("/sections-by-teacher")
    public ApiResponse<List<SectionResponseDto>> getSectionsByTeacher(
            @RequestParam Long teacherId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SectionResponseDto> sections = assignmentService.findSectionsByClassTeacherId(teacherId, pageable);
        return ApiResponse.paged(sections);
    }
}