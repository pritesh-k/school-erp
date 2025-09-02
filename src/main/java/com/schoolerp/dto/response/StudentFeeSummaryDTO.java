package com.schoolerp.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public class StudentFeeSummaryDTO {
    private Long studentId;
    private String studentName;
    private String className;
    private String sessionName;
    private BigDecimal totalAssigned;
    private BigDecimal totalDiscount;
    private BigDecimal totalPaid;
    private BigDecimal balance;
    private List<FeeHeadBalanceDTO> feeHeadBalances;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public BigDecimal getTotalAssigned() {
        return totalAssigned;
    }

    public void setTotalAssigned(BigDecimal totalAssigned) {
        this.totalAssigned = totalAssigned;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<FeeHeadBalanceDTO> getFeeHeadBalances() {
        return feeHeadBalances;
    }

    public void setFeeHeadBalances(List<FeeHeadBalanceDTO> feeHeadBalances) {
        this.feeHeadBalances = feeHeadBalances;
    }
}
