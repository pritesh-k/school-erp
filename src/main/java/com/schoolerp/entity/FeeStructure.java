package com.schoolerp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fee_structures", uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "school_class_id"}))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructure extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private AcademicSession session;
    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass; // Or add section if you need
    @OneToMany(mappedBy = "feeStructure", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FeeStructureItem> items = new HashSet<>();

    public AcademicSession getSession() {
        return session;
    }

    public void setSession(AcademicSession session) {
        this.session = session;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Set<FeeStructureItem> getItems() {
        return items;
    }

    public void setItems(Set<FeeStructureItem> items) {
        this.items = items;
    }
}
