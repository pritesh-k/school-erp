//package com.schoolerp.entity;
//
//import com.schoolerp.enums.DutyRole;
//import jakarta.persistence.*;
//
//@Entity
//@Table(name="exam_invigilation",
//        uniqueConstraints = @UniqueConstraint(name="uk_duty_slot_teacher", columnNames={"exam_slot_id","teacher_id"}),
//        indexes = @Index(name="idx_duty_slot", columnList="exam_slot_id"))
//public class ExamInvigilationDuty extends BaseEntity {
//    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="exam_slot_id", nullable=false)
//    private ExamSlot examSlot;
//
//    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="teacher_id", nullable=false)
//    private Teacher teacher;
//
//    @Enumerated(EnumType.STRING) @Column(nullable=false)
//    private DutyRole role; // INVIGILATOR, SUPERVISOR, RELIEF
//
//
//    public ExamSlot getExamSlot() {
//        return examSlot;
//    }
//
//    public void setExamSlot(ExamSlot examSlot) {
//        this.examSlot = examSlot;
//    }
//
//    public Teacher getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(Teacher teacher) {
//        this.teacher = teacher;
//    }
//
//    public DutyRole getRole() {
//        return role;
//    }
//
//    public void setRole(DutyRole role) {
//        this.role = role;
//    }
//}
