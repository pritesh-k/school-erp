//package com.schoolerp.entity;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "receipts")
//public class Receipt extends BaseEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private StudentFeeAssignment studentFeeAssignment;
//
//    private String receiptNo;
//    private LocalDate receiptDate;
//    private String fileUrl;
//    private String note;
//
//    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<FeePayment> payments = new HashSet<>();
//
//    public StudentFeeAssignment getStudentFeeAssignment() {
//        return studentFeeAssignment;
//    }
//
//    public void setStudentFeeAssignment(StudentFeeAssignment studentFeeAssignment) {
//        this.studentFeeAssignment = studentFeeAssignment;
//    }
//
//    public String getReceiptNo() {
//        return receiptNo;
//    }
//
//    public void setReceiptNo(String receiptNo) {
//        this.receiptNo = receiptNo;
//    }
//
//    public LocalDate getReceiptDate() {
//        return receiptDate;
//    }
//
//    public void setReceiptDate(LocalDate receiptDate) {
//        this.receiptDate = receiptDate;
//    }
//
//    public String getFileUrl() {
//        return fileUrl;
//    }
//
//    public void setFileUrl(String fileUrl) {
//        this.fileUrl = fileUrl;
//    }
//
//    public String getNote() {
//        return note;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }
//
//    public Set<FeePayment> getPayments() {
//        return payments;
//    }
//
//    public void setPayments(Set<FeePayment> payments) {
//        this.payments = payments;
//    }
//}
//
