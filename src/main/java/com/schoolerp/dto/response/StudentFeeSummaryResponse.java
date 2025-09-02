package com.schoolerp.dto.response;

import com.schoolerp.enums.ClassStandard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFeeSummaryResponse {
    private Long studentId;
    private String studentName;
    private ClassStandard className;
    private String sessionName;
    private BigDecimal totalAssigned;
    private BigDecimal totalDiscount;
    private BigDecimal totalPaid;
    private BigDecimal balance;
    private List<FeeHeadBalanceResponse> feeHeadBalances;

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

    public ClassStandard getClassName() {
        return className;
    }

    public void setClassName(ClassStandard className) {
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

    public List<FeeHeadBalanceResponse> getFeeHeadBalances() {
        return feeHeadBalances;
    }

    public void setFeeHeadBalances(List<FeeHeadBalanceResponse> feeHeadBalances) {
        this.feeHeadBalances = feeHeadBalances;
    }
}
