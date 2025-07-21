package com.schoolerp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolerp.enums.ClassStandard;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "school_classes")
@NoArgsConstructor @AllArgsConstructor @Builder
public class SchoolClass extends BaseEntity {

    @Column(unique = true)
    @JsonProperty("name")
    @Enumerated(EnumType.STRING)
    private ClassStandard name;

    @OneToMany(mappedBy = "schoolClass")
    private List<Section> sections = new ArrayList<>();

    public ClassStandard getName() {
        return name;
    }

    public void setName(ClassStandard name) {
        this.name = name;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}