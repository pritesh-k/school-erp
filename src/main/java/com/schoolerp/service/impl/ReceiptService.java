package com.schoolerp.service.impl;

import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    public String generateReceiptNumber() {
        return "RCP" + System.currentTimeMillis();
    }

    public String generateReceiptPDF(Long paymentId) {
        // Implementation for PDF generation
        // Using libraries like iText or Jasper Reports
        // Return file path or URL
        return "/receipts/" + generateReceiptNumber() + ".pdf";
    }
}

