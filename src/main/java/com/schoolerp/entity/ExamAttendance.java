//package com.schoolerp.entity;
//
//import com.schoolerp.enums.AttendanceStatus;
//import jakarta.persistence.*;
//
//@Entity
//@Table(
//        name = "exam_attendance",
//        uniqueConstraints = @UniqueConstraint(
//                name = "uk_attendance_enrollment_slot",
//                columnNames = {"student_enrollment_id", "exam_slot_id"}
//        ),
//        indexes = {
//                @Index(name = "idx_attendance_slot", columnList = "exam_slot_id"),
//                @Index(name = "idx_attendance_enrollment", columnList = "student_enrollment_id")
//        }
//)
//public class ExamAttendance extends BaseEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "student_enrollment_id", nullable = false)
//    private StudentEnrollment studentEnrollment;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "exam_slot_id", nullable = false)
//    private ExamSlot examSlot;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private AttendanceStatus status; // PRESENT, ABSENT, LATE, EXCUSED
//
//    public StudentEnrollment getStudentEnrollment() {
//        return studentEnrollment;
//    }
//
//    public void setStudentEnrollment(StudentEnrollment studentEnrollment) {
//        this.studentEnrollment = studentEnrollment;
//    }
//
//    public ExamSlot getExamSlot() {
//        return examSlot;
//    }
//
//    public void setExamSlot(ExamSlot examSlot) {
//        this.examSlot = examSlot;
//    }
//
//    public AttendanceStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(AttendanceStatus status) {
//        this.status = status;
//    }
//}
