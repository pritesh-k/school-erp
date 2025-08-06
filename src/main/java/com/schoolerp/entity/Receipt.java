package com.schoolerp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "receipts")
public class Receipt extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private StudentFeeAssignment studentFeeAssignment;

    private String receiptNo;
    private LocalDate receiptDate;
    private String fileUrl;
    private String note;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FeePayment> payments = new HashSet<>();
}

