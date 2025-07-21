//package com.schoolerp.controller;
//
//import com.schoolerp.dto.request.AuthorityCreateDto;
//import com.schoolerp.dto.response.ApiResponse;
//import com.schoolerp.entity.Authority;
//import com.schoolerp.security.JwtUtil;
//import com.schoolerp.service.impl.AuthorityServiceImpl;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/authorities")
//@RequiredArgsConstructor
//public class AuthorityController {
//
//    private final AuthorityServiceImpl authorityService;
//
//    @PostMapping("/grant")
//    @PreAuthorize("hasAuthority('MANAGE_AUTHORITIES')")
//    public ApiResponse<String> grantAuthority(
//            @RequestParam Long userId,
//            @RequestParam String authorityName,
//            HttpServletRequest request) {
//
//        Long grantedBy = JwtUtil.getUserIdFromRequest(request);
//        authorityService.grantAuthority(userId, authorityName, grantedBy);
//        return ApiResponse.ok("successfully granted authority");
//    }
//
//    @DeleteMapping("/revoke")
//    @PreAuthorize("hasAuthority('MANAGE_AUTHORITIES')")
//    public ApiResponse<String> revokeAuthority(
//            @RequestParam Long userId,
//            @RequestParam String authorityName) {
//
//        authorityService.revokeAuthority(userId, authorityName);
//        return ApiResponse.ok("successfully revoked authority");
//    }
//
//    @GetMapping("/user/{userId}")
//    @PreAuthorize("hasAuthority('VIEW_AUTHORITIES')")
//    public ApiResponse<List<String>> getUserAuthorities(@PathVariable Long userId) {
//        return ApiResponse.ok(authorityService.getUserAuthorities(userId));
//    }
//
//    @PostMapping
//    public ApiResponse<Authority> create(@RequestBody AuthorityCreateDto dto) {
//        return ApiResponse.ok(authorityService.create(dto));
//    }
//
//    @GetMapping
//    public ApiResponse<List<Authority>> getAll() {
//        return ApiResponse.ok(authorityService.getAll());
//    }
//
//    @PutMapping("/{id}")
//    public ApiResponse<Authority> update(@PathVariable Long id, @RequestBody AuthorityCreateDto dto) {
//        return ApiResponse.ok(authorityService.update(id, dto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ApiResponse<String> delete(@PathVariable Long id) {
//        authorityService.delete(id);
//        return ApiResponse.ok("successfully deleted authority");
//    }
//}
