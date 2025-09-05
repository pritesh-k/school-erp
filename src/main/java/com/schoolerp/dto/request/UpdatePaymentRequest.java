package com.schoolerp.dto.request;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class UpdatePaymentRequest {

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    private String paymentMode; // CASH, ONLINE, CHEQUE

    private String transactionReference;

    private String remarks;

    // Getters and setters (or use Lombok @Data / @Getter @Setter)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
