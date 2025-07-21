package com.schoolerp.dto.response;

import com.schoolerp.dto.BaseDTO;
import com.schoolerp.entity.Student;
import com.schoolerp.entity.User;

public class DocumentResponseDto extends BaseDTO {
    private Student student;
    private String type;
    private String fileName;
    private String fileUrl;
    private byte[] data;           // stores the actual file content
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