package com.schoolerp.entity;

import com.schoolerp.enums.Relation;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "parents")
@NoArgsConstructor @AllArgsConstructor @Builder
public class Parent extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    private String occupation;

    @Enumerated(EnumType.STRING)
    private Relation relation;

    // Parent entity
    @OneToMany(mappedBy = "parent")
    private Set<Student> students = new HashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}