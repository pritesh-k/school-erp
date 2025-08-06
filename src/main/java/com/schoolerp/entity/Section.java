package com.schoolerp.entity;

import com.schoolerp.enums.SectionName;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sections")
@NoArgsConstructor @AllArgsConstructor @Builder
public class Section extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private SectionName name;

    @Column(unique = true)
    private String roomNo;

    private Integer capacity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    // This maps subjects assigned to the section
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SectionSubjectAssignment> sectionSubjectAssignments = new HashSet<>();

    public Set<SectionSubjectAssignment> getSectionSubjectAssignments() {
        return sectionSubjectAssignments;
    }

    public void setSectionSubjectAssignments(Set<SectionSubjectAssignment> sectionSubjectAssignments) {
        this.sectionSubjectAssignments = sectionSubjectAssignments;
    }

    public SectionName getName() {
        return name;
    }

    public void setName(SectionName name) {
        this.name = name;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        return roomNo != null && roomNo.equals(section.roomNo);
    }

    @Override
    public int hashCode() {
        return roomNo != null ? roomNo.hashCode() : 0;
    }

}