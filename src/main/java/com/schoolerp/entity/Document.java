package com.schoolerp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documents")
@NoArgsConstructor @AllArgsConstructor @Builder
public class Document extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    private String type;
    private String fileName;
    private String fileUrl;

    @Lob
    @Basic(fetch = FetchType.LAZY) // prevent loading file data unless accessed
    private byte[] data;           // stores the actual file content

    @ManyToOne(fetch = FetchType.LAZY)
    private User uploadedBy;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}