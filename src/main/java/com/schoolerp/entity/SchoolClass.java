package com.schoolerp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolerp.enums.ClassStandard;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "school_classes")
@NoArgsConstructor @AllArgsConstructor @Builder
public class SchoolClass extends BaseEntity {

    @Column(unique = true)
    @JsonProperty("name")
    @Enumerated(EnumType.STRING)
    private ClassStandard name;

    @OneToMany(mappedBy = "schoolClass")
    private Set<Section> sections = new HashSet<>();

    public ClassStandard getName() {
        return name;
    }

    public void setName(ClassStandard name) {
        this.name = name;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    @Transient
    public int getSortOrder() {
        return name.getOrder(); // 'name' is your enum field
    }

}