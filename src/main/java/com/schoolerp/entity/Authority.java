//package com.schoolerp.entity;
//
//import com.schoolerp.enums.AuthorityCategory;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "authorities")
//@NoArgsConstructor
//@AllArgsConstructor
//public class Authority extends BaseEntity {
//
//    @Column(unique = true, nullable = false)
//    private String name; // CREATE_SUBJECT, UPDATE_STUDENT, etc.
//
//    private String description;
//
//    @Enumerated(EnumType.STRING)
//    private AuthorityCategory category; // SUBJECT, STUDENT, ATTENDANCE, etc.
//
//    // Getters and Setters
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public AuthorityCategory getCategory() { return category; }
//    public void setCategory(AuthorityCategory category) { this.category = category; }
//}
//
//
