package com.schoolerp.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class PdfUtil {
    public byte[] generateReceipt(String studentName, String feeType, String amount) throws DocumentException {
        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(doc, out);
        doc.open();
        doc.add(new Paragraph("School ERP Receipt"));
        doc.add(new Paragraph("Student: " + studentName));
        doc.add(new Paragraph("Fee: " + feeType));
        doc.add(new Paragraph("Amount: " + amount));
        doc.close();
        return out.toByteArray();
    }
}