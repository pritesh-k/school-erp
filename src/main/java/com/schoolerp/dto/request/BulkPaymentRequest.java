package com.schoolerp.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class BulkPaymentRequest {

    @NotEmpty(message = "Payments list cannot be empty")
    @Valid
    private List<RecordPaymentRequest> payments;

    // Getters and setters (or use Lombok @Data / @Getter @Setter)
    public List<RecordPaymentRequest> getPayments() {
        return payments;
    }

    public void setPayments(List<RecordPaymentRequest> payments) {
        this.payments = payments;
    }
}

