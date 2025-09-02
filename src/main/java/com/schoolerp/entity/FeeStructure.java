//package com.schoolerp.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "fee_structures",
//        uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "class_id"}))
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class FeeStructure extends BaseEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "session_id")
//    private AcademicSession session;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "class_id")
//    private SchoolClass schoolClass;
//
//    @Column(nullable = false, length = 200)
//    private String name; // "Class 10 - Academic Year 2025"
//
//    @OneToMany(mappedBy = "feeStructure", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<FeeStructureItem> items = new HashSet<>();
//
//    public AcademicSession getSession() {
//        return session;
//    }
//
//    public void setSession(AcademicSession session) {
//        this.session = session;
//    }
//
//    public SchoolClass getSchoolClass() {
//        return schoolClass;
//    }
//
//    public void setSchoolClass(SchoolClass schoolClass) {
//        this.schoolClass = schoolClass;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Set<FeeStructureItem> getItems() {
//        return items;
//    }
//
//    public void setItems(Set<FeeStructureItem> items) {
//        this.items = items;
//    }
//}
