package com.schoolerp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fee_heads")
public class FeeHead extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name; // e.g. "Tuition", "Transport", "Library"

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

