//package com.schoolerp.entity;
//
//import com.schoolerp.enums.FeeCategory;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "fee_heads")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class FeeHead extends BaseEntity {
//
//    @Column(nullable = false, unique = true, length = 100)
//    private String name; // "Tuition Fee", "Transport Fee", "Library Fee"
//
//    @Column(length = 500)
//    private String description;
//
//    @Enumerated(EnumType.STRING)
//    private FeeCategory category; // ACADEMIC, TRANSPORT, ACTIVITY
//
//    @Column(nullable = false)
//    private Boolean isMandatory = true;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public FeeCategory getCategory() {
//        return category;
//    }
//
//    public void setCategory(FeeCategory category) {
//        this.category = category;
//    }
//
//    public Boolean getMandatory() {
//        return isMandatory;
//    }
//
//    public void setMandatory(Boolean mandatory) {
//        isMandatory = mandatory;
//    }
//}
//
//
