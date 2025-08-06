package com.schoolerp.repository;

import com.schoolerp.dto.response.attendance.AttendanceStatsByStudentDto;
import com.schoolerp.entity.Attendance;
import com.schoolerp.entity.Teacher;
import com.schoolerp.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

    Page<Attendance> findByStudentEnrollment_Student_IdOrderByDateDesc(Long studentId, Pageable pageable);

    Page<Attendance> findByStudentEnrollment_Section_IdAndDate(Long sectionId, LocalDate date, Pageable pageable);


    Page<Attendance> findByStudentEnrollment_Section_Id(Long sectionId, Pageable pageable);

    Page<Attendance> findByDate(LocalDate date, Pageable pageable);

    Page<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);


    boolean existsByStudentEnrollment_IdAndDate(Long studentEnrollmentId, LocalDate date);

}